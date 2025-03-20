package ccr;

import ccr.controllers.EnderecoController;
import ccr.controllers.UsuarioController;
import ccr.entities.Usuario;
import ccr.repositories.CargoRepository;
import ccr.repositories.EnderecoRepository;
import ccr.repositories.UsuarioRepository;
import ccr.tests.EntitiesRepositoriesTests;

import java.util.Scanner;



public class Main {

    private static void executarTestesSistema() {
        EntitiesRepositoriesTests tests = new EntitiesRepositoriesTests();

        tests.executarTestes();
    }

    private static final Scanner scanner = new Scanner(System.in);

    private static EnderecoRepository enderecoRepository;
    private static UsuarioRepository usuarioRepository;
    private static CargoRepository cargoRepository;

    private static UsuarioController usuarioController;
    private static EnderecoController enderecoController;

    private static Usuario usuarioLogado = null;

    public static void main(String[] args) {
        inicializarRepositories();
        inicializarControllers();

        boolean executando = true;

        System.out.println("=== Sistema de Gerenciamento CCR ===");

        while (executando) {
            if (usuarioLogado == null) {
                executando = exibirMenuAcesso();
            } else {
                executando = exibirMenuPrincipal();
            }
        }

        System.out.println("Sistema encerrado. Obrigado por utilizar o Sistema CCR!");
    }

    private static void inicializarRepositories() {
        enderecoRepository = new EnderecoRepository();
        cargoRepository = new CargoRepository();
        usuarioRepository = new UsuarioRepository(enderecoRepository, cargoRepository);
    }

    private static void inicializarControllers() {
        usuarioController = new UsuarioController(usuarioRepository, scanner);
        enderecoController = new EnderecoController(enderecoRepository, scanner);
    }

    private static boolean exibirMenuAcesso() {
        System.out.println("\n=== Menu de Acesso ===");
        System.out.println("[ 1 ] Fazer Login");
        System.out.println("[ 2 ] Criar Novo Usuário");
        System.out.println("[ 3 ] Executar Testes");
        System.out.println("[ 0 ] Sair");

        System.out.print("Selecione a opção desejada: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                usuarioLogado = usuarioController.fazerLogin();
                return true;
            case "2":
                usuarioController.criarNovoUsuario(enderecoController);
                return true;
            case "3":
                executarTestesSistema();
                return true;
            case "0":
                return false;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                return true;
        }
    }

    private static boolean exibirMenuPrincipal() {
        System.out.println("\n=== Menu Principal (" + usuarioLogado.getCargo() + ") ===");
        System.out.println("[ 1 ] Gerenciar Usuários");
        System.out.println("[ 2 ] Gerenciar Endereços");
        System.out.println("[ 3 ] Fazer Logout");
        System.out.println("[ 0 ] Sair");

        System.out.print("Selecione a opção desejada: ");
        String opcao = scanner.nextLine();

        switch (opcao) {
            case "1":
                usuarioController.menuUsuarios(enderecoController);
                return true;
            case "2":
                enderecoController.menuEnderecos();
                return true;
            case "3":
                System.out.println("Logout realizado com sucesso!");
                usuarioLogado = null;
                return true;
            case "0":
                return false;
            default:
                System.out.println("Opção inválida. Tente novamente.");
                return true;
        }
    }
}
