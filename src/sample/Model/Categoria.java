package sample.Model;

public class Categoria {
    private int codCategoria;
    private String descCategoria;

    public int getCodCategoria() {
        return codCategoria;
    }

    public void setCodCategoria(int codCategoria) {
        this.codCategoria = codCategoria;
    }

    public String getDescCategoria() {
        return descCategoria;
    }

    public void setDescCategoria(String descCategoria) {
        this.descCategoria = descCategoria;
    }

    @Override
    public String toString() {
        return this.descCategoria;
    }
}
