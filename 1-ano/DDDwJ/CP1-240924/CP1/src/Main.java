import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        var musica1 = new Musica();
        musica1.setArtista("Snoop Dogg");
        musica1.setGenero("HipHop");
        musica1.setTitulo("Still DRE");
        musica1.setDuracao(240); // tempo em segundos
        musica1.setAlbum("Up in Smoke");

        var musica2 = new Musica();
        musica2.setArtista("The Beatles");
        musica2.setGenero("Rock");
        musica2.setTitulo("Let it be");
        musica2.setDuracao(180);
        musica2.setAlbum("Let it be");

        var podcast1 = new Podcast();
        podcast1.setNomePodcast("Flow");
        podcast1.setApresentador("Igor3K");
        podcast1.setDuracao(7200);
        podcast1.setTitulo("Flow Debate");
        podcast1.setDescricao("Porradaria ao vivo");

        var podcast2 = new Podcast();
        podcast2.setNomePodcast("Inteligencia");
        podcast2.setApresentador("Vilela");
        podcast2.setDuracao(7200);
        podcast2.setTitulo("Sacani");
        podcast2.setDescricao("Entrevista Serjão dos Foguete");

        var playlist1 = new Playlist();

        System.out.println("Adiciona musicas e podcasts na playlist");
        playlist1.getMusica().add(musica1);
        playlist1.getMusica().add(musica2);
        playlist1.getPodcast().add(podcast1);
        playlist1.getPodcast().add(podcast2);

        System.out.println(playlist1.toString());

        System.out.println("Remove musica e podcast da playlist");
        playlist1.getPodcast().remove(podcast1);
        playlist1.getMusica().remove(musica1);

        System.out.println(playlist1.toString());

    }
}
