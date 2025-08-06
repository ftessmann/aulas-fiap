package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@SuppressWarnings("ALL")
public class Usuario {
    private String nome;
    private UUID userId;
    private List<Servidor> servidores = new ArrayList<>();

    // verifica se o server já foi add para evitar stackoverflow e adiciona o server
    public void adicionarServidor(Servidor servidor) {
        if (!servidores.contains(servidor)) {
            servidores.add(servidor);
            servidor.adicionarUsuario(this);
        }
    }

    public void removerServidor(Servidor servidor) {
        if (servidores.contains(servidor)) {
            servidores.remove(servidor);
            servidor.removerUsuario(this);
        }
    }

    private String getServidoresNomes() {
        List<String> nomesServidores = new ArrayList<>();
        for (Servidor servidor : servidores) {
            nomesServidores.add(servidor.getNome());  // exibe apenas o nome do servidor
        }
        return String.join(", ", nomesServidores);
    }

    public Usuario() {
    }

    public Usuario(String nome, UUID userId, List<Servidor> servidores) {
        this.nome = nome;
        this.userId = userId;
        this.servidores = servidores;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public List<Servidor> getServidores() {
        return servidores;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return getUserId() == usuario.getUserId() && Objects.equals(getNome(), usuario.getNome()) && Objects.equals(getServidores(), usuario.getServidores());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getUserId());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", userId=" + userId +
                ", servidores=" + getServidoresNomes() +  // exibe apenas os nomes dos servidores
                '}';
    }
}
