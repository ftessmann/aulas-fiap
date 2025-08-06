import models.EpisodioPodcast;
import models.Musica;
import models.Playlist;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var musica1 = new Musica(
                "Sonho Médio",
                150,
                0,
                "Dead Fish",
                "Sonho Médio",
                "Hardcore"
        );

        var ep1 = new EpisodioPodcast(
                "Ep1",
                3600,
                0,
                "PodJava",
                "JavaMan",
                "Episodio sobre Java"
        );

        var musica2 = new Musica(
                "Killing in the name",
                180,
                0,
                "Rage against the machine",
                "RATM",
                "Rock");

        var ep2 = new EpisodioPodcast(
                "Ep2",
                7200,
                0,
                "Podcast",
                "Podcaster",
                "Ep sobre podcast");

        var playlist = new Playlist("Meus Favoritos", new ArrayList<>());

        playlist.adicionarMidia(musica1);
        playlist.adicionarMidia(ep1);
        playlist.adicionarMidia(ep2);
        playlist.adicionarMidia(musica2);

        playlist.reproduzir();
        playlist.duracaoTotal();

        System.out.println(musica1.getContadorReproducao());

        musica1.reproduzir();
        musica1.reproduzir();

        System.out.println(musica1.getContadorReproducao());

        musica1.reproduzir();

        System.out.println(musica1.getContadorReproducao());

        System.out.println("Tempo total da playlist: " + playlist.getTempoDuracao() + " segundos");

        playlist.ordenarPorTitulo();

        playlist.ordenarPorDuracao();

        playlist.mostrarMidiasFiltradasDuracao(400);

        playlist.mostrarMidiasFiltradasPorTipo();

        playlist.mostrarMidiaCurta();

        playlist.mostrarMidiaLonga();

    }
}
