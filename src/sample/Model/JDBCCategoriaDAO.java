package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;

public class JDBCCategoriaDAO implements CategoriaDAO {
    private static JDBCCategoriaDAO instance = null;

    public JDBCCategoriaDAO(){}

    public static JDBCCategoriaDAO getInstance(){
        if(instance == null){
            instance = new JDBCCategoriaDAO();
        }
        return instance;
    }

    @Override
    public Categoria create(Categoria categoria) throws SQLException {
        return null;
    }

    @Override
    public ObservableList<Categoria> list() {
        ObservableList<Categoria> categorias = FXCollections.observableArrayList();
        String sql = "SELECT * FROM categoria";

        try {
            Connection con = FabricaConexao.getConnection();
            ResultSet rs;
            Statement stm = con.createStatement();
            rs = stm.executeQuery(sql);

            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setCodCategoria(rs.getInt("cod_categoria"));
                categoria.setDescCategoria(rs.getString("desc_categoria"));

                categorias.add(categoria);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return categorias;
    }

    @Override
    public Categoria search(int cod) {
        Categoria categoria = null;
        String SQL = "SELECT * FROM categoria WHERE cod_categoria = ?";

        try {
            Connection con = FabricaConexao.getConnection();
            ResultSet rs;
            PreparedStatement pstm = con.prepareStatement(SQL);
            pstm.setInt(1, cod);
            rs = pstm.executeQuery();

            if (rs.next()) {
                categoria = new Categoria();
                categoria.setCodCategoria(rs.getInt("cod_categoria"));
                categoria.setDescCategoria(rs.getString("desc_categoria"));
            }

            rs.close();
            pstm.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao procurar categoria!\n" + e.getMessage());
        }

        return categoria;
    }
}
