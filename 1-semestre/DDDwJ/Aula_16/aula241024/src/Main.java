import models.*;

import java.util.Arrays;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        var user = new Usuario("Joao");

        var curso = new Curso("Inglês");

        var licao1 = new Licao("Saudações");
        var licao2 = new Licao("Animais");

        var exercicio1 = new ExercicioTraducao("Como se diz Olá em inglês", "Hello", 1);
        var exercicio2 = new ExercicioMultipla("Qual a tradução de Thank You?", "Obrigado", 1, Arrays.asList("Desculpa", "Obrigado", "De Nada", "Por Favor"));

        licao1.addExercicio(exercicio1);
        licao1.addExercicio(exercicio2);

        curso.addLicao(licao1);

        user.getProgressoCursos().put(curso, 0);

        var scanner = new Scanner(System.in);

        for (Exercicio exercicio : licao1.getExercicios()) {
            System.out.println("Tipo de pergunta: " + exercicio.getTipo());
            System.out.println(exercicio.getPergunta());
            if (exercicio instanceof ExercicioMultipla) {
                var exercicioME = (ExercicioMultipla) exercicio;
                int i = 1;
                for (String opcao : exercicioME.getOpcoes()) {
                    System.out.println(i + " - " + opcao);
                    i++;
                }
            }
            System.out.println("Resposta: ");
            var respostaUsuario = scanner.nextLine();
            user.realizarExercicio(exercicio, respostaUsuario, curso);
        }
        scanner.close();
        user.visualizarProgresso(curso);
    }
}
