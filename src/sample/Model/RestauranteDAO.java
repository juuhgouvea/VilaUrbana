package sample.Model;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface RestauranteDAO {
    public Restaurante create (Restaurante restaurante) throws SQLException;
    public ObservableList<Restaurante> list();
}
