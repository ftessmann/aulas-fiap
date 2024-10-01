import java.util.Scanner;

public class Main {
    public static void main (String[] args) {

        var scan = new Scanner(System.in);

        var item1 = new ItemCardapio();
        item1.setNome("Burgão");
        item1.setPreco(35.50);
        item1.setDescricao("Burgão suculento com queijo");

        var bebida1 = new Bebida();
        bebida1.setNome("Coca Cola");
        bebida1.setDescricao("Coquinha gelada");
        bebida1.setPreco(6.5);
        bebida1.setGelado(true);

        var pratoPrincipal = new PratoPrincipal();
        pratoPrincipal.setNome("Batata frita");
        pratoPrincipal.setDescricao("Batata crocante e salgadinha");
        pratoPrincipal.setPreco(20);
        pratoPrincipal.setTempoPreparo(300);

        var pedido = new Pedido();
        pedido.setNumeroPedido("1234");
        pedido.setStatus("Em preparação");

        pedido.adicionarItem(item1);
        pedido.adicionarItem(bebida1);
        pedido.adicionarItem(pratoPrincipal);

        pratoPrincipal.exibirInfos();
        bebida1.exibirInfos();

        pedido.exibirDetalhes();

        pedido.calcularValor();

        pedido.exibirDetalhes();

        pedido.removerItem(bebida1);

        pedido.calcularValor();

        pedido.exibirDetalhes();

        System.out.println("Altere o status do pedido");
        pedido.alterarStatus(scan.nextLine());

        pedido.exibirDetalhes();
    }
}
