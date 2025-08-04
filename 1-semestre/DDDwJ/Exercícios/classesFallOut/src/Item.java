import java.util.Objects;

public class Item {
    private String nomeItem;
    private double peso;
    private String categoria;
    private boolean itemDeMissao;
    private Caps valorCaps;
    private boolean consumivel;

    public Item() {
    }

    public Item(String nomeItem, double peso, String categoria, boolean itemDeMissao, Caps valorCaps, boolean consumivel) {
        this.nomeItem = nomeItem;
        this.peso = peso;
        this.categoria = categoria;
        this.itemDeMissao = itemDeMissao;
        this.valorCaps = valorCaps;
        this.consumivel = consumivel;
    }

    public String getNomeItem() {
        return nomeItem;
    }

    public void setNomeItem(String nomeItem) {
        this.nomeItem = nomeItem;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        this.peso = peso;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public boolean isItemDeMissao() {
        return itemDeMissao;
    }

    public void setItemDeMissao(boolean itemDeMissao) {
        this.itemDeMissao = itemDeMissao;
    }

    public Caps getValorCaps() {
        return valorCaps;
    }

    public void setValorCaps(Caps valorCaps) {
        this.valorCaps = valorCaps;
    }

    public boolean isConsumivel() {
        return consumivel;
    }

    public void setConsumivel(boolean consumivel) {
        this.consumivel = consumivel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Double.compare(getPeso(), item.getPeso()) == 0 && isItemDeMissao() == item.isItemDeMissao() && isConsumivel() == item.isConsumivel() && Objects.equals(getNomeItem(), item.getNomeItem()) && Objects.equals(getCategoria(), item.getCategoria()) && Objects.equals(getValorCaps(), item.getValorCaps());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNomeItem(), getPeso(), getCategoria(), isItemDeMissao(), getValorCaps(), isConsumivel());
    }

    @Override
    public String toString() {
        return "Item{" +
                "consumivel=" + consumivel +
                ", valorCaps=" + valorCaps +
                ", itemDeMissao=" + itemDeMissao +
                ", categoria='" + categoria + '\'' +
                ", peso=" + peso +
                ", nomeItem='" + nomeItem + '\'' +
                '}';
    }
}
