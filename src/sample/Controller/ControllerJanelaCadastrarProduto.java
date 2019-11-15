package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import sample.Model.*;
import sample.NavegadorJanelas;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class ControllerJanelaCadastrarProduto extends ControllerBase {
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

    private File foto = null;
    private Restaurante restaurante = null;
    private ArrayList<Produto> produtos;

    @FXML
    public void initialize() {
        cbCategoria.setItems(JDBCCategoriaDAO.getInstance().list());
        produtos = new ArrayList<>();
    }

    @Override
    public void setDados(Object dados) {
        this.restaurante = new Restaurante();
        this.restaurante = (Restaurante) dados;
    }

    @FXML
    public void cadastrar(){
        String erros = "";
        BufferedImage fotoImage = null;
        String nomeFoto = null;
        String nome = nomeProduto.getText();

        if(this.foto != null){
            nomeFoto = this.foto.getName();
            try{
                fotoImage = ImageIO.read(new File(this.foto.getPath()));
            }catch (Exception e){
                erros += "Erro ao ler imagem";
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

        try {
            String sucesso = "Produto cadastrado com sucesso!\n\nCaso deseja alterar os dados desse produto basta ir a Home!";
            JDBCProdutoDAO.getInstance().create(produto);
            this.produtos.add(produto);
            mensagem(Alert.AlertType.CONFIRMATION, sucesso);
            this.nomeProduto.setText("");
            this.cbCategoria.getSelectionModel().clearSelection();
            this.cbCategoria.setPromptText("Selecione a categoria");
            this.descProduto.setText("");
            this.valor.setText("");
            this.fotoFile.setText("");
            this.foto = null;
        } catch (Exception e) {
            mensagem(Alert.AlertType.ERROR, "Desculpe, houve um erro ao cadastrar seu produto!");
        }
    }

    @FXML
    public void uploadFoto(){
        FileChooser.ExtensionFilter PNGImages = new FileChooser.ExtensionFilter("PNG Files", "*.png", "*.PNG");
        FileChooser.ExtensionFilter JPGImages = new FileChooser.ExtensionFilter("JPG Files", "*.jpg", "*.JPG");
        FileChooser.ExtensionFilter JPEGImages = new FileChooser.ExtensionFilter("JPEG Files", "*.jpeg", "*.JPEG");

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(PNGImages, JPEGImages, JPGImages);
        fileChooser.setTitle("Escolha a foto do produto");
        this.foto = fileChooser.showOpenDialog(NavegadorJanelas.getStage());
        if(this.foto != null) {
             fotoFile.setText("" + this.foto.getName());
        }
    }

    @FXML
    public void concluir() {
        try {
            JDBCRestauranteDAO.getInstance().addProdutos(this.restaurante, this.produtos);
        } catch (Exception e) {
            mensagem(Alert.AlertType.ERROR, "Erro ao adicionar produtos no Restaurante");
            return;
        }

        mensagem(Alert.AlertType.CONFIRMATION, "Parabéns! Você concluiu as etapas!");
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_USUARIO);
    }

    @FXML
    public void voltar() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_USUARIO);
    }
}
