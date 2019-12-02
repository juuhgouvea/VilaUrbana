package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Model.JDBCUsuarioDAO;
import sample.Model.Timer;
import sample.Model.Usuario;
import sample.NavegadorJanelas;

public class ControllerJanelaLogin extends ControllerBase {
    @FXML
    private TextField nomeUsuario;
    @FXML
    private PasswordField senhaUsuario;

    public void entrar() {
        String nome = nomeUsuario.getText();
        String senha = senhaUsuario.getText();

        Usuario usuario = JDBCUsuarioDAO.getInstance().logar(nome, senha);
        if(usuario == null) {
            mensagem(Alert.AlertType.ERROR, "Suas credenciais não foram encontradas. Tente novamente!");
            return;
        }

        Timer.getInstance().start();
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_HOME);
    }

    @FXML
    public void voltar(){
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_USUARIO);
    }
}
