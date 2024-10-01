import models.EpisodioPodcast;
import models.Midia;
import models.Musica;
import models.Playlist;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var musica1 = new Musica(
                "Sonho Médio",
                150,
                "Dead FIsh",
                "Sonho Médio",
                "Hardcore"
        );

        var ep1 = new EpisodioPodcast(
                "Ep1",
                3600,
                "PodJava",
                "JavaMan",
                "Episodio sobre Java"
        );

        var playlist = new Playlist("Meus Favoritos", new ArrayList<>());

        playlist.adicionarMidia(musica1);
        playlist.adicionarMidia(ep1);

        playlist.reproduzir();
    }
}
