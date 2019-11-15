package sample.Controller;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;

public class ControllerBase {
    public void mensagem(Alert.AlertType tipo, String texto){
        Alert alerta = new Alert(tipo);
        alerta.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alerta.setTitle("Informações");
        alerta.setContentText(texto);
        alerta.showAndWait();
    }

    public void setDados(Object dados) {}
}
