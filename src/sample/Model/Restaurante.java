package sample.Model;

import java.util.ArrayList;

public class Restaurante {
    private int codRestaurante;
    private String nomeRestaurante;
    private String fotoRestaurante;
    private Usuario usuario;
    private ArrayList<Produto> produtos;

    public Restaurante() {
        this.produtos = new ArrayList();
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

    public ArrayList<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(ArrayList<Produto> produtos) {
        this.produtos.addAll(produtos);
    }
}
