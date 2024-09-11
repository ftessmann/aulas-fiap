public class Main {
    public static void main (String[] args) {
        var carne = new Produto(
                "Filé mignon",
                45,
                20
        );
        var cliente = new Cliente(
                "José",
                1000
        );

        if (cliente.comprarProduto(carne.getPreco()) == 1) {
            if (carne.venderProduto() == 1) {
                System.out.println("Comprou carne");
            } else {
                System.out.println("Sem estoque");
                cliente.adicionarSaldo(carne.getPreco());
            }
        } else {
            System.out.println("Cliente sem Saldo");
        }

        System.out.println(carne);
        System.out.println(cliente);

    }
}
