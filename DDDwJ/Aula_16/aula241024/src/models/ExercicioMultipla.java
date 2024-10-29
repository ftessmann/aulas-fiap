package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExercicioMultipla extends Exercicio{
    private List<String> opcoes = new ArrayList<>();

    public ExercicioMultipla() {
    }

    public ExercicioMultipla(List<String> opcoes) {
        this.opcoes = opcoes;
    }

    public ExercicioMultipla(String pergunta, String respostaCorreta, int dificuldade, List<String> opcoes) {
        super(pergunta, respostaCorreta, dificuldade);
        this.opcoes = opcoes;
    }

    @Override
    public String getTipo() {
        return "Exercicio de multipla escolha";
    }

    public List<String> getOpcoes() {
        return opcoes;
    }

    public void setOpcoes(List<String> opcoes) {
        this.opcoes = opcoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ExercicioMultipla that = (ExercicioMultipla) o;
        return Objects.equals(getOpcoes(), that.getOpcoes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getOpcoes());
    }

    @Override
    public String toString() {
        return "ExercicioMultipla{" +
                "opcoes=" + opcoes +
                "} " + super.toString();
    }
}
