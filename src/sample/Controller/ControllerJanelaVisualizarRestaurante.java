package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import sample.Model.*;
import sample.NavegadorJanelas;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

public class ControllerJanelaVisualizarRestaurante extends ControllerBase {
    @FXML
    private Text nomeRestaurante;
    @FXML
    private Text logoFile;
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
    private TextField tfNomeRestaurante;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnConcluirEdicao;
    @FXML
    private Pane alterarLogo;

    private File logo;
    private BufferedImage image;
    private Restaurante restaurante;

    @FXML
    public void initialize() {
        btnEditar.setVisible(false);
        btnConcluirEdicao.setVisible(false);
        tfNomeRestaurante.setVisible(false);
        alterarLogo.setVisible(false);
        nomeRestaurante.setText("");
        descProduto.setText("");
        valorProduto.setText("");
        fotoProduto.setVisible(false);
    }

    @Override
    public void setDados(Object dados) {
        this.restaurante = (Restaurante) dados;
        Usuario proprietario = this.restaurante.getUsuario();
        Usuario logado = JDBCUsuarioDAO.getInstance().getLogado();
        ltvProdutos.setItems(this.restaurante.getProdutos());
        nomeRestaurante.setText(this.restaurante.getNomeRestaurante());
        logoFile.setText(this.restaurante.getFotoRestaurante());
        this.logo = null;

        if(proprietario.getCodUsuario() == logado.getCodUsuario()) {
            btnEditar.setVisible(true);
        }

        if(this.restaurante.getFotoRestaurante() != null) {
            fotoRestaurante.setImage(getImage(null, true));
        }
    }

    @FXML
    private void mostrarProduto(javafx.scene.input.MouseEvent mouseEvent) {
        Produto produto = (Produto) ltvProdutos.getSelectionModel().getSelectedItem();

        if(produto == null) {
            return;
        }

        if(mouseEvent.getClickCount() == 2) {
            NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_ALTERAR_PRODUTO, produto);
            return;
        }

        descProduto.setText("Descrição do Produto: " + produto.getDescProduto());
        valorProduto.setText("Valor: R$ " + produto.getValor());

        if(produto.getFotoProduto() != null) {
            fotoProduto.setImage(getImage(produto, false));
        } else {
            fotoProduto.setImage(new Image("file:resources/images/noimage.png"));
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
    private void baixarCardapio(ActionEvent av){
        GeradorPDFCardapio geradorPDF = new GeradorPDFCardapio();

        FileChooser fc = new FileChooser();
        Stage window = (Stage) ((Node) av.getSource()).getScene().getWindow();
        File f = fc.showSaveDialog(window);

        try {
            if (f != null) {
                String arq = f.getAbsolutePath();

                geradorPDF.gerarCardapio(arq, this.restaurante.getProdutos());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @FXML
    private void editarRestaurante() {
        nomeRestaurante.setVisible(false);
        tfNomeRestaurante.setVisible(true);
        fotoRestaurante.setVisible(false);
        alterarLogo.setVisible(true);
        tfNomeRestaurante.setText(nomeRestaurante.getText());
        btnConcluirEdicao.setVisible(true);
        btnEditar.setVisible(false);
    }

    @FXML
    private void concluirEdicaoRestaurante() {
        String path = "";
        Restaurante alterado = this.restaurante;
        if(tfNomeRestaurante.getText().equals("")) {
            mensagem(Alert.AlertType.ERROR, "O nome do restaurante deve ser informado!");
            return;
        }

        alterado.setNomeRestaurante(tfNomeRestaurante.getText());

        try {
            alterado = JDBCRestauranteDAO.getInstance().update(alterado);

            if(this.logo != null) {
                path = "./resources/ImagensUsuarios/usuario-" +
                        JDBCUsuarioDAO.getInstance().getLogado().getCodUsuario() + "/" + logo.getName();
                alterado.setFotoRestaurante(this.logo.getName());
            }

            if(alterado != null) {
                ImageIO.write(this.image, "PNG", new File(path));

                File fotoAntiga = new File("./resources/ImagensUsuarios/usuario-" + this.restaurante.getUsuario().getCodUsuario()
                        + "/" + this.restaurante.getFotoRestaurante());
                if((restaurante.getFotoRestaurante() != null || !restaurante.getFotoRestaurante().equals("")) && fotoAntiga.exists()) {
                    fotoAntiga.delete();
                }

                this.restaurante = alterado;
                mensagem(Alert.AlertType.CONFIRMATION, "Restaurante alterado!");
            }
        } catch (SQLException e) {
            mensagem(Alert.AlertType.ERROR, "Um erro ocorreu ao tentar alterar o restaurante!");
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        nomeRestaurante.setVisible(true);
        nomeRestaurante.setText(alterado.getNomeRestaurante());
        tfNomeRestaurante.setVisible(false);
        fotoRestaurante.setVisible(true);
        alterarLogo.setVisible(false);
        tfNomeRestaurante.setText("");
        btnConcluirEdicao.setVisible(false);
        btnEditar.setVisible(true);
    }

    @FXML
    private void voltar() {
        NavegadorJanelas.loadJanela(NavegadorJanelas.JANELA_HOME);
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
}
