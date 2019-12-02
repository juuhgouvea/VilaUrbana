package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import sample.Model.Categoria;
import sample.Model.JDBCCategoriaDAO;
import sample.Model.JDBCProdutoDAO;
import sample.Model.Produto;
import sample.NavegadorJanelas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ControllerJanelaAlterarProduto extends ControllerBase {
    @FXML
    private TextField tfNomeProduto;
    @FXML
    private TextField tfValorProduto;
    @FXML
    private TextArea tfDescricaoProduto;
    @FXML
    private ComboBox<Categoria> cbCategoria;
    @FXML
    private ImageView fotoProduto;
    @FXML
    private Text logoFile;

    private Produto produto;
    private File logo;
    private BufferedImage image;

    @FXML
    private void initialize() {
        cbCategoria.setItems(JDBCCategoriaDAO.getInstance().list());
    }

    @Override
    public void setDados(Object dados) {
        this.produto = (Produto) dados;

        tfNomeProduto.setText(produto.getNomeProduto());
        tfDescricaoProduto.setText(produto.getDescProduto());
        tfValorProduto.setText("" + produto.getValor());
        for (Categoria c: cbCategoria.getItems()) {
            if(c.getCodCategoria() == produto.getCategoria().getCodCategoria()) {
                cbCategoria.setValue(c);
            }
        }
        Image image = getImage();
        if(image != null) {
            this.fotoProduto.setImage(image);
        }
    }

    @FXML
    private void alterar() {
        String erros = "";
        if(tfNomeProduto.getText() == "" || tfNomeProduto.getText().length() < 2) {
            erros += "\nO campo 'nome do produto' deve ter ao menos 2 caracteres!";
        }

        try {
            float valor = Float.parseFloat(this.tfValorProduto.getText());

            if(valor <= 0.0) {
                erros += "\nO valor não pode ser negativo";
            }
        } catch (Exception e) {
            erros += "\nO campo valor deve ser um número!";
        }

        if(this.cbCategoria.getSelectionModel().getSelectedItem() == null) {
            erros += "\nSelecione uma categoria!";
        }

        if(!erros.equals("")) {
            erros = "Preencha todos os campos obrigatorios (*)\n" + erros;
            mensagem(Alert.AlertType.ERROR, erros);
            return;
        }

        Categoria categoria = this.cbCategoria.getSelectionModel().getSelectedItem();
        Produto alterado = this.produto;
        alterado.setNomeProduto(tfNomeProduto.getText());
        // produto.setFotoProduto();
        alterado.setValor(Float.parseFloat(tfValorProduto.getText()));
        alterado.setDescProduto(tfDescricaoProduto.getText());
        alterado.setCategoria(categoria);

        try {
            alterado = JDBCProdutoDAO.getInstance().update(alterado);
            if(alterado != null) {
                mensagem(Alert.AlertType.CONFIRMATION, "Produto alterado com sucesso!");
                this.produto = alterado;
                voltar();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void excluir() {
        try {
            boolean sucesso = JDBCProdutoDAO.getInstance().delete(produto);
            if(sucesso) {
                mensagem(Alert.AlertType.CONFIRMATION, "Produto Excluido!");
                this.produto.getRestaurante().getProdutos().remove(produto);
                voltar();
            }
        } catch (SQLException e) {
            mensagem(Alert.AlertType.ERROR, "Não foi possivel excluir produto!");
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private Image getImage() {
        Image image = null;

        if(this.produto.getFotoProduto() == null) {
            return null;
        }

        String caminho = "file:resources/ImagensUsuarios/usuario-";
        caminho += this.produto.getRestaurante().getUsuario().getCodUsuario() + "/";
        caminho += "produto-" + this.produto.getCodProduto() + "-";
        caminho += this.produto.getFotoProduto();

        try {
            image = new Image(caminho);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return image;
    }

    @FXML
    public void uploadFoto(){
        ArrayList<String> arquivos = new ArrayList<String>();
        ArrayList<String> filtros = new ArrayList<String>();
        arquivos.addAll(Arrays.asList("PNG Files", "JPG Files", "JPEG Files"));
        filtros.addAll(Arrays.asList("*.png", "*.jpg", "*.jpeg"));
        this.logo = uploadFile(arquivos, filtros, "Escolha a logo do restaurante");

        if(logo != null) {
            try {
                image = ImageIO.read(new File(logo.getPath()));
            } catch (Exception e) {
                image = null;
                logo = null;
                logoFile.setText("");
                mensagem(Alert.AlertType.ERROR, "Erro ao ler imagem!");
                return;
            }

            this.logoFile.setText("" + logo.getName());
        }
    }

    @FXML
    private void voltar() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_VISUALIZAR_RESTAURANTE, produto.getRestaurante());
    }
}
