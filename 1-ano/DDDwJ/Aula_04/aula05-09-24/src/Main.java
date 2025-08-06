import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //variaveis();
        //operadoresECondicionais();

        //listas
        var nomes = new String[] {"João", "Maria", "José"};
        var numeros = new int[] {1, 2, 3, 4, 5, 6, 7, 8, 9};

        //para cada nome dentro do vetor de nomes
        //jeito facil
        for(var nome: nomes)
            System.out.println(nome);

        //jeito dificil
        for(int i = 0; i < numeros.length; i++)
            System.out.println(numeros[i]);

        //while

        var contador = 0;
        while (contador < nomes.length) {
            System.out.println(nomes[contador]);
            contador++;
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

    public static void operadoresECondicionais() {

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

        System.out.println("Insira a nota de DDD");
        var notaDDD = scan.nextInt();
        scan.nextLine();

        System.out.println("Insira sua nota de CPT");
        var notaCPT = scan.nextInt();
        scan.nextLine();

        System.out.println("Insira sua nota de DB");
        var notaDB = scan.nextInt();
        scan.nextLine();

        //operador ternario
        // funcao (<condicao>) ? <valor se true> : <valor se false>

        System.out.println(notaDDD < 6 || notaCPT < 6 || notaDB < 6 ?
                "Reprovado em pelo menos uma disciplina" : "Aprovado em todas as disciplinas");

        System.out.println("Insira numero para verificar se é par");
        var numero = scan.nextInt();
        scan.nextLine();

        System.out.println(
                numero % 2 == 0 ? "Número é par" : "Número é impar"
        );

    }
}