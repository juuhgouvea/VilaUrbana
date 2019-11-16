package sample.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCUsuarioDAO implements UsuarioDAO {
    private static JDBCUsuarioDAO instance;
    private Usuario logado = null;

    private JDBCUsuarioDAO() { }

    public static JDBCUsuarioDAO getInstance() {
        if (instance == null) {
            instance = new JDBCUsuarioDAO();
        }

        return instance;
    }

    @Override
    public Usuario create(Usuario usuario) throws SQLException {
        String SQL = "INSERT INTO usuarios(nome_completo, nome_usuario, email, senha) VALUES (?, ?, ?, ?)";
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;

        int cod = 0;

        con = FabricaConexao.getConnection();

        pstm = con.prepareStatement(SQL);
        pstm.setString(1, usuario.getNomeCompleto());
        pstm.setString(2, usuario.getNomeUsuario());
        pstm.setString(3, usuario.getEmail());
        pstm.setString(4, usuario.getSenha());;
        pstm.execute();

        SQL = "SELECT cod_usuario FROM usuarios ORDER BY cod_usuario DESC LIMIT 1";
        pstm = con.prepareStatement(SQL);

        rs = pstm.executeQuery();

        while (rs.next()) {
            cod = rs.getInt("cod_usuario");
        }
        usuario.setCodUsuario(cod);

        rs.close();
        pstm.close();
        con.close();

        return usuario;
    }

    @Override
    public Usuario search(int cod) {
        String SQL = "SELECT * FROM usuarios WHERE cod_usuario = ?";
        Usuario usuario = null;

        try {
            Connection con = FabricaConexao.getConnection();
            ResultSet rs;
            PreparedStatement pstm = con.prepareStatement(SQL);
            pstm.setInt(1, cod);
            rs = pstm.executeQuery();

            if (rs.next()) {
                usuario = new Usuario();
                usuario.setCodUsuario(rs.getInt("cod_usuario"));
                usuario.setNomeUsuario(rs.getString("nome_usuario"));
                usuario.setNomeCompleto(rs.getString("nome_completo"));
                usuario.setEmail(rs.getString("email"));
            }

            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao procurar usuario!\n" + e.getMessage());
        }

        return usuario;
    }

    @Override
    public Usuario logar(String nomeUsuario, String senha) {
        String SQL = "SELECT * FROM usuarios WHERE nome_usuario LIKE ? AND senha LIKE ?";

        try {
            Connection con = FabricaConexao.getConnection();
            ResultSet rs;
            PreparedStatement pstm = con.prepareStatement(SQL);
            pstm.setString(1, nomeUsuario);
            pstm.setString(2, senha);
            rs = pstm.executeQuery();

            if (rs.next()) {
                this.logado = new Usuario();
                this.logado.setCodUsuario(rs.getInt("cod_usuario"));
                this.logado.setNomeUsuario(rs.getString("nome_usuario"));
                this.logado.setNomeCompleto(rs.getString("nome_completo"));
                this.logado.setEmail(rs.getString("email"));
            } else {
                this.logado = null;
            }

            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao encontrar usuario!\n" + e.getMessage());
        }

        return this.logado;
    }

    @Override
    public void logout() {
        this.logado = null;
    }

    public Usuario getLogado() {
        return this.logado;
    }
}

