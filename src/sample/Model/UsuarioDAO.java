package sample.Model;

import java.sql.SQLException;

public interface UsuarioDAO {
    public Usuario create (Usuario usuario) throws SQLException;
    public Usuario search(int cod);
    public Usuario logar(String nomeUsuario, String senha);
    public void logout();
}
