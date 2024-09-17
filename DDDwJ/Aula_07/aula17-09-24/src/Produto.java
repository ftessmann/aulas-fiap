import java.util.ArrayList;
import java.util.Objects;

public class Produto {
    //Atributos
    private String nome;
    private String descricao;
    private double preco;;
    private int quantidade;
    private String categoria;
    private ArrayList<Caracteristica> caracteristica = new ArrayList<>();


    //Construtor vazio
    public Produto() {
    }

    // Construtor intermediario
    public Produto(String nome, double preco, String descricao, int quantidade, String categoria) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
        this.quantidade = quantidade;
        this.categoria = categoria;
    }

    // Construtor completo
    public Produto(String nome, String descricao, double preco, int quantidade, String categoria, ArrayList<Caracteristica> caracteristica) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
        this.categoria = categoria;
        this.caracteristica = caracteristica;
    }

    //Getters e Setters
    public String getNome(){
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
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

    public ArrayList<Caracteristica> getCaracteristica() {
        return caracteristica;
    }

    public void setCaracteristica(ArrayList<Caracteristica> caracteristica) {
        this.caracteristica = caracteristica;
    }

    //Funções ou Métodos
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return Double.compare(getPreco(), produto.getPreco()) == 0 && quantidade == produto.quantidade && Objects.equals(getNome(), produto.getNome()) && Objects.equals(getDescricao(), produto.getDescricao()) && Objects.equals(categoria, produto.categoria) && Objects.equals(getCaracteristica(), produto.getCaracteristica());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getDescricao(), getPreco(), quantidade, categoria, getCaracteristica());
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", categoria='" + categoria + '\'' +
                ", caracteristica=" + caracteristica +
                '}';
    }
}