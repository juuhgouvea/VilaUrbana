package sample.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    public Produto create(Produto produto) throws SQLException {
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL = "INSERT INTO produtos(nome_produto, foto_produto, valor, desc_produto, cod_categoria) VALUES (?, ?, ?, ?, ?)";

        int cod = 0;
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
            cod = rs.getInt("cod_produto");
        }

        produto.setCodProduto(cod);

        rs.close();
        pstm.close();
        con.close();

        return produto;
    }

    @Override
    public Produto search(int cod) {
        Produto produto = null;
        String SQL = "SELECT * FROM produtos WHERE cod_produto = ?";

        try {
            Connection con = FabricaConexao.getConnection();
            ResultSet rs;
            PreparedStatement pstm = con.prepareStatement(SQL);
            pstm.setInt(1, cod);
            rs = pstm.executeQuery();

            if (rs.next()) {
                int cod_categoria = rs.getInt("cod_categoria");
                produto = new Produto();
                produto.setCodProduto(rs.getInt("cod_produto"));
                produto.setNomeProduto(rs.getString("nome_produto"));
                produto.setDescProduto(rs.getString("desc_produto"));
                produto.setValor(rs.getFloat("valor"));
                produto.setFotoProduto(rs.getString("foto_produto"));
                produto.setCategoria(JDBCCategoriaDAO.getInstance().search(cod_categoria));
            }

            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao procurar produto!\n" + e.getMessage());
        }

        return produto;
    }

}
