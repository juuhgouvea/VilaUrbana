package sample.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
    public Restaurante create(Restaurante restaurante) throws Exception {
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL = "INSERT INTO restaurantes(nome_restaurante, foto_restaurante, cod_usuario) VALUES (?, ?, ?)";

        int id = 0;

        con = FabricaConexao.getConnection();

        pstm = con.prepareStatement(SQL);
        pstm.setString(1, restaurante.getNomeRestaurante());
        pstm.setString(2, restaurante.getFotoRestaurante());
        pstm.setInt(3, restaurante.getUsuario().getCodUsuario());
        pstm.execute();

        SQL = "SELECT cod_restaurante FROM restaurantes ORDER BY cod_restaurante DESC LIMIT 1";
        pstm = con.prepareStatement(SQL);

        rs = pstm.executeQuery();

        while (rs.next()) {
            id = rs.getInt("cod_restaurante");
        }
        restaurante.setCodRestaurante(id);

        rs.close();
        pstm.close();
        con.close();

        return restaurante;
    }

    @Override
    public ArrayList<Produto> addProdutos(Restaurante restaurante, ArrayList<Produto> produtos) throws Exception {
        Connection con = FabricaConexao.getConnection();
        String SQL = "INSERT INTO restaurantes_has_produtos (cod_restaurante, cod_produto) VALUES (?, ?)";
        PreparedStatement pstm = con.prepareStatement(SQL);

        for (Produto p : produtos) {
            pstm.setInt(1, restaurante.getCodRestaurante());
            pstm.setInt(2, p.getCodProduto());

            pstm.execute();
            restaurante.getProdutos().add(p);
        }

        return produtos;
    }

    public boolean buscarRest(String restaurante) throws Exception {
        boolean result = false;
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL;

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
    }
}
