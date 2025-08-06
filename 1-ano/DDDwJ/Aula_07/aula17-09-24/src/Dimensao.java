import java.util.Objects;

public class Dimensao {
    private double altura;
    private double largura;
    private double comprimento;

    public Dimensao() {
    }

    public Dimensao(double altura, double largura, double comprimento) {
        this.altura = altura;
        this.largura = largura;
        this.comprimento = comprimento;
    }



    public double getAltura() {
        return altura;
    }

    public void setAltura(double altura) {
        this.altura = altura;
    }

    public double getLargura() {
        return largura;
    }

    public void setLargura(double largura) {
        this.largura = largura;
    }

    public double getComprimento() {
        return comprimento;
    }

    public void setComprimento(double comprimento) {
        this.comprimento = comprimento;
    }

    public double CalcularVolume() {
        return altura * largura * comprimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dimensao dimensao = (Dimensao) o;
        return Double.compare(getAltura(), dimensao.getAltura()) == 0 && Double.compare(getLargura(), dimensao.getLargura()) == 0 && Double.compare(getComprimento(), dimensao.getComprimento()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAltura(), getLargura(), getComprimento());
    }

    @Override
    public String toString() {
        return "Dimensao{" +
                "altura=" + altura +
                ", largura=" + largura +
                ", comprimento=" + comprimento +
                '}';
    }
}
