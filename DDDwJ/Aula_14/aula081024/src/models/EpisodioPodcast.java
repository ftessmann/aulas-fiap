package models;

import java.util.Objects;

public class EpisodioPodcast extends Midia {
    private String nome;
    private String apresentador;
    private String descricao;

    @Override
    public void reproduzir() {
        System.out.println("Reproduzindo episodio: " + this.getTitulo());
        var contador = getContadorReproducao();
        contador++;
        setContadorReproducao(contador);
    }

    public EpisodioPodcast() {
    }

    public EpisodioPodcast(String titulo, int duracao, int contadorReproducao, String nome, String apresentador, String descricao) {
        super(titulo, duracao, contadorReproducao);
        this.nome = nome;
        this.apresentador = apresentador;
        this.descricao = descricao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getApresentador() {
        return apresentador;
    }

    public void setApresentador(String apresentador) {
        this.apresentador = apresentador;
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
        if (!super.equals(o)) return false;
        EpisodioPodcast that = (EpisodioPodcast) o;
        return Objects.equals(getNome(), that.getNome()) && Objects.equals(getApresentador(), that.getApresentador()) && Objects.equals(getDescricao(), that.getDescricao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNome(), getApresentador(), getDescricao());
    }

    @Override
    public String toString() {
        return "EpisodioPodcast{" +
                "nome='" + nome + '\'' +
                ", apresentador='" + apresentador + '\'' +
                ", descricao='" + descricao + '\'' +
                "} " + super.toString();
    }
}
