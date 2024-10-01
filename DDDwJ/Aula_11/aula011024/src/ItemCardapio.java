import java.util.Objects;

public class ItemCardapio {
    private String nome;
    private double preco;
    private String descricao;


    public void exibirInfos() {
        System.out.println(
                "Detalhes do item: \n"
                + "Nome do item: \n"
                + this.nome + "\n"
                + "Descrição do item: \n"
                + this.descricao + "\n"
                + "Preço do item: \n"
                + this.preco
        );
    }

    public ItemCardapio() {
    }

    public ItemCardapio(String nome, double preco, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemCardapio that = (ItemCardapio) o;
        return Double.compare(getPreco(), that.getPreco()) == 0 && Objects.equals(getNome(), that.getNome()) && Objects.equals(getDescricao(), that.getDescricao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getPreco(), getDescricao());
    }

    @Override
    public String toString() {
        return "ItemCardapio{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", descricao='" + descricao + '\'' +
                '}';
    }
}
