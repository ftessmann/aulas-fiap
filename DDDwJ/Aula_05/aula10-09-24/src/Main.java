public class Main {
    public static void main(String[] args) {

        var produto1 = new Produto(
                "Coca-Cola",
                5.0,
                "Refrigerante de Cola",
                50,
                "Bebidas"
        );

        var produto2 = new Produto(
                "Fanta",
                5.0,
                "Refrigerante de guaraná",
                20,
                "Bebidas"
        );

        System.out.println(produto1);

        produto1.AdicionarEstoque(5);

        System.out.println(produto1);

        produto1.RemoverEstoque(40);

        System.out.println(produto1);

        //Produto 1
        var nome = "Coca-Cola";
        var preco = 5.0;
        var descricao = "Refrigerante de cola";

        //Produto 2
        var nome2 = "Guaraná";
        var preco2 = 4.0;
        var descricao2 = "Refrigerante de guaraná";
    }
}