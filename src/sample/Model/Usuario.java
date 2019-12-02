package sample.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Usuario {
    private int codUsuario;
    private String nomeCompleto;
    private String nomeUsuario;
    private String email;
    private String senha;
    private ObservableList<Restaurante> restaurantes;

    public Usuario() {
        restaurantes = FXCollections.observableArrayList();
    }

    public ObservableList<Restaurante> getRestaurantes() {
        return restaurantes;
    }

    public void setRestaurantes(ObservableList<Restaurante> restaurantes) {
        this.restaurantes.clear();
        this.restaurantes.addAll(restaurantes);
    }

    public int getCodUsuario() {
        return this.codUsuario;
    }

    public void setCodUsuario(int codUsuario) { this.codUsuario = codUsuario; }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
