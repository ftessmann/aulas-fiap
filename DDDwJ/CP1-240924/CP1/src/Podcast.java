import java.util.ArrayList;
import java.util.Objects;

public class Podcast extends Audio{
    private String nomePodcast;
    private String apresentador;
    private String descricao;

    // vazio
    public Podcast() {
    }

    // intermediario
    public Podcast(String nomePodcast, String apresentador, String descricao) {
        this.nomePodcast = nomePodcast;
        this.apresentador = apresentador;
        this.descricao = descricao;
    }

    // completo
    public Podcast(String titulo, double duracao, String nomePodcast, String apresentador, String descricao) {
        super(titulo, duracao);
        this.nomePodcast = nomePodcast;
        this.apresentador = apresentador;
        this.descricao = descricao;
    }

    public String getNomePodcast() {
        return nomePodcast;
    }

    public void setNomePodcast(String nomePodcast) {
        this.nomePodcast = nomePodcast;
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
        Podcast podcast = (Podcast) o;
        return Objects.equals(getNomePodcast(), podcast.getNomePodcast()) && Objects.equals(getApresentador(), podcast.getApresentador()) && Objects.equals(getDescricao(), podcast.getDescricao());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getNomePodcast(), getApresentador(), getDescricao());
    }

    @Override
    public String toString() {
        return "Podcast{" +
                "nomePodcast='" + nomePodcast + '\'' +
                ", apresentador='" + apresentador + '\'' +
                ", descricao='" + descricao + '\'' +
                "} " + super.toString();
    }

}
