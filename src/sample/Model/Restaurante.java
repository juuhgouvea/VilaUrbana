package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class Restaurante {
    private int codRestaurante;
    private String nomeRestaurante;
    private String fotoRestaurante;
    private Usuario usuario;
    private ObservableList<Produto> produtos;

    public Restaurante() {
        this.produtos = FXCollections.observableArrayList();
    }

    public int getCodRestaurante() {
        return codRestaurante;
    }

    public void setCodRestaurante(int codRestaurante) {
        this.codRestaurante = codRestaurante;
    }

    public String getNomeRestaurante() {
        return nomeRestaurante;
    }

    public void setNomeRestaurante(String nome) {
        this.nomeRestaurante = nome;
    }

    public String getFotoRestaurante() {
        return fotoRestaurante;
    }

    public void setFotoRestaurante(String logo) {
        this.fotoRestaurante = logo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public ObservableList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ObservableList<Produto> produtos) {
        this.produtos.clear();
        this.produtos.addAll(produtos);
    }

    @Override
    public String toString() {
        return this.nomeRestaurante;
    }
}
