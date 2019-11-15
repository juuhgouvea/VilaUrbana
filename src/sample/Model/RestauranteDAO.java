package sample.Model;

import java.util.ArrayList;

public interface RestauranteDAO {
    public Restaurante create (Restaurante restaurante) throws Exception;
    public ArrayList<Produto> addProdutos(Restaurante restaurante, ArrayList<Produto> produtos) throws Exception;
}
