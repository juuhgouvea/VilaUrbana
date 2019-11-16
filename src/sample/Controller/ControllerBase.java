package sample.Controller;

import javafx.scene.control.Alert;
import javafx.scene.layout.Region;
import javafx.stage.FileChooser;
import sample.NavegadorJanelas;

import java.io.File;
import java.util.ArrayList;

public class ControllerBase {
    public void setDados(Object dados) {}

    public void mensagem(Alert.AlertType tipo, String texto){
        Alert alerta = new Alert(tipo);
        alerta.setTitle("Informações");
        alerta.setContentText(texto);
        alerta.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        alerta.showAndWait();
    }

    public File uploadFile(ArrayList<String> arquivos, ArrayList<String> filtros, String titulo) {
        File file = null;
        FileChooser fileChooser = new FileChooser();
        for(int i = 0; i < arquivos.size(); i++) {
            FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(arquivos.get(i),
                    filtros.get(i).toLowerCase(), filtros.get(i).toUpperCase());
            fileChooser.getExtensionFilters().add(extensionFilter);
        }

        fileChooser.setTitle(titulo);
        file = fileChooser.showOpenDialog(NavegadorJanelas.getStage());

        return file;
    }
}
