package sample.Controller;

import com.sun.glass.ui.CommonDialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.Model.JDBCRestauranteDAO;
import sample.Model.JDBCUsuarioDAO;
import sample.Model.Restaurante;
import sample.Model.Usuario;
import sample.NavegadorJanelas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ControllerJanelaCadastrarRestaurante extends ControllerBase {
    private File logo = null;

    @FXML
    private Text logoFile;
    @FXML
    private TextField nomeRestaurante;

    @FXML
    public void cadastrar() {
        BufferedImage logoImage = null;
        String nomeLogo = "";
        String nome = nomeRestaurante.getText();

        if(this.logo != null) {
            nomeLogo = this.logo.getName();
            try {
                logoImage = ImageIO.read(new File(this.logo.getPath()));
            } catch (Exception e) {
                mensagem(Alert.AlertType.ERROR, "Erro ao ler imagem!");
            }
        }

        Restaurante restaurante = new Restaurante();
        Usuario usuario = new Usuario();
        usuario.setId(1);
        restaurante.setNome(nome);
        restaurante.setLogo(nomeLogo);
        restaurante.setUsuario(usuario);

        try {
            boolean verifica = JDBCRestauranteDAO.getInstance().buscarRest(this.nomeRestaurante.getText());

            if(this.nomeRestaurante.getText().equals("")){
                throw new NullPointerException();
            }
            else if(verifica){
                throw new Exception();
            }
            boolean sucesso = JDBCRestauranteDAO.getInstance().create(restaurante);

            if (sucesso) {
                if(logoImage != null){
                    String path = "./resources/ImagensUsuarios/usuario-" + restaurante.getUsuario().getId() + "/" + logo.getName();
                    ImageIO.write(logoImage, "PNG", new File(path));
                }

                mensagem(Alert.AlertType.CONFIRMATION, "Restaurante cadastrado com sucesso!");
            } else {
                mensagem(Alert.AlertType.ERROR, "Erro ao adicionar restaurante!");
            }
        } catch (NullPointerException e) {
            mensagem(Alert.AlertType.ERROR, "Preencha o campo obrigatório!");
        }catch (Exception s){
            mensagem(Alert.AlertType.ERROR, "Este restaurante já foi cadastrado!");
        }
    }

    @FXML
    public void uploadFoto(){
        FileChooser.ExtensionFilter PNGImages = new FileChooser.ExtensionFilter("PNG Files", "*.png", "*.PNG");
        FileChooser.ExtensionFilter JPGImages = new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.JPG");
        FileChooser.ExtensionFilter JPEGImages = new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg", "*.JPEG");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(PNGImages, JPEGImages, JPGImages);
        fileChooser.setTitle("Escolha a logo do restaurante");
        this.logo = fileChooser.showOpenDialog(NavegadorJanelas.getStage());
        if(logo != null) {
            logoFile.setText("" + this.logo.getName());
        }
    }

    @FXML
    public void voltar() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_USUARIO);
    }

    @FXML
    public void proximo() {}
}
