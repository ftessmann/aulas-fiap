public class Produto {
    //Atributos
    private String nome;
    private double preco;
    private String descricao;
    private int quantidade;
    private String categoria;

    // Construtor

    public Produto(String nome, double preco, String descricao, int quantidade, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    public void AdicionarEstoque (int quantidade) {
        this.quantidade += quantidade;
    }

    public void RemoverEstoque (int quantidade) {
        if (this.quantidade - quantidade < 0) {
            System.out.println("Quantidade insuficiente em estoque");
        } else {
            this.quantidade -= quantidade;
        }
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", descricao='" + descricao + '\'' +
                ", quantidade=" + quantidade +
                ", categoria='" + categoria + '\'' +
                '}';
    }
}