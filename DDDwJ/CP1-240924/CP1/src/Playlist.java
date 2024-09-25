import java.util.ArrayList;
import java.util.Objects;

public class Playlist{
    private ArrayList<Musica> musica = new ArrayList<>();
    private ArrayList<Podcast> podcast = new ArrayList<>();

    public Playlist() {
    }

    public Playlist(ArrayList<Musica> musica, ArrayList<Podcast> podcast) {
        this.musica = musica;
        this.podcast = podcast;
    }


    public ArrayList<Musica> getMusica() {
        return musica;
    }

    public void setMusica(ArrayList<Musica> musica) {
        this.musica = musica;
    }

    public ArrayList<Podcast> getPodcast() {
        return podcast;
    }

    public void setPodcast(ArrayList<Podcast> podcast) {
        this.podcast = podcast;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(getMusica(), playlist.getMusica()) && Objects.equals(getPodcast(), playlist.getPodcast());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMusica(), getPodcast());
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "musica=" + musica +
                ", podcast=" + podcast +
                '}';
    }

}
