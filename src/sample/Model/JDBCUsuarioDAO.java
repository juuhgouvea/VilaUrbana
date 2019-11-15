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
    public Usuario create(Usuario usuario) throws Exception {
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL = "INSERT INTO usuarios(nome_completo, nome_usuario, email, senha) VALUES (?, ?, ?, ?)";

        int id = 0;

        con = FabricaConexao.getConnection();

        pstm = con.prepareStatement(SQL);
        pstm.setString(1, usuario.getNomeCompleto());
        pstm.setString(2, usuario.getNomeUsuario());
        pstm.setString(3, usuario.getEmail());
        pstm.setString(4, usuario.getSenha());
        pstm.execute();

        SQL = "SELECT cod_usuario FROM usuarios ORDER BY cod_usuario DESC LIMIT 1";
        pstm = con.prepareStatement(SQL);

        rs = pstm.executeQuery();

        while (rs.next()) {
            id = rs.getInt("id");
        }
        usuario.setCodUsuario(id);

        rs.close();
        pstm.close();
        con.close();

        return usuario;
    }

    public Usuario buscarUsuario(String email) throws Exception {
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL;
        Usuario usuario = null;

        con = FabricaConexao.getConnection();

        SQL = "SELECT * FROM usuarios WHERE email LIKE ?";
        pstm = con.prepareStatement(SQL);
        pstm.setString(1, email);

        rs = pstm.executeQuery();

        if (rs.next()) {
            usuario = new Usuario();
            usuario.setCodUsuario(rs.getInt("id"));
            usuario.setNomeUsuario(rs.getString("nome_usuario"));
            usuario.setNomeCompleto(rs.getString("nome_completo"));
            usuario.setEmail(rs.getString("email"));
        }

        pstm.close();

        return usuario;
    }
}

