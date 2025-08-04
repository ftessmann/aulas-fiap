import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //variaveis();

        //operadores
        var calculo = 1 + 2 - (3*2) / 5.0; //5.0 transforma o numero em double
        System.out.println(calculo);
        calculo++;
        System.out.println(calculo);
        calculo--;
        System.out.println(calculo);

        //condicionais

        var scan = new Scanner(System.in);

        System.out.println("Digite a sua idade: ");
        var idade = scan.nextInt();
        scan.nextLine();

        if (idade >= 18 && idade <= 200) {
            System.out.println("A pessoa é maior de idade");
        } else if (idade > 200) {
            System.out.println("Idade inválida");
        } else {
            System.out.println("A pessoa é menor de idade");
        }

        }
    public static void variaveis() {
        System.out.println("Sistema iniciando");

        var scanner = new Scanner(System.in);

        System.out.println("Digite o número 1: ");
        var numero1 = scanner.nextBigDecimal(); // para numeros longos
        scanner.nextLine();

        System.out.println("Digite o número 2: ");
        var numero2 = scanner.nextBigDecimal(); // para numeros longos
        scanner.nextLine();

        System.out.println(numero1.add(numero2)); // com BigDecimal, utilizar função add

        String nome = "FIAP"; // para texto
        System.out.println(nome);

        int numero = 10; // para numero inteiros
        System.out.println(numero);

        double valor = 10.5; // para numeros com virgula
        System.out.println(valor);

        long valorGrande = 1000000000000000000L; // L no final para indicar long number
        System.out.println(valorGrande);

        short valorPequeno = 10; // para numeros pequenos
        System.out.println(valorPequeno);

        char letra = 'A'; // apenas uma letra em aspas simples
        System.out.println(letra);

        boolean condicaoLogica = true; // condição lógica de verdadeiro ou falso

        /*
         * var pode ser usado para definir qualquer variavel, o tipo
         * será definido pela informação passada para a variável
         */

        var numeroTexto = "10";
        var numeroConvertido = Integer.parseInt(numeroTexto); // conversão explícita de str para int

        // declaração de variaveis
        int idade; // não pode ser var, pois tipo precisa ser inferido

        // inicialização de variaveis
        idade = 10;
        idade = 20;
        idade = 30;

        System.out.println(idade); // irá printar a ultima inicializaçao

        // declaração e inicialização
        int idade2 = 10; // pode ser var, pois tipo está inferido na inicialização

        System.out.println("Sistema finalizando");
    }
}