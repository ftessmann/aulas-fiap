import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        var numeros = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        numeros.stream()
                .filter(numero -> numero % 2 == 0)
                .map(numero -> numero * numero)
                .toList()
                .forEach(System.out::println);

        var nomes = new String[]{"Ana", "Leo", "Bruno", "Cesar", "Amanda", "Antonio"};

        var nomesComInicialA = Arrays.stream(nomes)
                .filter(nome -> nome.startsWith("A") && nome.length() >= 3) // filtra os nomes que começam com A e tem 3 ou mais letras
                .map(String::toUpperCase) // transforma tudo em maiusculo
                .sorted() // ordena os nomes
                .toList(); //converte o stream em uma lista

        nomesComInicialA.forEach(System.out::println);

        var listas = new ArrayList<ArrayList<Integer>>();
        listas.add(new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5)));
        listas.add(new ArrayList<>(Arrays.asList(6, 7, 8, 9, 0)));

        listas.stream()
                .flatMap(List::stream)// concatena todas as listas da matriz
                .forEach(System.out::println);

        var outrosNumeros = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

        var soma = outrosNumeros.stream()
                .reduce(0, Integer::sum); // soma todos os itens da lista
        System.out.println(soma);

        var palavras = Arrays.asList("Java", "python", "javascript", "html");

        if (palavras.stream().anyMatch(palavra -> palavra.startsWith("j"))) // retorna boolean se existe palavra com J na lista
            System.out.println("Existe palavra com J na lista");

        if (palavras.stream().allMatch(palavra -> palavra.startsWith("j"))) // retorna boolean se todas as palavras iniciam com J na lista
            System.out.println("Todos os elementos iniciam com J na lista");
        else
            System.out.println("Existe algum elemento que não começa com J");

        if (palavras.stream().noneMatch(palavra -> palavra.startsWith("c"))) // retorna boolean se nenhuma palavra inicia com C na lista
            System.out.println("Não existe nenhum elemento que inicie com C");
        else
            System.out.println("Existe algum elemento que inicia com C");

        var numerosAleatorios = new ArrayList<Integer>();
        for (int i = 0; i < 1000000; i++ ) {
            numerosAleatorios.add((int) (Math.random() * 1000));
        }

        numerosAleatorios
                .parallelStream() // acelera o processo dividindo o processamento
                .map(numero -> numero * 2)
                .forEach(System.out::println);

        /**
        var numerosPares = new ArrayList<Integer>();

        for(var numero : numeros) {
            if(numero % 2 == 0) {
                numerosPares.add(numero * numero);
            }
        }

        for (var numero : numerosPares)
            System.out.println(numero);

         **/
    }
}
