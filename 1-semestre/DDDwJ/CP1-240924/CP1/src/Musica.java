import java.util.Objects;

public class Musica extends Audio {
    private String artista;
    private String album;
    private String genero;

    // vazio
    public Musica() {
    }

    // intermediario
    public Musica(String artista, String album, String genero) {
        this.artista = artista;
        this.album = album;
        this.genero = genero;
    }

    // completo
    public Musica(String titulo, double duracao, String artista, String album, String genero) {
        super(titulo, duracao);
        this.artista = artista;
        this.album = album;
        this.genero = genero;
    }

    public String getArtista() {
        return artista;
    }

    public void setArtista(String artista) {
        this.artista = artista;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Musica musica = (Musica) o;
        return Objects.equals(getArtista(), musica.getArtista()) && Objects.equals(getAlbum(), musica.getAlbum()) && Objects.equals(getGenero(), musica.getGenero());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getArtista(), getAlbum(), getGenero());
    }

    @Override
    public String toString() {
        return "Musica{" +
                "artista='" + artista + '\'' +
                ", album='" + album + '\'' +
                ", genero='" + genero + '\'' +
                "} " + super.toString();
    }

}
