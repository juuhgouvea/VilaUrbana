package sample.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import sample.Model.JDBCRestauranteDAO;
import sample.Model.Restaurante;
import sample.NavegadorJanelas;

public class ControllerJanelaHome extends ControllerBase {
    @FXML
    private ListView ltvRestaurantes;

    @FXML
    public void initialize() {
        ObservableList<Restaurante> restaurantes = FXCollections.observableArrayList();
        restaurantes.addAll(JDBCRestauranteDAO.getInstance().list());
        ltvRestaurantes.setItems(restaurantes);
    }

    @FXML
    public void cadastrarRestaurante() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_RESTAURANTE_1);
    }
}
