package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.Model.JDBCRestauranteDAO;
import sample.Model.JDBCUsuarioDAO;
import sample.Model.Restaurante;
import sample.Model.Usuario;
import sample.NavegadorJanelas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class ControllerJanelaCadastrarRestaurante extends ControllerBase {
    @FXML
    private Text logoFile;
    @FXML
    private TextField nomeRestaurante;
    @FXML
    private Button btnConcluir;

    private File logo = null;
    private BufferedImage image = null;
    private Restaurante restaurante = null;

    @Override
    public void setDados(Object dados) {
        String nome = ((Restaurante) dados).getNomeRestaurante();
        this.nomeRestaurante.setText(nome);
        this.restaurante = null;
    }

    @FXML
    public void cadastrar() {
        String erros = "";
        if(this.restaurante != null) {
            mensagem(Alert.AlertType.INFORMATION, "Prossiga para o próximo passo!");
            return;
        }

        String nome = this.nomeRestaurante.getText();
        Restaurante newRestaurante = new Restaurante();

        try {
            if(nome.equals("")){
                erros += "Preencha o campo obrigatório!\n";
            }

            if(!erros.equals("")) {
                mensagem(Alert.AlertType.ERROR, erros);
                return;
            }

            Usuario logado = JDBCUsuarioDAO.getInstance().getLogado();
            newRestaurante.setNomeRestaurante(nome);
            newRestaurante.setUsuario(logado);

            if (this.logo != null && this.image != null) {
                String path = "./resources/ImagensUsuarios/usuario-" +
                        logado.getCodUsuario() + "/" + logo.getName();

                ImageIO.write(this.image, "PNG", new File(path));
                newRestaurante.setFotoRestaurante(this.logo.getName());
            }

            this.restaurante = newRestaurante;
            this.nomeRestaurante.setDisable(true);
            this.btnConcluir.setDisable(true);
            mensagem(Alert.AlertType.CONFIRMATION, "Avance para o próximo passo para concluir o cadastro!");
        } catch (Exception e){
            mensagem(Alert.AlertType.ERROR, "Um erro ocorreu ao registrar informações do restaurante!\n" + e.getMessage());
        }
    }

    @FXML
    public void uploadFoto(){
        ArrayList<String> arquivos = new ArrayList<String>();
        ArrayList<String> filtros = new ArrayList<String>();
        arquivos.addAll(Arrays.asList("PNG Files", "JPG Files", "JPEG Files"));
        filtros.addAll(Arrays.asList("*.png", "*.jpg", "*.jpeg"));
        this.logo = uploadFile(arquivos, filtros, "Escolha a logo do restaurante");

        if(logo != null) {
            try {
                this.image = ImageIO.read(new File(this.logo.getPath()));
            } catch (Exception e) {
                this.image = null;
                this.logo = null;
                this.logoFile.setText("");
                mensagem(Alert.AlertType.ERROR, "Erro ao ler imagem!");
                return;
            }

            this.logoFile.setText("" + this.logo.getName());
        }
    }

    @FXML
    public void voltar() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_USUARIO);
    }

    @FXML
    public void proximo() {
        if(this.restaurante == null) {
            mensagem(Alert.AlertType.ERROR, "Insira as informações do restaurante para seguir para o próximo passo!");
            return;
        }
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_RESTAURANTE_2, this.restaurante);
    }
}
