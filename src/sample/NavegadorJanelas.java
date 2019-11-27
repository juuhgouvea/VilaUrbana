
//referência do código
//https://gist.github.com/jewelsea/6460130

package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.stage.Stage;
import sample.Controller.ControllerBase;
import sample.Controller.ControllerJanelaPrincipal;
import sample.Model.FabricaConexao;

import java.net.URL;
import java.util.ResourceBundle;

public class NavegadorJanelas {

    public static final String PRINCIPAL = "Views/JanelaPrincipal.fxml";
    public static final String JANELA_CADASTRAR_USUARIO = "Views/cadastrarUsuario.fxml";
    public static final String JANELA_CADASTRAR_RESTAURANTE_1 = "Views/CadastrarRestaurante/Passo1.fxml";
    public static final String JANELA_CADASTRAR_RESTAURANTE_2 = "Views/CadastrarRestaurante/Passo2.fxml";
    public static final String JANELA_EDITAR_PERFIL = "Views/JanelaEditarPerfil.fxml";
    public static final String JANELA_VISUALIZAR_RESTAURANTE = "Views/JanelaVisualizarRestaurante.fxml";
    public static final String JANELA_LOGIN = "Views/telaLogin.fxml";
    public static final String JANELA_HOME = "Views/Home.fxml";
    public static Stage stage = null;

    private static ControllerJanelaPrincipal controladorJanelaPrincipal;

    public static void setControladorPrincipal(ControllerJanelaPrincipal controladorJanelaPrincipal) {
        NavegadorJanelas.controladorJanelaPrincipal = controladorJanelaPrincipal;
    }

    public static Stage getStage() {
        return stage;
    }

    public static void setStage(Stage stage) {
        NavegadorJanelas.stage = stage;
    }

    public static void loadJanela(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader();

            Node janela = loader.load(
                    NavegadorJanelas.class.getResource(fxml));

            controladorJanelaPrincipal.setJanela(janela);
        } catch (Exception e) {
            System.out.println("Erro ao trocar janela: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void loadJanela(String fxml, Object dados) {
        try {
            FXMLLoader loader = new FXMLLoader();

            Node janela = loader.load(
                    NavegadorJanelas.class.getResourceAsStream(fxml));

            ((ControllerBase) loader.getController()).setDados(dados);
            controladorJanelaPrincipal.setJanela(janela);
        } catch (Exception e) {
            System.out.println("Erro ao trocar janela: " + e.getMessage());
            e.printStackTrace();
        }
    }
}