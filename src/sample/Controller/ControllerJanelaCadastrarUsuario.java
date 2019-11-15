package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.Model.JDBCUsuarioDAO;
import sample.Model.Usuario;

import java.io.File;

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
            Usuario usuarioBuscado = JDBCUsuarioDAO.getInstance().buscarUsuario(this.email.getText());
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
            else if (usuarioBuscado != null){
                mensagem(Alert.AlertType.ERROR, "Email de usuário já cadastrado!");
                return;
            }

            Usuario usuario = new Usuario();
            usuario.setEmail(email);
            usuario.setNomeCompleto(nomeCompleto);
            usuario.setNomeUsuario(nomeUsuario);
            usuario.setSenha(senha);

            Usuario sucesso = JDBCUsuarioDAO.getInstance().create(usuario);
            if (sucesso != null) {
                File usuarioDiretorio = new File("./resources/ImagensUsuarios/usuario-" + usuario.getCodUsuario());
                usuarioDiretorio.mkdir();
                mensagem(Alert.AlertType.CONFIRMATION, usuario.getNomeCompleto() + ", você foi cadastrado(a) com sucesso!");
            } else {
                mensagem(Alert.AlertType.ERROR, "Desculpe, houve um erro no seu cadastro!");
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
