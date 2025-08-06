package models;

import java.util.Objects;

@SuppressWarnings("ALL")
public abstract class Exercicio {
    private String pergunta;
    private String respostaCorreta;
    private int dificuldade;

    public boolean avaliarResposta(String respostaUsuario) {
        return respostaCorreta.equals(respostaUsuario.trim());
    }

    public Exercicio() {
    }

    public String getTipo() {
        return "Exercicio";
    }

    public Exercicio(String pergunta, String respostaCorreta, int dificuldade) {
        this.pergunta = pergunta;
        this.respostaCorreta = respostaCorreta;
        this.dificuldade = dificuldade;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getRespostaCorreta() {
        return respostaCorreta;
    }

    public void setRespostaCorreta(String respostaCorreta) {
        this.respostaCorreta = respostaCorreta;
    }

    public int getDificuldade() {
        return dificuldade;
    }

    public void setDificuldade(int dificuldade) {
        this.dificuldade = dificuldade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercicio exercicio = (Exercicio) o;
        return getDificuldade() == exercicio.getDificuldade() && Objects.equals(getPergunta(), exercicio.getPergunta()) && Objects.equals(getRespostaCorreta(), exercicio.getRespostaCorreta());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPergunta(), getRespostaCorreta(), getDificuldade());
    }

    @Override
    public String
    toString() {
        return "Exercicio{" +
                "pergunta='" + pergunta + '\'' +
                ", respostaCorreta='" + respostaCorreta + '\'' +
                ", dificuldade=" + dificuldade +
                '}';
    }
}
