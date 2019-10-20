package sample.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCRestauranteDAO implements RestauranteDAO {
    private static JDBCRestauranteDAO instance = null;

    private JDBCRestauranteDAO(){}

    public static JDBCRestauranteDAO getInstance() {
        if(instance == null) {
            instance = new JDBCRestauranteDAO();
        }

        return instance;
    }

    @Override
    public boolean create(Restaurante restaurante) throws Exception {
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL = "INSERT INTO restaurantes(nome_restaurante, foto_restaurante, usuario_id) VALUES (?, ?, ?)";

        int id = 0;

        try {
            con = FabricaConexao.getConnection();

            pstm = con.prepareStatement(SQL);
            pstm.setString(1, restaurante.getNome());
            pstm.setString(2, restaurante.getLogo());
            pstm.setInt(3, restaurante.getUsuario().getId());
            pstm.execute();

            SQL = "SELECT id FROM restaurantes ORDER BY id DESC LIMIT 1";
            pstm = con.prepareStatement(SQL);

            rs = pstm.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
            restaurante.setId(id);

            rs.close();
            pstm.close();
            con.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean buscarRest(String restaurante) throws Exception {
        boolean result = false;
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL;

        try {
            con = FabricaConexao.getConnection();

            SQL = "SELECT nome_restaurante FROM restaurantes WHERE nome_restaurante = ? ";
            pstm = con.prepareStatement(SQL);
            pstm.setString(1, restaurante);

            rs = pstm.executeQuery();

            if (rs.next()) {
                result = true;
            }
            pstm.close();
            return result;

        } catch (Exception e) {
            return false;
        }
    }
}
