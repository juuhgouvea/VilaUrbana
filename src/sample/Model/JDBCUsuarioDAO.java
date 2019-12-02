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
    public Usuario update(Usuario usuario) throws SQLException {
        Connection con = FabricaConexao.getConnection();
        String SQL = "UPDATE usuarios SET email = ?, nome_completo = ?, nome_usuario = ?, senha = ? WHERE cod_usuario = ?";
        PreparedStatement pstm = con.prepareStatement(SQL);

        pstm.setString(1, usuario.getEmail());
        pstm.setString(2, usuario.getNomeCompleto());
        pstm.setString(3, usuario.getNomeUsuario());
        pstm.setString(4, usuario.getSenha());
        pstm.setInt(5, usuario.getCodUsuario());

        boolean failed = pstm.execute();

        if(!failed) {
            return usuario;
        }

        return null;
    }

    @Override
    public boolean delete(Usuario usuario) throws SQLException {
        Connection con = FabricaConexao.getConnection();
        String SQL = SQL = "DELETE FROM produtos WHERE cod_produto IN " +
                "(SELECT cod_produto FROM restaurantes_has_produtos WHERE cod_restaurante IN" +
                " (SELECT cod_restaurante FROM restaurantes WHERE cod_usuario = ?))";
        PreparedStatement pstm = con.prepareStatement(SQL);
        pstm.setInt(1, usuario.getCodUsuario());
        boolean failed = pstm.execute();

        SQL = "DELETE FROM restaurantes_has_produtos WHERE cod_restaurante IN" +
                " (SELECT cod_restaurante FROM restaurantes WHERE cod_usuario = ?)";

        pstm = con.prepareStatement(SQL);
        pstm.setInt(1, usuario.getCodUsuario());
        failed = pstm.execute();

        SQL = "DELETE FROM restaurantes WHERE cod_usuario = ?";
        pstm = con.prepareStatement(SQL);
        pstm.setInt(1, usuario.getCodUsuario());
        failed = pstm.execute();

        SQL = "DELETE FROM usuarios WHERE cod_usuario = ?";
        pstm = con.prepareStatement(SQL);
        pstm.setInt(1, usuario.getCodUsuario());
        failed = pstm.execute();

        pstm.close();
        con.close();

        if(!failed) {
            return true;
        }

        return false;
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
                this.logado.setSenha(rs.getString("senha"));
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

