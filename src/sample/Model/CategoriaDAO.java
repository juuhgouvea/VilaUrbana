package sample.Model;

import javafx.collections.ObservableList;

import java.sql.SQLException;

public interface CategoriaDAO {
    public Categoria create(Categoria categoria) throws SQLException;
    public ObservableList<Categoria> list();
    public Categoria search(int cod);
}
