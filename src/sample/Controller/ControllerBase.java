package sample.Controller;

import javafx.scene.control.Alert;

public class ControllerBase {
    public void mensagem(Alert.AlertType tipo, String texto){
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Informações");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }
}
