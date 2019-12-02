package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.Model.JDBCUsuarioDAO;
import sample.Model.Timer;
import sample.Model.Usuario;
import sample.NavegadorJanelas;

import java.io.File;
import java.sql.SQLException;

public class ControllerJanelaCadastrarUsuario extends ControllerBase {
    @FXML
    private TextField email;
    @FXML
    private TextField nomeCompleto;
    @FXML
    private TextField nomeUsuario;
    @FXML
    private TextField senha;

    @FXML
    public void cadastrar(){
        String email = this.email.getText();
        String nomeCompleto = this.nomeCompleto.getText();
        String nomeUsuario = this.nomeUsuario.getText();
        String senha = this.senha.getText();

        try {
            if(this.email.getText().equals("") || this.email.getText().equals(null) ||
                    this.nomeCompleto.getText().equals("") || this.nomeCompleto.getText().equals(null) ||
                    this.nomeUsuario.getText().equals("") || this.nomeUsuario.getText().equals(null) ||
                    this.senha.getText().equals("") || this.senha.getText().equals(null)){
                mensagem(Alert.AlertType.ERROR, "Preencha todos os campos!");
                return;
            }
            else if(this.senha.getText().length() < 6){
                mensagem(Alert.AlertType.ERROR, "A senha precisa no mínimo de 6 caracteres!");
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNomeCompleto(nomeCompleto);
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setSenha(senha);

            Usuario sucesso = JDBCUsuarioDAO.getInstance().create(usuario);
            if (sucesso != null) {
                File usuarioDiretorio = new File("resources/ImagensUsuarios/usuario-" + usuario.getCodUsuario());
                System.out.println(usuarioDiretorio.getAbsolutePath());
                usuarioDiretorio.mkdirs();
                mensagem(Alert.AlertType.CONFIRMATION, usuario.getNomeCompleto() + ", você foi cadastrado(a) com sucesso!");
                JDBCUsuarioDAO.getInstance().logar(sucesso.getNomeUsuario(), sucesso.getSenha());

               Timer.getInstance().setTime(0);

                NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_HOME);
            } else {
                mensagem(Alert.AlertType.ERROR, "Desculpe, houve um erro no seu cadastro!");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            if(e.getErrorCode() == 19) {
                String erro = "";
                String campo = e.getMessage().split(":")[1];
                campo = campo.substring(1, campo.length()-1);

                if(campo.equals("usuarios.nome_usuario")) {
                    erro = "Este nome de usuario já existe!";
                } else {
                    erro = "Este email já existe!";
                }

                mensagem(Alert.AlertType.ERROR, erro);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void login() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_LOGIN);
    }
}
