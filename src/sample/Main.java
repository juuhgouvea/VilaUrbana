package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Controller.ControllerJanelaPrincipal;
import sample.Model.FabricaConexao;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        NavegadorJanelas.setStage(primaryStage);
        primaryStage.setTitle("Vila Urbana");
        primaryStage.getIcons().add(new Image("file:resources/images/VilaUrbana.png"));
        primaryStage.setScene(createScene(loadMainPane()));
        primaryStage.show();
    }

    private Pane loadMainPane() throws IOException {
        FXMLLoader loader = new FXMLLoader();

        Pane mainPane = (Pane) loader.load(
                getClass().getResourceAsStream(
                        NavegadorJanelas.PRINCIPAL
                )
        );

        ControllerJanelaPrincipal controller = loader.getController();

        NavegadorJanelas.setControladorPrincipal(controller);
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_USUARIO);

        return mainPane;
    }

    private Scene createScene(Pane mainPane){
        Scene scene = new Scene(mainPane, 800, 400);
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
