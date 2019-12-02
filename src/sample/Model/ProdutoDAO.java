package sample.Model;

import java.sql.SQLException;
import java.util.List;

public interface ProdutoDAO {
   public Produto create (Produto produto) throws SQLException;
   public Produto update(Produto produto) throws SQLException;
   public boolean delete(Produto produto) throws SQLException;
   public boolean addProduto_restaurante(Produto produto) throws SQLException;
   public Produto search(int cod);
}
