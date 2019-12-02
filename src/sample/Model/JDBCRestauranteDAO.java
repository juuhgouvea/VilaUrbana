package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class JDBCRestauranteDAO implements RestauranteDAO {
    private static JDBCRestauranteDAO instance = null;
    private ObservableList<Restaurante> restaurantes;

    private JDBCRestauranteDAO(){
        this.restaurantes = FXCollections.observableArrayList();
    }

    public static JDBCRestauranteDAO getInstance() {
        if(instance == null) {
            instance = new JDBCRestauranteDAO();
        }

        return instance;
    }

    @Override
    public Restaurante create(Restaurante restaurante) throws SQLException {
        Connection con;
        ResultSet rs;
        PreparedStatement pstm;
        String SQL = "INSERT INTO restaurantes(nome_restaurante, foto_restaurante, cod_usuario) VALUES (?, ?, ?)";

        int cod = 0;

        con = FabricaConexao.getConnection();

        pstm = con.prepareStatement(SQL);
        pstm.setString(1, restaurante.getNomeRestaurante());
        pstm.setString(2, restaurante.getFotoRestaurante());
        pstm.setInt(3, restaurante.getUsuario().getCodUsuario());
        pstm.execute();

        SQL = "SELECT cod_restaurante FROM restaurantes ORDER BY cod_restaurante DESC LIMIT 1";
        pstm = con.prepareStatement(SQL);

        rs = pstm.executeQuery();

        while (rs.next()) {
            cod = rs.getInt("cod_restaurante");
        }
        restaurante.setCodRestaurante(cod);

        SQL = "INSERT INTO restaurantes_has_produtos (cod_restaurante, cod_produto) VALUES (?, ?)";
        pstm = con.prepareStatement(SQL);

        for (Produto p : restaurante.getProdutos()) {
            JDBCProdutoDAO.getInstance().create(p);
            pstm.setInt(1, restaurante.getCodRestaurante());
            pstm.setInt(2, p.getCodProduto());

            pstm.execute();
        }

        rs.close();
        pstm.close();
        con.close();

        return restaurante;
    }

    @Override
    public Restaurante update(Restaurante restaurante) throws SQLException {
        Connection con = FabricaConexao.getConnection();
        String SQL = "UPDATE restaurantes SET nome_restaurante = ?, foto_restaurante = ? WHERE cod_restaurante = ?";
        PreparedStatement pstm = con.prepareStatement(SQL);

        pstm.setString(1, restaurante.getNomeRestaurante());
        pstm.setString(2, restaurante.getFotoRestaurante());
        pstm.setInt(3, restaurante.getCodRestaurante());

        boolean failed = pstm.execute();

        if(!failed) {
            return restaurante;
        }

        pstm.close();
        con.close();

        return null;
    }

    @Override
    public ObservableList<Restaurante> list() {
        String SQL = "SELECT * FROM restaurantes";

        try {
            Connection con = FabricaConexao.getConnection();
            PreparedStatement pstm = con.prepareStatement(SQL);
            ResultSet rs;
            rs = pstm.executeQuery();

            this.restaurantes.clear();
;
            while (rs.next()) {
                int cod_usuario = rs.getInt("cod_usuario");
                Restaurante restaurante = new Restaurante();
                restaurante.setCodRestaurante(rs.getInt("cod_restaurante"));
                restaurante.setNomeRestaurante(rs.getString("nome_restaurante"));
                restaurante.setFotoRestaurante(rs.getString("foto_restaurante"));
                restaurante.setUsuario(JDBCUsuarioDAO.getInstance().search(cod_usuario));

                SQL = "SELECT cod_produto FROM restaurantes_has_produtos WHERE cod_restaurante = ?";
                pstm = con.prepareStatement(SQL);
                pstm.setInt(1, restaurante.getCodRestaurante());
                ResultSet rsProdutos = pstm.executeQuery();

                while(rsProdutos.next()) {
                    int codProduto = rsProdutos.getInt("cod_produto");
                    Produto p = JDBCProdutoDAO.getInstance().search(codProduto);
                    p.setRestaurante(restaurante);
                    restaurante.getProdutos().add(p);
                }

                rsProdutos.close();
                this.restaurantes.add(restaurante);
            }

            rs.close();
            con.close();
        } catch (Exception e) {
            System.out.println("Erro ao listar restaurantes! " + e.getMessage());
            e.printStackTrace();
        }
        return restaurantes;
    }

    public ObservableList<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public void setRestaurantes(ObservableList<Restaurante> restaurantes) {
        this.restaurantes.clear();
        this.restaurantes.addAll(restaurantes);
    }
}
