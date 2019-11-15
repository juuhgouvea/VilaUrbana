package sample.Controller;

import com.sun.glass.ui.CommonDialogs;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.Model.JDBCRestauranteDAO;
import sample.Model.Restaurante;
import sample.Model.Usuario;
import sample.NavegadorJanelas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class ControllerJanelaCadastrarRestaurante extends ControllerBase {
    @FXML
    private Text logoFile;
    @FXML
    private TextField nomeRestaurante;

    private File logo = null;
    private Restaurante restaurante = null;

    @FXML
    public void cadastrar() {
        if(this.restaurante != null) {
            mensagem(Alert.AlertType.INFORMATION, "Você já cadastrou um restaurante. Prossiga para o próximo passo!");
            return;
        }

        BufferedImage logoImage = null;
        String nomeLogo = "";
        String nome = this.nomeRestaurante.getText();
        this.restaurante = new Restaurante();

        if(this.logo != null) {
            nomeLogo = this.logo.getName();
            this.restaurante.setFotoRestaurante(nomeLogo);

            try {
                logoImage = ImageIO.read(new File(this.logo.getPath()));
            } catch (Exception e) {
                mensagem(Alert.AlertType.ERROR, "Erro ao ler imagem!");
            }
        }

        try {
            boolean verifica = JDBCRestauranteDAO.getInstance().buscarRest(nome);
            if(nome.equals("")){
                System.out.println(nome);
                throw new NullPointerException();
            }
            else if(verifica){
                throw new Exception();
            }

            Usuario usuario = new Usuario();
            usuario.setCodUsuario(1);
            this.restaurante.setNomeRestaurante(nome);
            this.restaurante.setUsuario(usuario);

            if (this.logo != null && logoImage != null) {
                String path = "./resources/ImagensUsuarios/usuario-" +
                        restaurante.getUsuario().getCodUsuario() + "/" + logo.getName();

                ImageIO.write(logoImage, "PNG", new File(path));
            }
;
            this.restaurante = JDBCRestauranteDAO.getInstance().create(this.restaurante);

            if(this.restaurante == null) {
                mensagem(Alert.AlertType.ERROR, "Erro ao cadastrar restaurante");
                return;
            }

            mensagem(Alert.AlertType.CONFIRMATION, "Restaurante Cadastrado com sucesso!");
            this.nomeRestaurante.setText("");
        } catch (NullPointerException e) {
            mensagem(Alert.AlertType.ERROR, "Preencha o campo obrigatório!");
            this.restaurante = null;
        }catch (Exception s){
            mensagem(Alert.AlertType.ERROR, "Este restaurante já foi cadastrado!");
            this.restaurante = null;
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
    public void proximo() {
        if(this.restaurante == null) {
            mensagem(Alert.AlertType.ERROR, "Cadastre um restaurante para seguir para o próximo passo!");
            return;
        }
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_RESTAURANTE_2, this.restaurante);
    }
}
