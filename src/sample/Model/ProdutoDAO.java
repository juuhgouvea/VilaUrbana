package sample.Model;

import java.sql.SQLException;

public interface ProdutoDAO {
   public Produto create (Produto produto) throws SQLException;
   public Produto search(int cod);
}
