package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import sample.Model.JDBCUsuarioDAO;
import sample.Model.Usuario;

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

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setNomeCompleto(nomeCompleto);
        usuario.setNomeUsuario(nomeUsuario);
        usuario.setSenha(senha);

        try {
            boolean verifica = JDBCUsuarioDAO.getInstance().buscarUsuario(this.nomeUsuario.getText());
            if(this.email.getText().equals("") || this.email.getText().equals(null) || this.nomeCompleto.getText().equals("") || this.nomeCompleto.getText().equals(null) || this.nomeUsuario.getText().equals("") || this.nomeUsuario.getText().equals(null) || this.senha.getText().equals("") || this.senha.getText().equals(null)){
                throw new NullPointerException();
            }
            else if(this.senha.getText().length() < 6){
                throw new IllegalArgumentException();
            }
            else if (verifica ){
                throw new Exception();
            }

            boolean sucesso = JDBCUsuarioDAO.getInstance().create(usuario);
            if(sucesso) {
                File usuarioDiretorio = new File("./resources/ImagensUsuarios/usuario-" + usuario.getId());
                usuarioDiretorio.mkdir();
                mensagem(Alert.AlertType.CONFIRMATION, usuario.getNomeCompleto() + ", você foi cadastrado(a) com sucesso!");
            } else {
                mensagem(Alert.AlertType.ERROR, "Desculpe, houve um erro no seu cadastro!");
            }
        }catch (NullPointerException e) {
            mensagem(Alert.AlertType.ERROR, "Preencha todos os campos!");
        }catch (IllegalArgumentException e) {
            mensagem(Alert.AlertType.ERROR, "A senha precisa no mínimo de 6 caracteres!");
        }
        catch (Exception s){
            mensagem(Alert.AlertType.ERROR, "Nome de usuário já cadastrado!");
        }
    }
}
