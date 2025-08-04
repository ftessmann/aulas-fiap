package models;

import java.util.Objects;

public class Exercicio {
    private String nome;
    private int xp;
    private String pergunta;
    private String resposta;
    private Boolean acerto;

    public void addXp(Usuario usuario) {
        int xpUser = usuario.getXp();
        var xpAdd = xpUser + getXp();
        usuario.setXp(xpAdd);
    }


    public Exercicio() {
    }

    public Exercicio(String nome, int xp, String pergunta, String resposta, Boolean acerto) {
        this.nome = nome;
        this.xp = xp;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.acerto = acerto;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Boolean getAcerto() {
        return acerto;
    }

    public void setAcerto(Boolean acerto) {
        this.acerto = acerto;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exercicio exercicio = (Exercicio) o;
        return getXp() == exercicio.getXp() && Objects.equals(getNome(), exercicio.getNome()) && Objects.equals(getPergunta(), exercicio.getPergunta()) && Objects.equals(getResposta(), exercicio.getResposta()) && Objects.equals(getAcerto(), exercicio.getAcerto());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getXp(), getPergunta(), getResposta(), getAcerto());
    }

    @Override
    public String toString() {
        return "Exercicio{" +
                "nome='" + nome + '\'' +
                ", xp=" + xp +
                ", pergunta='" + pergunta + '\'' +
                ", resposta='" + resposta + '\'' +
                ", acerto=" + acerto +
                '}';
    }
}
