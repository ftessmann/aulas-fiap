package models;

import java.util.List;
import java.util.Objects;

public class Playlist {
    private String nome;
    private List<Midia> midias;

    public void adicionarMidia(Midia midia) {
        midias.add(midia);
    }

    public void removerMidia(Midia midia) {
        midias.remove(midia);
    }

    public void reproduzir() {
        System.out.println("Reproduzindo playlist: " + this.nome);
        for (var midia:midias) {
            midia.reproduzir();
            System.out.println("---------------------------------------");
        }
    }

    public Playlist(){
    }

    public Playlist(String nome, List<Midia> midias) {
        this.nome = nome;
        this.midias = midias;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Midia> getMidias() {
        return midias;
    }

    // para listas, não usar set

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return Objects.equals(getNome(), playlist.getNome()) && Objects.equals(getMidias(), playlist.getMidias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getMidias());
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "nome='" + nome + '\'' +
                ", midias=" + midias +
                '}';
    }
}
