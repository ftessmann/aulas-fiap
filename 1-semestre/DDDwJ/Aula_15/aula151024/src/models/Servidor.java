package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class Servidor {
    private String nome;
    private List<Canal> canais = new ArrayList<>();
    private List<Usuario> usuarios = new ArrayList<>();

    // adiciona canais no servidor
    public void adicionarCanal(Canal canal) {
        canais.add(canal);
    }

    // remove canais do servidor
    public void removerCanal(Canal canal) {
        canais.remove(canal);
    }

    // verifica se o user já foi add para evitar stackoverflow e adiciona user no servidor
    public void adicionarUsuario(Usuario usuario) {
        if (!usuarios.contains(usuario)) {
            usuarios.add(usuario);
            usuario.adicionarServidor(this);
        }
    }

    // remove user no servidor
    public void removerUsuario(Usuario usuario) {
        if (usuarios.contains(usuario)) {
            usuarios.remove(usuario);
            usuario.removerServidor(this);
        }
    }

    // visualiza usuarios no servidor
    public void visualizarUsuarios() {
        usuarios.forEach(System.out::println);
    }

    public List<Mensagem> filtrarMensagensPorCanal(Canal canal) {
        return canal.getMensagens();
    }

    public int contarMensagensPorUsuario(Usuario usuario) {
         int quantidadeMensagens = (int) canais.stream()
                .flatMap(canal -> canal.getMensagens().stream())
                .filter(mensagem -> mensagem.getUsuario().equals(usuario))
                .count();
        System.out.println(
                "Usuário: " + usuario.getNome() +
                "\nMensagens enviadas: " + quantidadeMensagens
        );
         return quantidadeMensagens;
    }

    // agrupando mensagens por usuario
    public Map<Usuario, List<Mensagem>> agruparMensagensPorUsuario() {
        Map<Usuario, List<Mensagem>> mensagensPorUsuario = canais.stream()
                .flatMap(canal -> canal.getMensagens().stream())
                .collect(Collectors.groupingBy(Mensagem::getUsuario));
        mensagensPorUsuario.forEach((usuario, mensagens) -> {
            System.out.println("Usuário: " + usuario.getNome());
            mensagens.forEach(mensagem ->
                    System.out.println("    Conteúdo: " + mensagem.getConteudo() +
                            ", Data e Hora: " + mensagem.getData())
            );
        });

        return mensagensPorUsuario;
    }

    public int contarMensagensNoServidor() {
        int totalMensagens = canais.stream()
                .mapToInt(canal -> canal.getMensagens().size()) // conta as mensagens em cada canal
                .sum(); // soma as quantidades de cada canal

        System.out.println("Total de mensagens no servidor '" + getNome() + "': " + totalMensagens);

        return totalMensagens;
    }

    // metodo privado para acesso somente na classe
    private String getUsuariosNomes() {
        List<String> nomesUsuarios = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            nomesUsuarios.add(usuario.getNome());  // exibe apenas o nome do user para toString
        }
        return String.join(", ", nomesUsuarios);
    }

    // metodo privado para acesso somente na classe
    private String getCanaisNomes() {
        List<String> nomesCanais = new ArrayList<>();
        for (Canal canal : canais) {
            nomesCanais.add(canal.getNome());  // exibe apenas o nome do canal para toString
        }
        return String.join(", ", nomesCanais);
    }


    public Servidor() {
    }

    public Servidor(String nome, List<Canal> canais, List<Usuario> usuarios) {
        this.nome = nome;
        this.canais = canais;
        this.usuarios = usuarios;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Canal> getCanais() {
        return canais;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Servidor servidor = (Servidor) o;
        return Objects.equals(getNome(), servidor.getNome()) && Objects.equals(getCanais(), servidor.getCanais()) && Objects.equals(getUsuarios(), servidor.getUsuarios());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome());
    }

    @Override
    public String toString() {
        return "Servidor{" +
                "nome='" + nome + '\'' +
                ", canais=" + getCanaisNomes() +
                ", usuarios=" + getUsuariosNomes() +  // Exibe apenas os nomes dos usuários
                '}';
    }
}
