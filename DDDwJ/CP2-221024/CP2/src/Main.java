import models.Curso;
import models.Exercicio;
import models.Licao;
import models.Usuario;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        Exercicio multipla1 = new Exercicio();
        multipla1.setXp(20);
        multipla1.setNome("Exercicio de multipla escolha.");
        multipla1.setPergunta(
                """
                        Como se fala Por Favor em Inglês?\s
                        A)Thank You\s
                        B)Yes\s
                        C)Please"""
        );
        multipla1.setResposta("C");

        Exercicio traducao = new Exercicio();
        traducao.setXp(40);
        traducao.setNome("Exercicio de traducao.");
        traducao.setPergunta(
                """
                        Qual a tradução de Thank You?
                """
        );
        traducao.setResposta("Obrigado");

        Curso curso1 = new Curso();

        Licao licao1 = new Licao();

        licao1.addExercicio(multipla1);
        licao1.addExercicio(traducao);

        curso1.addLicao(licao1);

        Usuario user1 = new Usuario();
        user1.setNome("joaozinho");
        user1.setXp(0);
        user1.addCurso(curso1);

        licao1.visualizarExercicios();

        System.out.println("Insira sua resposta: ");
        var resposta = sc.nextLine();

        if (resposta.equals(multipla1.getResposta())) {
            System.out.println("Você acertou, parabéns!");
            multipla1.addXp(user1);
            multipla1.setAcerto(true);
            System.out.println("Sua nova XP é: " + user1.getXp());
        } else {
            System.out.println("Reposta errada");
        }

        System.out.println("Insira sua resposta: ");
        var respostaTrad = sc.nextLine();

        if (respostaTrad.equals(traducao.getResposta())) {
            System.out.println("Você acertou, parabéns!");
            traducao.addXp(user1);
            traducao.setAcerto(true);
            System.out.println("Sua nova XP é: " + user1.getXp());
        } else {
            System.out.println("Reposta errada");
        }

        licao1.filtrarPorCertas();


    }
}
