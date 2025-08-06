package ccr.controllers;

import ccr.entities.Cargo;
import ccr.entities.Endereco;
import ccr.entities.Usuario;
import ccr.repositories.UsuarioRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UsuarioController {
    private final UsuarioRepository usuarioRepository;
    private final Scanner scanner;

    public UsuarioController(UsuarioRepository usuarioRepository, Scanner scanner) {
        this.usuarioRepository = usuarioRepository;
        this.scanner = scanner;
    }

    public Usuario fazerLogin() {
        System.out.println("\n=== Login ===");
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();

        Optional<Usuario> usuario = usuarioRepository.autenticar(email, senha);

        if (usuario.isPresent()) {
            System.out.println("Login realizado com sucesso! Bem-vindo, " + usuario.get().getNome() + "!");
            return usuario.get();
        } else {
            System.out.println("Email ou senha incorretos. Tente novamente.");
            return null;
        }
    }

    public void criarNovoUsuario(EnderecoController enderecoController) {
        System.out.println("\n=== Criar Novo Usuário ===");

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Senha: ");
        String senha = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();

        Endereco endereco = enderecoController.criarEndereco();
        if (endereco == null) {
            System.out.println("Criação de usuário cancelada.");
            return;
        }

        System.out.println("\nCargos disponíveis:");
        Cargo[] cargos = Cargo.values();
        for (int i = 0; i < cargos.length; i++) {
            System.out.println("[ " + (i + 1) + " ] " + cargos[i]);
        }

        System.out.print("Selecione o cargo: ");
        int cargoIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (cargoIndex < 0 || cargoIndex >= cargos.length) {
            System.out.println("Cargo inválido. Criação de usuário cancelada.");
            return;
        }

        Cargo cargo = cargos[cargoIndex];

        Usuario usuario = new Usuario();
        usuario.setNome(nome);
        usuario.setCpf(cpf);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        usuario.setTelefone(telefone);
        usuario.setEndereco(endereco);
        usuario.setCargo(cargo);

        Usuario usuarioSalvo = usuarioRepository.salvar(usuario);
        if (usuarioSalvo != null && usuarioSalvo.getId() > 0) {
            System.out.println("Usuário criado com sucesso!");
        } else {
            System.out.println("Erro ao criar usuário. Verifique os logs para mais detalhes.");
        }
    }

    public void menuUsuarios(EnderecoController enderecoController) {
        boolean voltar = false;

        while (!voltar) {
            System.out.println("\n=== Gerenciar Usuários ===");
            System.out.println("[ 1 ] Listar Usuários");
            System.out.println("[ 2 ] Buscar Usuário por ID");
            System.out.println("[ 3 ] Buscar Usuário por CPF");
            System.out.println("[ 4 ] Buscar Usuário por Email");
            System.out.println("[ 5 ] Buscar Usuários por Nome");
            System.out.println("[ 6 ] Buscar Usuários por Cargo");
            System.out.println("[ 7 ] Criar Novo Usuário");
            System.out.println("[ 8 ] Atualizar Usuário");
            System.out.println("[ 9 ] Remover Usuário");
            System.out.println("[ 10 ] Alterar Senha");
            System.out.println("[ 0 ] Voltar");

            System.out.print("Selecione a opção desejada: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    listarUsuarios();
                    break;
                case "2":
                    buscarUsuarioPorId();
                    break;
                case "3":
                    buscarUsuarioPorCpf();
                    break;
                case "4":
                    buscarUsuarioPorEmail();
                    break;
                case "5":
                    buscarUsuariosPorNome();
                    break;
                case "6":
                    buscarUsuariosPorCargo();
                    break;
                case "7":
                    criarNovoUsuario(enderecoController);
                    break;
                case "8":
                    atualizarUsuario(enderecoController);
                    break;
                case "9":
                    removerUsuario();
                    break;
                case "10":
                    alterarSenha();
                    break;
                case "0":
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private void listarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.listarTodos();

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário cadastrado.");
            return;
        }

        System.out.println("\n=== Lista de Usuários ===");
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId() +
                    " | Nome: " + usuario.getNome() +
                    " | CPF: " + usuario.getCpf() +
                    " | Email: " + usuario.getEmail() +
                    " | Cargo: " + usuario.getCargo());
        }
    }

    private void buscarUsuarioPorId() {
        System.out.print("Digite o ID do usuário: ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Usuario> usuario = usuarioRepository.buscarPorId(id);

        if (usuario.isPresent()) {
            exibirDetalhesUsuario(usuario.get());
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private void buscarUsuarioPorCpf() {
        System.out.print("Digite o CPF do usuário: ");
        String cpf = scanner.nextLine();

        Optional<Usuario> usuario = usuarioRepository.buscarPorCpf(cpf);

        if (usuario.isPresent()) {
            exibirDetalhesUsuario(usuario.get());
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private void buscarUsuarioPorEmail() {
        System.out.print("Digite o email do usuário: ");
        String email = scanner.nextLine();

        Optional<Usuario> usuario = usuarioRepository.buscarPorEmail(email);

        if (usuario.isPresent()) {
            exibirDetalhesUsuario(usuario.get());
        } else {
            System.out.println("Usuário não encontrado.");
        }
    }

    private void buscarUsuariosPorNome() {
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();

        List<Usuario> usuarios = usuarioRepository.buscarPorNome(nome);

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado com esse nome.");
            return;
        }

        System.out.println("\n=== Usuários Encontrados ===");
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId() +
                    " | Nome: " + usuario.getNome() +
                    " | CPF: " + usuario.getCpf() +
                    " | Email: " + usuario.getEmail() +
                    " | Cargo: " + usuario.getCargo());
        }
    }

    private void buscarUsuariosPorCargo() {
        System.out.println("\nCargos disponíveis:");
        Cargo[] cargos = Cargo.values();
        for (int i = 0; i < cargos.length; i++) {
            System.out.println("[ " + (i + 1) + " ] " + cargos[i]);
        }

        System.out.print("Selecione o cargo: ");
        int cargoIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (cargoIndex < 0 || cargoIndex >= cargos.length) {
            System.out.println("Cargo inválido.");
            return;
        }

        Cargo cargo = cargos[cargoIndex];

        List<Usuario> usuarios = usuarioRepository.buscarPorCargo(cargo);

        if (usuarios.isEmpty()) {
            System.out.println("Nenhum usuário encontrado com esse cargo.");
            return;
        }

        System.out.println("\n=== Usuários com Cargo " + cargo + " ===");
        for (Usuario usuario : usuarios) {
            System.out.println("ID: " + usuario.getId() +
                    " | Nome: " + usuario.getNome() +
                    " | CPF: " + usuario.getCpf() +
                    " | Email: " + usuario.getEmail());
        }
    }

    private void atualizarUsuario(EnderecoController enderecoController) {
        System.out.print("Digite o ID do usuário que deseja atualizar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(id);

        if (!usuarioOpt.isPresent()) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        Usuario usuario = usuarioOpt.get();

        System.out.println("\n=== Atualizar Usuário ===");
        System.out.println("Deixe em branco para manter o valor atual.");

        System.out.print("Nome [" + usuario.getNome() + "]: ");
        String nome = scanner.nextLine();
        if (!nome.isEmpty()) {
            usuario.setNome(nome);
        }

        System.out.print("CPF [" + usuario.getCpf() + "]: ");
        String cpf = scanner.nextLine();
        if (!cpf.isEmpty()) {
            usuario.setCpf(cpf);
        }

        System.out.print("Email [" + usuario.getEmail() + "]: ");
        String email = scanner.nextLine();
        if (!email.isEmpty()) {
            usuario.setEmail(email);
        }

        System.out.print("Telefone [" + usuario.getTelefone() + "]: ");
        String telefone = scanner.nextLine();
        if (!telefone.isEmpty()) {
            usuario.setTelefone(telefone);
        }

        System.out.println("\nDeseja atualizar o endereço? (S/N)");
        String atualizarEndereco = scanner.nextLine();

        if (atualizarEndereco.equalsIgnoreCase("S")) {
            Endereco novoEndereco = enderecoController.atualizarEndereco(usuario.getEndereco());
            if (novoEndereco != null) {
                usuario.setEndereco(novoEndereco);
            }
        }

        System.out.println("\nDeseja atualizar o cargo? (S/N)");
        String atualizarCargo = scanner.nextLine();

        if (atualizarCargo.equalsIgnoreCase("S")) {
            System.out.println("\nCargos disponíveis:");
            Cargo[] cargos = Cargo.values();
            for (int i = 0; i < cargos.length; i++) {
                System.out.println("[ " + (i + 1) + " ] " + cargos[i]);
            }

            System.out.print("Selecione o novo cargo: ");
            int cargoIndex = Integer.parseInt(scanner.nextLine()) - 1;

            if (cargoIndex >= 0 && cargoIndex < cargos.length) {
                usuario.setCargo(cargos[cargoIndex]);
            }
        }

        usuarioRepository.atualizar(id, usuario);
        System.out.println("Usuário atualizado com sucesso!");
    }

    private void removerUsuario() {
        System.out.print("Digite o ID do usuário que deseja remover: ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Usuario> usuario = usuarioRepository.buscarPorId(id);

        if (!usuario.isPresent()) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.println("Tem certeza que deseja remover o usuário " + usuario.get().getNome() + "? (S/N)");
        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            usuarioRepository.remover(id);
            System.out.println("Usuário removido com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void exibirDetalhesUsuario(Usuario usuario) {
        System.out.println("\n=== Detalhes do Usuário ===");
        System.out.println("ID: " + usuario.getId());
        System.out.println("Nome: " + usuario.getNome());
        System.out.println("CPF: " + usuario.getCpf());
        System.out.println("Email: " + usuario.getEmail());
        System.out.println("Telefone: " + usuario.getTelefone());
        System.out.println("Cargo: " + usuario.getCargo());

        if (usuario.getEndereco() != null) {
            System.out.println("\nEndereço:");
            System.out.println("CEP: " + usuario.getEndereco().getCep());
            System.out.println("Rua: " + usuario.getEndereco().getRua() + ", " + usuario.getEndereco().getNumero());
            System.out.println("Bairro: " + usuario.getEndereco().getBairro());
            System.out.println("Cidade: " + usuario.getEndereco().getCidade() + "/" + usuario.getEndereco().getEstado());
            if (usuario.getEndereco().getComplemento() != null && !usuario.getEndereco().getComplemento().isEmpty()) {
                System.out.println("Complemento: " + usuario.getEndereco().getComplemento());
            }
        }
    }

    private void alterarSenha() {
        System.out.print("Digite o ID do usuário: ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Usuario> usuarioOpt = usuarioRepository.buscarPorId(id);

        if (!usuarioOpt.isPresent()) {
            System.out.println("Usuário não encontrado.");
            return;
        }

        System.out.print("Digite a senha atual: ");
        String senhaAtual = scanner.nextLine();
        System.out.print("Digite a nova senha: ");
        String novaSenha = scanner.nextLine();

        boolean resultado = usuarioRepository.alterarSenha(id, senhaAtual, novaSenha);

        if (resultado) {
            System.out.println("Senha alterada com sucesso!");
        } else {
            System.out.println("Não foi possível alterar a senha. Verifique se a senha atual está correta.");
        }
    }
}
