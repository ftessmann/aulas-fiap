package models;

import java.util.Objects;

public abstract class Midia {
    private String titulo;
    private int duracao;
    private int contadorReproducao;


    public void reproduzir() {
        System.out.println("Reproduzindo: " + this.titulo);
    }

    public Midia() {
    }

    public Midia(String titulo, int duracao, int contadorReproducao) {
        this.titulo = titulo;
        this.duracao = duracao;
        this.contadorReproducao = contadorReproducao;
    }

    public int getContadorReproducao() {
        return contadorReproducao;
    }

    public void setContadorReproducao(int contadorReproducao) {
        this.contadorReproducao = contadorReproducao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Midia midia = (Midia) o;
        return getDuracao() == midia.getDuracao() && Objects.equals(getTitulo(), midia.getTitulo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getDuracao());
    }

    @Override
    public String toString() {
        return "Midia{" +
                "titulo='" + titulo + '\'' +
                ", duracao=" + duracao +
                '}';
    }
}
