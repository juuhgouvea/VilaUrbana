package sample.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUsuarioDAO implements UsuarioDAO {
    private static JDBCUsuarioDAO instance;

    private JDBCUsuarioDAO() {
    }

    public static JDBCUsuarioDAO getInstance() {
        if (instance == null) {
            instance = new JDBCUsuarioDAO();
        }

        return instance;
    }

    @Override
    public boolean create(Usuario usuario) throws Exception {
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL = "INSERT INTO usuarios(nome_completo, nome_usuario, email, senha) VALUES (?, ?, ?, ?)";
        ;
        int id = 0;

        try {
            con = FabricaConexao.getConnection();

            pstm = con.prepareStatement(SQL);
            pstm.setString(1, usuario.getNomeCompleto());
            pstm.setString(2, usuario.getNomeUsuario());
            pstm.setString(3, usuario.getEmail());
            pstm.setString(4, usuario.getSenha());
            pstm.execute();

            SQL = "SELECT id FROM usuarios ORDER BY id DESC LIMIT 1";
            pstm = con.prepareStatement(SQL);

            rs = pstm.executeQuery();

            while (rs.next()) {
                id = rs.getInt("id");
            }
            usuario.setId(id);

            rs.close();
            pstm.close();
            con.close();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean buscarUsuario(String usuario) throws Exception {
        boolean result = false;
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL;

        try {
            con = FabricaConexao.getConnection();

            SQL = "SELECT nome_usuario FROM usuarios WHERE nome_usuario = ? ";
            pstm = con.prepareStatement(SQL);
            pstm.setString(1, usuario);

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

