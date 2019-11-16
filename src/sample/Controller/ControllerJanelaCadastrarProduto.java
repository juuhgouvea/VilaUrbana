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
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

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

    private File fotoAtual;
    private ArrayList<File> fotos;
    private Restaurante restaurante = null;

    @FXML
    public void initialize() {
        cbCategoria.setItems(JDBCCategoriaDAO.getInstance().list());
        fotos = new ArrayList<>();
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

        if(this.fotoAtual != null){
            this.fotos.add(this.fotoAtual);
            nomeFoto = this.fotoAtual.getName();
        } else {
            this.fotos.add(null);
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
            String sucesso = "Produto adicionado com sucesso!\n\nCaso deseja alterar os dados desse produto basta ir a Home!";
            this.restaurante.getProdutos().add(produto);
            mensagem(Alert.AlertType.CONFIRMATION, sucesso);

            this.nomeProduto.setText("");
            this.cbCategoria.getSelectionModel().clearSelection();
            this.cbCategoria.setPromptText("Selecione a categoria");
            this.descProduto.setText("");
            this.valor.setText("");
            this.fotoFile.setText("");
            this.fotoAtual = null;
            nomeFoto = null;
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
        this.fotoAtual  = uploadFile(arquivos, filtros, "Selecione a foto do produto");

        if(this.fotoAtual != null) {
            this.fotoFile.setText(this.fotoAtual.getName());
        }
    }

    @FXML
    public void concluir() {
        try {
            JDBCRestauranteDAO.getInstance().create(this.restaurante);
            ArrayList<Produto> produtos = new ArrayList<>();
            produtos.addAll(this.restaurante.getProdutos());

            for (int i = 0; i < produtos.size(); i++) {
                if(this.fotos.get(i) == null) {
                    continue;
                }

                BufferedImage image = ImageIO.read(new File(this.fotos.get(i).getPath()));
                String path = "./resources/ImagensUsuarios/usuario-" +
                        this.restaurante.getUsuario().getCodUsuario() + "/produto-"
                        + produtos.get(i).getCodProduto() + "-" + produtos.get(i).getFotoProduto();

                ImageIO.write(image, "PNG", new File(path));
                this.fotos.set(i, new File(path));
            }
        } catch ( SQLException e) {
            if(e.getErrorCode() == 19) {
                mensagem(Alert.AlertType.ERROR, "Restaurante já cadastrado!");
                NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_CADASTRAR_RESTAURANTE_1, this.restaurante);
            }
            return;
        } catch (Exception e) {
            mensagem(Alert.AlertType.ERROR, "Erro ao adicionar produtos no Restaurante!");
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
