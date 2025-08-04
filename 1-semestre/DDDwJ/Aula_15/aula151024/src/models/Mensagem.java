package models;

import java.util.Date;
import java.util.Objects;

@SuppressWarnings("ALL")
public class Mensagem {
    private String conteudo;
    private Usuario usuario;
    private Date data;

    public Mensagem() {
    }

    public Mensagem(String conteudo, Usuario usuario, Date data) {
        this.conteudo = conteudo;
        this.usuario = usuario;
        this.data = data;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mensagem mensagem = (Mensagem) o;
        return Objects.equals(getConteudo(), mensagem.getConteudo()) && Objects.equals(getUsuario(), mensagem.getUsuario()) && Objects.equals(getData(), mensagem.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getConteudo());
    }

    @Override
    public String toString() {
        return "Mensagem{" +
                "conteudo='" + conteudo + '\'' +
                ", autor=" + usuario +
                ", data=" + data +
                '}';
    }
}
