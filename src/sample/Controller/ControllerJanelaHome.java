package sample.Controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import sample.Model.JDBCRestauranteDAO;
import sample.Model.Restaurante;
import sample.Model.Timer;
import sample.NavegadorJanelas;

public class ControllerJanelaHome extends ControllerBase {
    @FXML
    private ListView ltvRestaurantes;
    @FXML
    private Text timer;

    private Thread cronometro = null;

    @FXML
    public void initialize() {
        ObservableList<Restaurante> restaurantes = FXCollections.observableArrayList();
        restaurantes.addAll(JDBCRestauranteDAO.getInstance().list());
        ltvRestaurantes.setItems(restaurantes);
        iniciarCronometro();
    }

    @FXML
    public void cadastrarRestaurante() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_RESTAURANTE_1);
    }

    @FXML
    public void editarPerfil() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_EDITAR_PERFIL);
    }

    @FXML
    public void visualizar() {
        Restaurante restaurante = (Restaurante) ltvRestaurantes.getSelectionModel().getSelectedItem();
        if(restaurante == null) {
            mensagem(Alert.AlertType.ERROR, "Selecione um restaurante!");
            return;
        }
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_VISUALIZAR_RESTAURANTE, restaurante);
    }

    @FXML
    public void sair(){
        this.cronometro.stop();
        this.cronometro = null;
        mensagem(Alert.AlertType.INFORMATION, "Seu tempo de permanencia foi: "
                + Timer.getInstance().getTime() + " segundos");
        Timer.getInstance().setTime(0);
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_USUARIO);
    }

    public void iniciarCronometro() {
        Task t =  new Task() {
            @Override
            protected Object call() throws Exception {
                while(Timer.getInstance().isAlive()) {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            timer.setText("" + Timer.getInstance().getTime());
                        }
                    });
                    Thread.sleep(1000);
                }
                return null;
            }
        };

        this.cronometro = new Thread(t);
        this.cronometro.start();
    }
}
