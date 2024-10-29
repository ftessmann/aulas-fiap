package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
public class Licao {
    private String titulo;
    private List<Exercicio> exercicios = new ArrayList<>();

    public void addExercicio(Exercicio exercicio) {
        exercicios.add(exercicio);
    }

    public void listarExercicios() {
        System.out.println("Exercicios da lição " + titulo + ":");
        for (Exercicio exercicio : exercicios) {
            System.out.println(exercicio.getPergunta());
        }
    }

    public Licao() {
    }

    public Licao(String titulo) {
        this.titulo = titulo;
    }

    public Licao(String titulo, List<Exercicio> exercicios) {
        this.titulo = titulo;
        this.exercicios = exercicios;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<Exercicio> getExercicios() {
        return exercicios;
    }

    public void setExercicios(List<Exercicio> exercicios) {
        this.exercicios = exercicios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Licao licao = (Licao) o;
        return Objects.equals(getTitulo(), licao.getTitulo()) && Objects.equals(getExercicios(), licao.getExercicios());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getExercicios());
    }

    @Override
    public String
    toString() {
        return "Licao{" +
                "titulo='" + titulo + '\'' +
                ", exercicios=" + exercicios +
                '}';
    }
}
