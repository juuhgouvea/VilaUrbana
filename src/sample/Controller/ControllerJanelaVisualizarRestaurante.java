package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.Model.Produto;
import sample.Model.Restaurante;
import sample.NavegadorJanelas;


public class ControllerJanelaVisualizarRestaurante extends ControllerBase {
    @FXML
    private Text nomeRestaurante;
    @FXML
    private Text descProduto;
    @FXML
    private Text valorProduto;
    @FXML
    private ImageView fotoRestaurante;
    @FXML
    private ImageView fotoProduto;
    @FXML
    private ListView<Produto> ltvProdutos;
    @FXML
    private TextField search;

    @FXML
    public void initialize() {
        nomeRestaurante.setText("");
        descProduto.setText("");
        valorProduto.setText("");
        fotoProduto.setVisible(false);
    }

    @Override
    public void setDados(Object dados) {
        Restaurante restaurante = (Restaurante) dados;
        ltvProdutos.setItems(restaurante.getProdutos());
        nomeRestaurante.setText(restaurante.getNomeRestaurante());
        if(restaurante.getFotoRestaurante() != null) {
            String caminho = "../../../resources/imagensUsuarios/usuario-" +
                    restaurante.getUsuario().getCodUsuario() + "/"
                    + restaurante.getFotoRestaurante();
            try {
                Image image = new Image(caminho);
                fotoRestaurante.setImage(image);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @FXML
    private void mostrarProduto() {
        Produto produto = (Produto) ltvProdutos.getSelectionModel().getSelectedItem();

        if(produto == null) {
            return;
        }

        descProduto.setText("Descrição do Produto: " + produto.getDescProduto());
        valorProduto.setText("Valor: R$ " + produto.getValor());
        fotoProduto.setVisible(true);
    }

    @FXML
    private void voltar() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_HOME);
    }
}
