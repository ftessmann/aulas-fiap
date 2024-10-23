package models;

import java.util.ArrayList;
import java.util.Objects;

public class Curso {
    private String nome;
    private ArrayList<Licao> licoes = new ArrayList<>();

    public void addLicao(Licao licao) {
        licoes.add(licao);
    }

    public void removeLicao(Licao licao) {
        licoes.add(licao);
    }

    public void visualizarLicoes() {
        licoes.forEach(System.out::println);
    }

    public Curso() {
    }

    public Curso(String nome, ArrayList<Licao> licoes) {
        this.nome = nome;
        this.licoes = licoes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Licao> getLicoes() {
        return licoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return Objects.equals(getNome(), curso.getNome()) && Objects.equals(getLicoes(), curso.getLicoes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getLicoes());
    }

    @Override
    public String toString() {
        return "Curso{" +
                "nome='" + nome + '\'' +
                ", licoes=" + licoes +
                '}';
    }
}
