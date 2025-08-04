import java.util.ArrayList;
import java.util.Objects;

public class Overseer extends Dweller {
    private boolean aberturaPorta;
    private boolean sabeExperimento;

    public Overseer() {
    }

    // Construtor intermediário
    public Overseer(boolean aberturaPorta, boolean sabeExperimento) {
        this.aberturaPorta = aberturaPorta;
        this.sabeExperimento = sabeExperimento;
    }

    public Overseer(String nome, String id, Vault vault, Faccao faccao, ArrayList<Item> mochila, VaultRoom vaultRoom, Funcao funcao, Caps caps, boolean aberturaPorta, boolean sabeExperimento) {
        super(nome, id, vault, faccao, mochila, vaultRoom, funcao, caps);
        this.aberturaPorta = aberturaPorta;
        this.sabeExperimento = sabeExperimento;
    }

    public boolean isAberturaPorta() {
        return aberturaPorta;
    }

    public void setAberturaPorta(boolean aberturaPorta) {
        this.aberturaPorta = aberturaPorta;
    }

    public boolean isSabeExperimento() {
        return sabeExperimento;
    }

    public void setSabeExperimento(boolean sabeExperimento) {
        this.sabeExperimento = sabeExperimento;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Overseer overseer = (Overseer) o;
        return isAberturaPorta() == overseer.isAberturaPorta() && isSabeExperimento() == overseer.isSabeExperimento();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), isAberturaPorta(), isSabeExperimento());
    }

    @Override
    public String toString() {
        return "Overseer{" +
                "aberturaPorta=" + aberturaPorta +
                ", sabeExperimento=" + sabeExperimento +
                "} " + super.toString();
    }
}

