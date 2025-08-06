import java.util.Objects;

public class Vault {
    private String vaultNome;
    private String vaultNumero;
    private String experimento;
    private String definicaoExperimento;
    private Overseer overseer;

    public Vault() {
    }

    public Vault(String vaultNome, String vaultNumero, String experimento, String definicaoExperimento, Overseer overseer) {
        this.vaultNome = vaultNome;
        this.vaultNumero = vaultNumero;
        this.experimento = experimento;
        this.definicaoExperimento = definicaoExperimento;
        this.overseer = overseer;
    }

    public String getVaultNome() {
        return vaultNome;
    }

    public void setVaultNome(String vaultNome) {
        this.vaultNome = vaultNome;
    }

    public String getVaultNumero() {
        return vaultNumero;
    }

    public void setVaultNumero(String vaultNumero) {
        this.vaultNumero = vaultNumero;
    }

    public String getExperimento() {
        return experimento;
    }

    public void setExperimento(String experimento) {
        this.experimento = experimento;
    }

    public String getDefinicaoExperimento() {
        return definicaoExperimento;
    }

    public void setDefinicaoExperimento(String definicaoExperimento) {
        this.definicaoExperimento = definicaoExperimento;
    }

    public Overseer getOverseer() {
        return overseer;
    }

    public void setOverseer(Overseer overseer) {
        this.overseer = overseer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vault vault = (Vault) o;
        return Objects.equals(getVaultNome(), vault.getVaultNome()) && Objects.equals(getVaultNumero(), vault.getVaultNumero()) && Objects.equals(getExperimento(), vault.getExperimento()) && Objects.equals(getDefinicaoExperimento(), vault.getDefinicaoExperimento()) && Objects.equals(getOverseer(), vault.getOverseer());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVaultNome(), getVaultNumero(), getExperimento(), getDefinicaoExperimento(), getOverseer());
    }

    @Override
    public String toString() {
        return "Vault{" +
                "vaultNome='" + vaultNome + '\'' +
                ", vaultNumero='" + vaultNumero + '\'' +
                ", experimento='" + experimento + '\'' +
                ", definicaoExperimento='" + definicaoExperimento + '\'' +
                ", overseer=" + overseer +
                '}';
    }
}
