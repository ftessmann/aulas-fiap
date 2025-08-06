import java.util.ArrayList;
import java.util.Objects;

public class Dweller {
    private String nome;
    private String id;
    private Vault vault;
    private Faccao faccao;
    private ArrayList<Item> mochila;
    private VaultRoom vaultRoom;
    private Funcao funcao;
    private Caps caps;

    public Dweller() {
    }

    public Dweller(String nome, String id, Vault vault, Faccao faccao, ArrayList<Item> mochila, VaultRoom vaultRoom, Funcao funcao, Caps caps) {
        this.nome = nome;
        this.id = id;
        this.vault = vault;
        this.faccao = faccao;
        this.mochila = mochila;
        this.vaultRoom = vaultRoom;
        this.funcao = funcao;
        this.caps = caps;
    }



    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Vault getVault() {
        return vault;
    }

    public void setVault(Vault vault) {
        this.vault = vault;
    }

    public Faccao getFaccao() {
        return faccao;
    }

    public void setFaccao(Faccao faccao) {
        this.faccao = faccao;
    }


    public VaultRoom getVaultRoom() {
        return vaultRoom;
    }

    public void setVaultRoom(VaultRoom vaultRoom) {
        this.vaultRoom = vaultRoom;
    }

    public Funcao getFuncao() {
        return funcao;
    }

    public void setFuncao(Funcao funcao) {
        this.funcao = funcao;
    }

    public ArrayList<Item> getMochila() {
        return mochila;
    }

    public void setMochila(ArrayList<Item> mochila) {
        this.mochila = mochila;
    }

    public Caps getCaps() {
        return caps;
    }

    public void setCaps(Caps caps) {
        this.caps = caps;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dweller dweller = (Dweller) o;
        return Objects.equals(getNome(), dweller.getNome()) && Objects.equals(getId(), dweller.getId()) && Objects.equals(getVault(), dweller.getVault()) && Objects.equals(getFaccao(), dweller.getFaccao()) && Objects.equals(getMochila(), dweller.getMochila()) && Objects.equals(getVaultRoom(), dweller.getVaultRoom()) && Objects.equals(getFuncao(), dweller.getFuncao()) && Objects.equals(getCaps(), dweller.getCaps());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNome(), getId(), getVault(), getFaccao(), getMochila(), getVaultRoom(), getFuncao(), getCaps());
    }

    @Override
    public String toString() {
        return "Dweller{" +
                "nome='" + nome + '\'' +
                ", id='" + id + '\'' +
                ", vault=" + vault +
                ", faccao=" + faccao +
                ", mochila=" + mochila +
                ", vaultRoom=" + vaultRoom +
                ", funcao=" + funcao +
                ", caps=" + caps +
                '}';
    }
}
