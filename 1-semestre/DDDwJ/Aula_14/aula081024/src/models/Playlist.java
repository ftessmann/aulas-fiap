package models;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Playlist {
    private String nome;
    private List<Midia> midias;
    private int tempoDuracao;

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
    // para cada elemento da lista, soma o tempo de duração da midia
    public void duracaoTotal() {
        this.tempoDuracao = 0;
        midias.forEach(midias -> this.tempoDuracao += midias.getDuracao());
    }

    // seleciona o título dos itens da lista, ordena e exibe um por um
    public void ordenarPorTitulo() {
        midias.stream()
                .map(Midia::getTitulo)
                .sorted()
                .forEach(System.out::println);
    }

    // seleciona a duração dos itens da lista, ordena e exibe um por um
    public void ordenarPorDuracao() {
        midias.stream()
                .map(Midia::getDuracao)
                .sorted()
                .forEach(System.out::println);
    }

    public List<Midia> ordenarPorTipo() {
        return midias.stream()
                .sorted(Comparator.comparing(midia -> midia instanceof Musica ? "Musica" : "Podcast"))
                .collect(Collectors.toList());
    }

    public void mostrarMidiasFiltradasPorTipo() {
        List<Midia> midiasFiltradas = ordenarPorTipo();
        midiasFiltradas.forEach(midia -> {
            String tipo = midia instanceof Musica ? "Música" : "Podcast";
            System.out.println("Tipo: " + tipo + ", Título: " + midia.getTitulo());
        });
    }

    public List<Midia> filtrarMidiasDuracao(int duracaoMaxima) {
        return midias.stream()
                .filter(midias -> midias.getDuracao() <= duracaoMaxima)
                .collect(Collectors.toList());
    }

    public void mostrarMidiasFiltradasDuracao(int duracaoMaxima) {
        List<Midia> midiasFiltradas = filtrarMidiasDuracao(duracaoMaxima);
        for (Midia midia:midiasFiltradas) {
            System.out.println("Título: " + midia.getTitulo());
        }
    }

    public Midia encontrarMaisLonga() {
        return midias.stream()
                .max(Comparator.comparingInt(Midia::getDuracao))
                .orElse(null);
    }

    public Midia encontrarMaisCurta() {
        return midias.stream()
                .min(Comparator.comparingInt(Midia::getDuracao))
                .orElse(null);
    }

    public void mostrarMidiaLonga() {
        Midia midiaLonga = encontrarMaisLonga();
        System.out.println("A mídia mais longa é: " + midiaLonga.getTitulo());
    }

    public void mostrarMidiaCurta() {
            Midia midiaCurta = encontrarMaisCurta();
            System.out.println("A mídia mais curta é: " + midiaCurta.getTitulo());
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

    public int getTempoDuracao() {
        return tempoDuracao;
    }

    public void setTempoDuracao(int tempoDuracao) {
        this.tempoDuracao = tempoDuracao;
    }

    // para listas, não usar set


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Playlist playlist = (Playlist) o;
        return getTempoDuracao() == playlist.getTempoDuracao() && Objects.equals(getNome(), playlist.getNome()) && Objects.equals(getMidias(), playlist.getMidias());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getMidias(), getTempoDuracao());
    }

    @Override
    public String toString() {
        return "Playlist{" +
                "nome='" + nome + '\'' +
                ", midias=" + midias +
                ", tempoDuracao=" + tempoDuracao +
                '}';
    }
}
