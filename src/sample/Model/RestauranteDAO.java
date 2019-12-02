package sample.Model;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface RestauranteDAO {
    public Restaurante create (Restaurante restaurante) throws SQLException;
    public Restaurante update (Restaurante restaurante) throws SQLException;
    public boolean delete (Restaurante restaurante) throws SQLException;
    public Restaurante search(int cod);
    public ObservableList<Restaurante> list();
}
