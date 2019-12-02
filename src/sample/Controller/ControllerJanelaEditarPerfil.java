package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import sample.Model.JDBCUsuarioDAO;
import sample.Model.Usuario;
import sample.NavegadorJanelas;

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
    public void excluir() {
        try {
            boolean sucesso = JDBCUsuarioDAO.getInstance().delete(this.logado);

            if(sucesso) {
                JDBCUsuarioDAO.getInstance().logout();
                mensagem(Alert.AlertType.CONFIRMATION, "Sua conta foi excluida!");
                NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_USUARIO);
            }
        } catch (SQLException e) {
            mensagem(Alert.AlertType.ERROR, "Houve um erro ao excluir sua conta!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
            Usuario alterado = new Usuario();
            alterado.setCodUsuario(this.logado.getCodUsuario());
            alterado.setEmail(tfEmail.getText());
            alterado.setNomeCompleto(tfNomeCompleto.getText());
            alterado.setNomeUsuario(tfNomeUsuario.getText());

            if(!tfSenha.getText().equals("")) {
                alterado.setSenha(tfSenha.getText());
            } else {
                alterado.setSenha(this.logado.getSenha());
            }

            alterado = JDBCUsuarioDAO.getInstance().update(alterado);
            if (alterado != null) {

               this.logado = alterado;

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
