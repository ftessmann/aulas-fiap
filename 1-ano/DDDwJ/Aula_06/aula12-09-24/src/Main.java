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

        var produto3 = new Produto(
                "Guaraná",
                0,
                "Refrigerante de guaraná",
                0,
                "Bebidas"
        );

        produto3.setNome("Fanta Guaraná");
        System.out.println(produto3.getNome());

        System.out.println(produto1);

        produto1.AdicionarEstoque(5);

        System.out.println(produto1);

        produto1.RemoverEstoque(40);

        System.out.println(produto1);

        var numero = 10;

        if (numero == 10)
            System.out.println("Número é igual a 10");

        var text = "teste";
        if (text.equals("teste"))
            System.out.println("Texto é igual");

        //Cria dois produtos usando o construtor vazio
        var produto4 = new Produto();
        var produto5 = new Produto();

        //Compara se dois objetos são iguais - compara cada atributo do objeto
        if (produto4.equals(produto5))
            System.out.println("Produtos são iguais");

        //Herda todos os atributos e métodos de Produto
        var produtoFisico1 = new ProdutoFisico();
        produtoFisico1.setNome("Livro");

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
