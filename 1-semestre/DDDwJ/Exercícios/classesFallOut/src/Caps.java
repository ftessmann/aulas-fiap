import java.util.Objects;

public class Caps {
    private int quantidade;

    public Caps() {
    }

    public Caps(int quantidade) {
        this.quantidade = quantidade;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Caps caps = (Caps) o;
        return getQuantidade() == caps.getQuantidade();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getQuantidade());
    }

    @Override
    public String toString() {
        return "Caps{" +
                "quantidade=" + quantidade +
                '}';
    }
}
