import java.util.Objects;

public class ProdutoFisico extends Produto {
    private double peso;
    private Dimensao dimensao;

    public ProdutoFisico() {
    }

    public ProdutoFisico(String nome, double preco, String descricao, int quantidade, String categoria, Dimensao dimensoes, double peso) {
        super(nome, preco, descricao, quantidade, categoria);
        this.dimensao = dimensoes;
        this.peso = peso;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public Dimensao getDimensao() {
        return dimensao;
    }

    public void setDimensao(Dimensao dimensao) {
        this.dimensao = dimensao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ProdutoFisico that = (ProdutoFisico) o;
        return Double.compare(getPeso(), that.getPeso()) == 0 && Objects.equals(getDimensao(), that.getDimensao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getPeso(), getDimensao());
    }

    @Override
    public String toString() {
        return "ProdutoFisico{" +
                "peso=" + peso +
                ", dimensao=" + dimensao +
                "} " + super.toString();
    }
}

