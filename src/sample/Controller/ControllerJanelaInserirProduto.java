package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sample.Model.*;
import sample.NavegadorJanelas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ControllerJanelaInserirProduto extends ControllerBase {
    @FXML
    private Text fotoFile;
    @FXML
    private TextField nomeProduto;
    @FXML
    private TextField valor;
    @FXML
    private TextArea descProduto;
    @FXML
    private ComboBox<Categoria> cbCategoria;

    private File foto;
    private BufferedImage image;
    private Restaurante restaurante = null;

    @FXML
    public void initialize() {
        cbCategoria.setItems(JDBCCategoriaDAO.getInstance().list());
    }

    @Override
    public void setDados(Object dados) {
        this.restaurante = (Restaurante) dados;
    }

    @FXML
    public void cadastrar(){
        String erros = "";
        String nomeFoto = null;
        String nome = nomeProduto.getText();

        if(this.foto != null){
            try {
                this.image = ImageIO.read(this.foto);
                nomeFoto = this.foto.getName();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if(nomeProduto.getText() == "" || nomeProduto.getText().length() < 2) {
            erros += "\nO campo 'nome do produto' deve ter ao menos 2 caracteres!";
        }

        try {
            float valor = Float.parseFloat(this.valor.getText());

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
        Produto produto = new Produto();
        produto.setNomeProduto(nome);
        produto.setFotoProduto(nomeFoto);
        produto.setValor(Float.parseFloat(valor.getText()));
        produto.setDescProduto(descProduto.getText());
        produto.setCategoria(categoria);
        produto.setRestaurante(this.restaurante);

        try {
            JDBCProdutoDAO.getInstance().create(produto);
            JDBCProdutoDAO.getInstance().addProduto_restaurante(produto);
            this.restaurante.getProdutos().add(produto);

            if(this.image != null) {
                String path = "./resources/ImagensUsuarios/usuario-" +
                        this.restaurante.getUsuario().getCodUsuario() + "/produto-"
                        + produto.getCodProduto() + "-" + produto.getFotoProduto();

                ImageIO.write(image, "PNG", new File(path));
            }

            String sucesso = "Produto adicionado com sucesso!\n\n";
            mensagem(Alert.AlertType.CONFIRMATION, sucesso);

            this.nomeProduto.setText("");
            this.cbCategoria.getSelectionModel().clearSelection();
            this.cbCategoria.setPromptText("Selecione a categoria");
            this.descProduto.setText("");
            this.valor.setText("");
            this.fotoFile.setText("");
            this.foto = null;
            this.image = null;
            nomeFoto = null;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            mensagem(Alert.AlertType.ERROR, "Desculpe, houve um erro ao cadastrar seu produto!");
        }
    }

    @FXML
    public void uploadFoto(){
        ArrayList<String> arquivos = new ArrayList<String>();
        ArrayList<String> filtros = new ArrayList<String>();
        arquivos.addAll(Arrays.asList("PNG Files", "JPG Files", "JPEG Files"));
        filtros.addAll(Arrays.asList("*.png", "*.jpg", "*.jpeg"));
        this.foto  = uploadFile(arquivos, filtros, "Selecione a foto do produto");

        if(this.foto != null) {
            this.fotoFile.setText(this.foto.getName());
        }
    }

    @FXML
    public void voltar() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_VISUALIZAR_RESTAURANTE, this.restaurante);
    }
}
