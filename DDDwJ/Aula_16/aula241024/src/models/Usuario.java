package models;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@SuppressWarnings("ALL")
public class Usuario {
    private String nome;
    private int xp;
    private int nivel;
    private Map<Curso, Integer> progressoCursos = new HashMap<>(); // map cria um dicionario com lista da classe junto com o numero

    public void realizarExercicio(Exercicio exercicio, String respostaUsuario, Curso curso) {
        // 1- verifica a reposta do exercicio
        var acerto = exercicio.avaliarResposta(respostaUsuario);
        // 2 - se acertou, add xp
        if (acerto) {
            System.out.println("Resposta correta"); // 3 - mostra resposta correta
            xp += 10 * exercicio.getDificuldade(); // 4 - multiplica a xp conforme a dificuldade
            verificarNivel(); // 5 - verifica o nivel do usuario
        } else {
            System.out.println("Resposta incorreta");
        }
        var progresso = progressoCursos.get(curso);
        progressoCursos.put(curso, progresso + 10);
    }

    public void visualizarProgresso(Curso curso) {
        var progresso = progressoCursos.get(curso);
        System.out.println("Progresso no curso de " + curso.getIdioma() + ":" + progresso + "%");
    }

    private void verificarNivel() {
        int nivelCalculado = (xp / 100) + 1;
        if (nivelCalculado > nivel) {
            System.out.println("Parabéns, você subiu de nivel");
            nivel = nivelCalculado;
        }
    }

    public Usuario() {
    }

    public Usuario(String nome) {
        this.nome = nome;
        this.xp = 0;
        this.nivel = 1;
    }

    public Usuario(String nome, int xp, int nivel, Map<Curso, Integer> progressoCursos) {
        this.nome = nome;
        this.xp = xp;
        this.nivel = nivel;
        this.progressoCursos = progressoCursos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public Map<Curso, Integer> getProgressoCursos() {
        return progressoCursos;
    }

    public void setProgressoCursos(Map<Curso, Integer> progressoCursos) {
        this.progressoCursos = progressoCursos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return getXp() == usuario.getXp() && getNivel() == usuario.getNivel() && Objects.equals(getNome(), usuario.getNome()) && Objects.equals(getProgressoCursos(), usuario.getProgressoCursos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getXp(), getNivel(), getProgressoCursos());
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "nome='" + nome + '\'' +
                ", xp=" + xp +
                ", nivel=" + nivel +
                ", progressoCursos=" + progressoCursos +
                '}';
    }
}
