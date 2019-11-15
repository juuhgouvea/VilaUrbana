package sample.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class JDBCProdutoDAO implements ProdutoDAO{
    private static JDBCProdutoDAO instance = null;

    private JDBCProdutoDAO(){}

    public static JDBCProdutoDAO getInstance(){
        if(instance == null){
            instance = new JDBCProdutoDAO();
        }
        return instance;
    }

    @Override
    public Produto create(Produto produto) throws Exception{
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL = "INSERT INTO produtos(nome_produto, foto_produto, valor, desc_produto, cod_categoria) VALUES (?, ?, ?, ?, ?)";

        int id = 0;
        con = FabricaConexao.getConnection();

        pstm = con.prepareStatement(SQL);
        pstm.setString(1, produto.getNomeProduto());
        pstm.setString(2, produto.getFotoProduto());
        pstm.setFloat(3, produto.getValor());
        pstm.setString(4, produto.getDescProduto());
        pstm.setInt(5, produto.getCategoria().getCodCategoria());
        pstm.execute();

        SQL = "SELECT cod_produto FROM produtos ORDER BY cod_produto DESC LIMIT 1";
        pstm = con.prepareStatement(SQL);

        rs = pstm.executeQuery();

        while (rs.next()) {
            id = rs.getInt("cod_produto");
        }

        produto.setCodProduto(id);

        rs.close();
        pstm.close();
        con.close();

        return produto;
    }

}
