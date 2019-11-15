package sample.Model;

public class Usuario {
    private int codUsuario;
    private String nomeCompleto;
    private String nomeUsuario;
    private String email;
    private String senha;

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
