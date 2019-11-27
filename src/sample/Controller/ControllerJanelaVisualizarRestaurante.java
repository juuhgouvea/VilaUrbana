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

    private Restaurante restaurante;

    @FXML
    public void initialize() {
        nomeRestaurante.setText("");
        descProduto.setText("");
        valorProduto.setText("");
        fotoProduto.setVisible(false);
    }

    @Override
    public void setDados(Object dados) {
        this.restaurante = (Restaurante) dados;
        ltvProdutos.setItems(this.restaurante.getProdutos());
        nomeRestaurante.setText(this.restaurante.getNomeRestaurante());

        if(this.restaurante.getFotoRestaurante() != null) {
            fotoRestaurante.setImage(getImage(null, true));
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

        if(produto.getFotoProduto() != null) {
            fotoProduto.setImage(getImage(produto, false));
        }
        fotoProduto.setVisible(true);
    }

    private Image getImage(Object object, boolean restaurante) {
        Image image = null;
        String caminho = "file:resources/ImagensUsuarios/usuario-";
        caminho += this.restaurante.getUsuario().getCodUsuario() + "/";

        if(restaurante) {
            caminho += this.restaurante.getFotoRestaurante();
        } else {
            caminho += "produto-" + ((Produto) object).getCodProduto() + "-";
            caminho += ((Produto) object).getFotoProduto();
        }

        try {
            image = new Image(caminho);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return image;
    }

    @FXML
    private void voltar() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_HOME);
    }
}
