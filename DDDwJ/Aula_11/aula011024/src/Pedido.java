import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Pedido {
    private String numeroPedido;
    private List<ItemCardapio> itemPedido = new ArrayList<>();
    private double valorTotal;
    private String status;

    public Pedido() {
    }

    public Pedido(String numeroPedido, List<ItemCardapio> itemPedido, double valorTotal, String status) {
        this.numeroPedido = numeroPedido;
        this.itemPedido = itemPedido;
        this.valorTotal = valorTotal;
        this.status = status;
    }

    public void adicionarItem(ItemCardapio itemCardapio) {
        itemPedido.add(itemCardapio);
    }

    public void removerItem(ItemCardapio itemCardapio) {
        itemPedido.remove(itemCardapio);
    }

    public void calcularValor() {
        valorTotal = 0;
        for (var itemCardapio:itemPedido) {
            this.valorTotal += itemCardapio.getPreco();
        }
    }

    public void alterarStatus(String status) {
        this.status = status;
    }

    public void exibirDetalhes() {
        System.out.println(
            "Detalhes do pedido: \n" +
            "Numero do pedido: \n"
            + this.numeroPedido + "\n"
            + "Itens do pedido: \n"
            + this.itemPedido + "\n"
            + "Status do Pedido: \n"
            + this.status + "\n"
            + "Valor total: \n"
            + this.valorTotal
        );

    }


    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }

    public List<ItemCardapio> getItemPedido() {
        return itemPedido;
    }

    public void setItemPedido(List<ItemCardapio> itemPedido) {
        this.itemPedido = itemPedido;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pedido pedido = (Pedido) o;
        return Double.compare(getValorTotal(), pedido.getValorTotal()) == 0 && Objects.equals(getNumeroPedido(), pedido.getNumeroPedido()) && Objects.equals(getItemPedido(), pedido.getItemPedido()) && Objects.equals(getStatus(), pedido.getStatus());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNumeroPedido(), getItemPedido(), getValorTotal(), getStatus());
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "numeroPedido='" + numeroPedido + '\'' +
                ", itemPedido=" + itemPedido +
                ", valorTotal=" + valorTotal +
                ", status='" + status + '\'' +
                '}';
    }
}
