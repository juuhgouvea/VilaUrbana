package sample.Model;

import javafx.collections.ObservableList;

public interface CategoriaDAO {
    public Categoria create(Categoria categoria) throws Exception;

    public ObservableList<Categoria> list();
}
