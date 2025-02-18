package cartas.aula;

import cartas.aula.entities.Colecao;
import cartas.aula.repositories.ColecaoRepository;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private static final String SENHA_MESTRE = "senha123";
    public static void main(String[] args) {
        var colecaoRepository = new ColecaoRepository();
        var scan = new Scanner(System.in);

        System.out.println("Bem vindo ao sistema de cartas");
        while (true) {
            System.out.println("Digite a opção desejada");
            System.out.println("""
                    [1] - Adicionar coleção
                    [2] - Listar coleções
                    [3] - Remover coleção
                    [4] - Listar todas as coleções (apenas Admin)
                    [5] - Sair
                    """);
            var opcao = scan.nextInt();
            scan.nextLine();
            try {
                if (opcao == 1) {
                    CadastrarColecao(colecaoRepository);
                }
                if (opcao == 2) {
                    System.out.println(colecaoRepository.listar());
                }
                if (opcao == 3) {
                    RemoverColecao(colecaoRepository);
                }
                if (opcao == 4) {
                    System.out.println("Digite a senha de admin:");
                    var senha = scan.nextLine();
                    if (senha.equals(SENHA_MESTRE)) {
                        colecaoRepository.listarTodos();
                    } else {
                        System.out.println("Acesso não autorizado");
                    }
                }
                if (opcao == 5) {
                    break;
                }
            } catch (InputMismatchException e) {
                System.out.println("Opção inválida, digite apenas numeros");
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            } catch (Exception e) {
                System.out.println("Error:" + e.getMessage());
            }
        }
    }

    public static void CadastrarColecao(ColecaoRepository repository) {
        var scan = new Scanner(System.in);
        System.out.println("Digite o ID da coleção");
        var id = scan.nextInt();
        scan.nextLine();
        System.out.println("Digite o nome da coleção:");
        var nome = scan.nextLine();
        System.out.println("Digite o código da coleção:");
        var codigo = scan.nextLine();
        System.out.println("Digite a data de lançamento:");
        var dataLancamento = scan.nextLine();
        var colecao = new Colecao(nome, codigo, dataLancamento);
        colecao.setId(id);
        repository.adicionar(colecao);
    }

    public static void RemoverColecao(ColecaoRepository repository) {
        var scan = new Scanner(System.in);
        System.out.println("Digite o id da coleção");
        var id = scan.nextInt();
        scan.nextLine();
        repository.deleteById(id);
    }
}
