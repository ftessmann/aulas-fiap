package models;

import java.util.ArrayList;
import java.util.Objects;

public class Usuario {
    private String nome;
    private ArrayList<Curso> cursos = new ArrayList<>();
    private int xp;


    public void addCurso(Curso curso) {
        cursos.add(curso);
    }

    public void removeCurso(Curso curso) {
        cursos.remove(curso);
    }

    public void visualizarCursos() {
        cursos.forEach(System.out::println);
    }

    public Usuario() {
    }

    public Usuario(String nome, ArrayList<Curso> cursos, int xp) {
        this.nome = nome;
        this.cursos = cursos;
        this.xp = xp;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public ArrayList<Curso> getCursos() {
        return cursos;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return getXp() == usuario.getXp() && Objects.equals(getNome(), usuario.getNome()) && Objects.equals(getCursos(), usuario.getCursos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getCursos(), getXp());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", cursos=" + cursos +
                ", xp=" + xp +
                '}';
    }
}
