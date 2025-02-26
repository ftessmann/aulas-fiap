package cartas.aula;

import cartas.aula.entities.Colecao;
import cartas.aula.repositories.ColecaoRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {
    private static final String SENHA_MESTRE = "senha123";
    public static Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Sistema iniciando");
        var colecaoRepository = new ColecaoRepository();
        var scan = new Scanner(System.in);

        System.out.println("Bem vindo ao sistema de cartas");
        label:
        while (true) {
            System.out.println("Digite a opção desejada");
            System.out.println("""
                    [1] - Adicionar coleção
                    [2] - Listar coleções
                    [3] - Remover coleção
                    [4] - Listar todas as coleções (apenas Admin)
                    [5] - Exportar arquivo de coleções
                    [6] - Importar arquivo
                    [7] - Sair
                    """);
            var opcao = scan.nextInt();
            scan.nextLine();
            switch (opcao) {
                case 1:
                    CadastrarColecao(colecaoRepository);
                    break;
                case 2:
                    System.out.println(colecaoRepository.listar());
                    break;
                case 3:
                    RemoverColecao(colecaoRepository);
                    break;
                case 4:
                    ListarTodasColecoes(colecaoRepository);
                    break;
                case 5:
                    colecaoRepository.exportarParaJson();
                    break;
                case 6:
                    System.out.println("Digite o nome do arquivo: ");
                    var arquivo = scan.nextLine();
                    colecaoRepository.importar(arquivo);
                    break;
                case 7:
                    break label;
                default:
                    System.out.println("Opção inválida");
            }
        }
        logger.info("Sistema encerrando");
    }

    public static void CadastrarColecao(ColecaoRepository repository) {
        try {
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
            logger.info("Coleção registrada com sucesso {}", colecao);
        } catch (Exception e) {
            System.out.println("Campo com valor inválido");
            logger.error("Erro ao cadastrar coleção", e);
        }
    }

    public static void RemoverColecao(ColecaoRepository repository) {
        var scan = new Scanner(System.in);
        System.out.println("Digite o id da coleção");
        var id = scan.nextInt();
        scan.nextLine();
        repository.deleteById(id);
    }

    public static void ListarTodasColecoes(ColecaoRepository repository) {
        var scan = new Scanner(System.in);
        System.out.println("Digite a senha de admin:");
        var senha = scan.nextLine();
        if (senha.equals(SENHA_MESTRE)) {
            repository.listarTodos();
        } else {
            System.out.println("Acesso não autorizado");
        }
    }
}
