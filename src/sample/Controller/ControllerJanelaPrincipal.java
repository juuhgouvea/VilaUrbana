package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.StackPane;

public class ControllerJanelaPrincipal extends ControllerBase {
    @FXML
    private StackPane Painel;


    public void setJanela(Node node){
        Painel.getChildren().setAll(node);
    }
}
