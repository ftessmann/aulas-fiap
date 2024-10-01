import java.util.Objects;

public class Bebida extends ItemCardapio {
    private boolean gelado;

    public Bebida() {
    }

    public Bebida(String nome, double preco, String descricao, boolean gelado) {
        super(nome, preco, descricao);
        this.gelado = gelado;
    }

    @Override
    public void exibirInfos() {

        System.out.println(
                "Detalhes do item: \n"
                        + "Nome do item: \n"
                        + getNome() + "\n"
                        + "Descrição do item: \n"
                        + getDescricao() + "\n"
                        + "Preço do item: \n"
                        + getPreco() + "\n"
                        + "Produto resfriado: \n"
                        + this.gelado
        );
    }

    public boolean isGelado() {
        return gelado;
    }

    public void setGelado(boolean gelado) {
        this.gelado = gelado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Bebida bebida = (Bebida) o;
        return isGelado() == bebida.isGelado();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isGelado());
    }

    @Override
    public String toString() {
        return "Bebidas{" +
                "gelado=" + gelado +
                "} " + super.toString();
    }
}
