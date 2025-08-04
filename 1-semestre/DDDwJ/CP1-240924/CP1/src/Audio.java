import java.util.Objects;

public class Audio {
    private String titulo;
    private double duracao;

    public Audio() {
    }

    public Audio(String titulo, double duracao) {
        this.titulo = titulo;
        this.duracao = duracao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public double getDuracao() {
        return duracao;
    }

    public void setDuracao(double duracao) {
        this.duracao = duracao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Audio audio = (Audio) o;
        return Double.compare(getDuracao(), audio.getDuracao()) == 0 && Objects.equals(getTitulo(), audio.getTitulo());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitulo(), getDuracao());
    }

    @Override
    public String toString() {
        return "Audio{" +
                "titulo='" + titulo + '\'' +
                ", duracao=" + duracao +
                '}';
    }
}
