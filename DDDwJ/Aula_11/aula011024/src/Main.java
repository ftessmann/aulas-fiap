import java.util.Scanner;

public class Main {
    public static void main (String[] args) {

        var scan = new Scanner(System.in);

        var bebida1 = new Bebida();
        bebida1.setNome("Coca Cola");
        bebida1.setDescricao("Coquinha gelada");
        bebida1.setPreco(6.5);
        bebida1.setGelado(true);

        var pratoPrincipal1 = new PratoPrincipal();
        pratoPrincipal1.setNome("Batata frita");
        pratoPrincipal1.setDescricao("Batata crocante e salgadinha");
        pratoPrincipal1.setPreco(20);
        pratoPrincipal1.setTempoPreparo(300);

        var pratoPrincipal2 = new PratoPrincipal();
        pratoPrincipal2.setNome("Burgão");
        pratoPrincipal2.setPreco(35.50);
        pratoPrincipal2.setDescricao("Burgão suculento com queijo");
        pratoPrincipal2.setTempoPreparo(720);

        var pedido = new Pedido();
        pedido.setNumeroPedido("1234");
        pedido.setStatus("Em preparação");

        pedido.adicionarItem(pratoPrincipal2);
        pedido.adicionarItem(bebida1);
        pedido.adicionarItem(pratoPrincipal1);

        pratoPrincipal1.exibirInfos();
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
