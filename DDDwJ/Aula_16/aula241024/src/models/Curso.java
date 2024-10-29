package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
public class Curso {
    private String idioma;
    private List<Licao> licoes = new ArrayList<>();

    public void addLicao(Licao licao) {
        licoes.add(licao);
    }

    public void removeLicao(Licao licao) {
        licoes.remove(licao);
    }

    public void listarLicoes() {
        System.out.println("Lições do curso de " + idioma + ":");
        for (Licao licao : licoes) {
            System.out.println(licao.getTitulo());
        }
    }

    public Curso() {
    }

    public Curso(String idioma) {
        this.idioma = idioma;
    }

    public Curso(String idioma, List<Licao> licoes) {
        this.idioma = idioma;
        this.licoes = licoes;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public List<Licao> getLicoes() {
        return licoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Curso curso = (Curso) o;
        return Objects.equals(getIdioma(), curso.getIdioma()) && Objects.equals(getLicoes(), curso.getLicoes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIdioma(), getLicoes());
    }

    @Override
    public String toString() {
        return "Curso{" +
                "idioma='" + idioma + '\'' +
                ", licoes=" + licoes +
                '}';
    }
}
