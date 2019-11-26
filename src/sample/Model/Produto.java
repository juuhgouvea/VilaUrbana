package sample.Model;

public class Produto {
    private int codProduto;
    private String nomeProduto;
    private String fotoProduto;
    private Categoria categoria;
    private float valor;
    private String descProduto;

    public int getCodProduto(){
        return codProduto;
    }

    public void setCodProduto(int codProduto) { this.codProduto = codProduto; }

    public String getNomeProduto() { return nomeProduto; }

    public void setNomeProduto(String nomeProduto) { this.nomeProduto = nomeProduto; }

    public String getFotoProduto() { return fotoProduto; }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public void setFotoProduto(String fotoProduto) { this.fotoProduto = fotoProduto; }

    public float getValor() { return valor; }

    public void setValor(float valor) { this.valor = valor; }

    public String getDescProduto() { return descProduto; }

    public void setDescProduto(String descProduto) { this.descProduto = descProduto; }

    @Override
    public String toString() { return this.nomeProduto; }
}
