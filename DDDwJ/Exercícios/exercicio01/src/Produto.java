public class Produto {
    private String nome;
    private double preco;
    private int quantidadeEstoque;

    public Produto(String nome, double preco, int quantidadeEstoque) {
        this.nome = nome;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque(int quantidadeEstoque) {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int venderProduto() {
        if (this.quantidadeEstoque > 0) {
            this.quantidadeEstoque--;
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public String toString() {
        return "\nEstoque é: " + quantidadeEstoque;
    }
}
