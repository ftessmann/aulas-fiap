public class Main {
    public static void main(String[] args) {

        //Herda todos os atributos e métodos de Produto
        var produtoFisico1 = new ProdutoFisico();
        produtoFisico1.setNome("Elden Ring - Midia física - PS5");
        produtoFisico1.setPreco(200);
        produtoFisico1.setDescricao("Midia fisica para PS5 do Elden Ring");

        produtoFisico1.setDimensao(new Dimensao(20, 20, 20));
        produtoFisico1.getCaracteristica().add(
                new Caracteristica("nome", "Jogo que tu vai passar raiva")
        );
        System.out.println(produtoFisico1);

    }
}
