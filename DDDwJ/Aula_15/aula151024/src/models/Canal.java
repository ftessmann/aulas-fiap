package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SuppressWarnings("ALL")
public class Canal {
    private String nome;
    private Servidor servidor;
    private List<Mensagem> mensagens = new ArrayList<>();

    public void adicionarMensagem(Mensagem mensagem) {
        mensagens.add(mensagem);
    }

    public void removerMensagem(Mensagem mensagem) {
        mensagens.remove(mensagem);
    }


    public void visualizarMensagens() {
        mensagens.forEach(System.out::println);
    }

    // filtra mensagem por user
    public void filtrarMensagensPorUsuario(Usuario usuario) {
        List<Mensagem> mensagensFiltradas = mensagens.stream()
                .filter(mensagem -> mensagem.getUsuario().equals(usuario))
                .toList();
        mensagensFiltradas.forEach(mensagem -> System.out.println(
                "Usuario: " + mensagem.getUsuario().getNome() +
                "\nConteudo: " + mensagem.getConteudo() +
                "\nData: " + mensagem.getData()
                )
        );

    }

    //filtra mensagem por conteudo
    public void filtrarMensagensPorPalavraChave(String palavraChave) {
        List<Mensagem> mensagensFiltradas = mensagens.stream()
                .filter(mensagem -> mensagem.getConteudo().contains(palavraChave))
                .toList();
        mensagensFiltradas.forEach(mensagem -> System.out.println(
                "Usuario: " + mensagem.getUsuario().getNome() +
                "\nConteudo: " + mensagem.getConteudo() +
                "\nData: " + mensagem.getData()
                )
        );

    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Servidor getServidor() {
        return servidor;
    }

    public void setServidor(Servidor servidor) {
        this.servidor = servidor;
    }

    public List<Mensagem> getMensagens() {
        return mensagens;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Canal canal = (Canal) o;
        return Objects.equals(getNome(), canal.getNome()) && Objects.equals(getServidor(), canal.getServidor()) && Objects.equals(getMensagens(), canal.getMensagens());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }

    @Override
    public String toString() {
        return "Canal{" +
                "nome='" + nome + '\'' +
                ", servidor=" + servidor.getNome() +
                ", mensagens=" + mensagens +
                '}';
    }
}
