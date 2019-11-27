package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Model.JDBCUsuarioDAO;
import sample.Model.Usuario;
import sample.NavegadorJanelas;

import java.io.File;
import java.sql.SQLException;

public class ControllerJanelaEditarPerfil extends ControllerBase {
    @FXML
    private TextField tfEmail;
    @FXML
    private TextField tfNomeCompleto;
    @FXML
    private TextField tfNomeUsuario;
    @FXML
    private PasswordField tfSenha;
    @FXML
    private PasswordField tfConfirmarSenha;
    private Usuario logado;

    @FXML
    public void initialize() {
        this.logado = JDBCUsuarioDAO.getInstance().getLogado();
        this.tfEmail.setText(logado.getEmail());
        this.tfNomeCompleto.setText(logado.getNomeCompleto());
        this.tfNomeUsuario.setText(logado.getNomeUsuario());
    }

    @FXML
    public void excluir() {}

    @FXML
    public void concluir() {
        if(this.tfEmail.getText().equals("") || this.tfEmail.getText().equals(null) ||
                this.tfNomeCompleto.getText().equals("") || this.tfNomeCompleto.getText().equals(null) ||
                this.tfNomeUsuario.getText().equals("") || this.tfNomeUsuario.getText().equals(null)){
            mensagem(Alert.AlertType.ERROR, "Preencha todos os campos!");
            return;
        }
        else if(!tfSenha.getText().equals("") && this.tfSenha.getText().length() < 6){
            mensagem(Alert.AlertType.ERROR, "A senha precisa no mínimo de 6 caracteres!");
            return;
        } else if(!tfSenha.getText().equals("") &&
                !this.tfSenha.getText().equals(tfConfirmarSenha.getText())) {
            mensagem(Alert.AlertType.ERROR, "Confirme a senha corretamente!");
            return;
        }

        try {
            this.logado.setEmail(tfEmail.getText());
            this.logado.setNomeCompleto(tfNomeCompleto.getText());
            this.logado.setNomeUsuario(tfNomeUsuario.getText());

            if(!tfSenha.getText().equals("")) {
                this.logado.setSenha(tfSenha.getText());
            } else {
                this.logado.setSenha(JDBCUsuarioDAO.getInstance().getLogado().getSenha());
            }

            Usuario usuario = JDBCUsuarioDAO.getInstance().update(this.logado);
            if (usuario != null) {
               this.logado = usuario;
               JDBCUsuarioDAO.getInstance().getLogado().setEmail(this.logado.getEmail());
               JDBCUsuarioDAO.getInstance().getLogado().setNomeCompleto(this.logado.getNomeCompleto());
               JDBCUsuarioDAO.getInstance().getLogado().setNomeUsuario(this.logado.getNomeUsuario());
               JDBCUsuarioDAO.getInstance().getLogado().setSenha("");

                mensagem(Alert.AlertType.CONFIRMATION, this.logado.getNomeCompleto() + ", você foi atualizado(a) com sucesso!");
            } else {
                mensagem(Alert.AlertType.ERROR, "Desculpe, houve um erro na atualização!");
            }
        } catch (SQLException e) {
            System.out.println(e.getErrorCode());
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

    @FXML
    public void voltar () {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_HOME);
    }



}
