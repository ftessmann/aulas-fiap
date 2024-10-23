package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Licao {
    private String nome;
    private ArrayList<Exercicio> exercicios = new ArrayList<>();

    public void filtrarPorCertas() {
        List<Exercicio> exerciciosCertos = exercicios.stream()
                .filter(Exercicio::getAcerto)
                .toList();
        exerciciosCertos.forEach(System.out::println);
    }


    public void addExercicio(Exercicio exercicio) {
        exercicios.add(exercicio);
    }

    public void removeExercicio(Exercicio exercicio) {
        exercicios.remove(exercicio);
    }

    public void visualizarExercicios() {
        exercicios.forEach(System.out::println);
    }

    public Licao() {
    }

    public Licao(String nome, ArrayList<Exercicio> exercicios) {
        this.nome = nome;
        this.exercicios = exercicios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Exercicio> getExercicios() {
        return exercicios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Licao licao = (Licao) o;
        return Objects.equals(getNome(), licao.getNome()) && Objects.equals(getExercicios(), licao.getExercicios());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getExercicios());
    }

    @Override
    public String toString() {
        return "Licao{" +
                "nome='" + nome + '\'' +
                ", exercicios=" + exercicios +
                '}';
    }
}
