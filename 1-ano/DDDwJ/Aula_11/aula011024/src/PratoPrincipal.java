import java.util.Objects;

public class PratoPrincipal extends ItemCardapio {
    private int tempoPreparo;

    public PratoPrincipal() {
    }

    public PratoPrincipal(String nome, double preco, String descricao, int tempoPreparo) {
        super(nome, preco, descricao);
        this.tempoPreparo = tempoPreparo;
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
                + "Tempo de preparo: \n"
                + this.tempoPreparo
        );
    }

    public int getTempoPreparo() {
        return tempoPreparo;
    }

    public void setTempoPreparo(int tempoPreparo) {
        this.tempoPreparo = tempoPreparo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        PratoPrincipal that = (PratoPrincipal) o;
        return getTempoPreparo() == that.getTempoPreparo();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getTempoPreparo());
    }

    @Override
    public String toString() {
        return "PratoPrincipal{" +
                "tempoPreparo=" + tempoPreparo +
                "} " + super.toString();
    }
}
