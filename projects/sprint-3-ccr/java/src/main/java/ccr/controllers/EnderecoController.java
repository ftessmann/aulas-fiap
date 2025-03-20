package ccr.controllers;

import ccr.entities.Endereco;
import ccr.repositories.EnderecoRepository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class EnderecoController {
    private final EnderecoRepository enderecoRepository;
    private final Scanner scanner;

    public EnderecoController(EnderecoRepository enderecoRepository, Scanner scanner) {
        this.enderecoRepository = enderecoRepository;
        this.scanner = scanner;
    }

    public void menuEnderecos() {
        boolean voltar = false;

        while (!voltar) {
            System.out.println("\n=== Gerenciar Endereços ===");
            System.out.println("[ 1 ] Listar Endereços");
            System.out.println("[ 2 ] Buscar Endereço por ID");
            System.out.println("[ 3 ] Buscar Endereços por CEP");
            System.out.println("[ 4 ] Buscar Endereços por Cidade");
            System.out.println("[ 5 ] Criar Novo Endereço");
            System.out.println("[ 6 ] Atualizar Endereço");
            System.out.println("[ 7 ] Remover Endereço");
            System.out.println("[ 0 ] Voltar");

            System.out.print("Selecione a opção desejada: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    listarEnderecos();
                    break;
                case "2":
                    buscarEnderecoPorId();
                    break;
                case "3":
                    buscarEnderecosPorCep();
                    break;
                case "4":
                    buscarEnderecosPorCidade();
                    break;
                case "5":
                    criarNovoEndereco();
                    break;
                case "6":
                    atualizarEndereco();
                    break;
                case "7":
                    removerEndereco();
                    break;
                case "0":
                    voltar = true;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    public Endereco criarEndereco() {
        System.out.println("\n=== Criar Novo Endereço ===");
        System.out.print("CEP: ");
        String cep = scanner.nextLine();
        System.out.print("Rua: ");
        String rua = scanner.nextLine();
        System.out.print("Número: ");
        String numero = scanner.nextLine();
        System.out.print("Bairro: ");
        String bairro = scanner.nextLine();
        System.out.print("Cidade: ");
        String cidade = scanner.nextLine();
        System.out.print("Estado: ");
        String estado = scanner.nextLine();
        System.out.print("Complemento (opcional): ");
        String complemento = scanner.nextLine();

        Endereco endereco = new Endereco();
        endereco.setCep(cep);
        endereco.setRua(rua);
        endereco.setNumero(numero);
        endereco.setBairro(bairro);
        endereco.setCidade(cidade);
        endereco.setEstado(estado);
        endereco.setComplemento(complemento);

        enderecoRepository.salvar(endereco);

        System.out.println("Endereço criado com sucesso!");
        return endereco;
    }

    private void criarNovoEndereco() {
        criarEndereco();
    }

    public Endereco atualizarEndereco(Endereco endereco) {
        System.out.println("\n=== Atualizar Endereço ===");
        System.out.println("Deixe em branco para manter o valor atual.");

        System.out.print("CEP [" + endereco.getCep() + "]: ");
        String cep = scanner.nextLine();
        if (!cep.isEmpty()) {
            endereco.setCep(cep);
        }

        System.out.print("Rua [" + endereco.getRua() + "]: ");
        String rua = scanner.nextLine();
        if (!rua.isEmpty()) {
            endereco.setRua(rua);
        }

        System.out.print("Número [" + endereco.getNumero() + "]: ");
        String numero = scanner.nextLine();
        if (!numero.isEmpty()) {
            endereco.setNumero(numero);
        }

        System.out.print("Bairro [" + endereco.getBairro() + "]: ");
        String bairro = scanner.nextLine();
        if (!bairro.isEmpty()) {
            endereco.setBairro(bairro);
        }

        System.out.print("Cidade [" + endereco.getCidade() + "]: ");
        String cidade = scanner.nextLine();
        if (!cidade.isEmpty()) {
            endereco.setCidade(cidade);
        }

        System.out.print("Estado [" + endereco.getEstado() + "]: ");
        String estado = scanner.nextLine();
        if (!estado.isEmpty()) {
            endereco.setEstado(estado);
        }

        System.out.print("Complemento [" + endereco.getComplemento() + "]: ");
        String complemento = scanner.nextLine();
        if (!complemento.isEmpty()) {
            endereco.setComplemento(complemento);
        }

        enderecoRepository.atualizar(endereco.getId(), endereco);

        System.out.println("Endereço atualizado com sucesso!");
        return endereco;
    }

    private void atualizarEndereco() {
        System.out.print("Digite o ID do endereço que deseja atualizar: ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Endereco> enderecoOpt = enderecoRepository.buscarPorId(id);

        if (!enderecoOpt.isPresent()) {
            System.out.println("Endereço não encontrado.");
            return;
        }

        atualizarEndereco(enderecoOpt.get());
    }

    private void listarEnderecos() {
        List<Endereco> enderecos = enderecoRepository.listarTodos();

        if (enderecos.isEmpty()) {
            System.out.println("Nenhum endereço cadastrado.");
            return;
        }

        System.out.println("\n=== Lista de Endereços ===");
        for (Endereco endereco : enderecos) {
            System.out.println("ID: " + endereco.getId() +
                    " | CEP: " + endereco.getCep() +
                    " | Rua: " + endereco.getRua() + ", " + endereco.getNumero() +
                    " | Cidade: " + endereco.getCidade() + "/" + endereco.getEstado());
        }
    }

    private void buscarEnderecoPorId() {
        System.out.print("Digite o ID do endereço: ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Endereco> endereco = enderecoRepository.buscarPorId(id);

        if (endereco.isPresent()) {
            exibirDetalhesEndereco(endereco.get());
        } else {
            System.out.println("Endereço não encontrado.");
        }
    }

    private void buscarEnderecosPorCep() {
        System.out.print("Digite o CEP: ");
        String cep = scanner.nextLine();

        List<Endereco> enderecos = enderecoRepository.buscarPorCep(cep);

        if (enderecos.isEmpty()) {
            System.out.println("Nenhum endereço encontrado com esse CEP.");
            return;
        }

        System.out.println("\n=== Endereços Encontrados ===");
        for (Endereco endereco : enderecos) {
            System.out.println("ID: " + endereco.getId() +
                    " | Rua: " + endereco.getRua() + ", " + endereco.getNumero() +
                    " | Bairro: " + endereco.getBairro() +
                    " | Cidade: " + endereco.getCidade() + "/" + endereco.getEstado());
        }
    }

    private void buscarEnderecosPorCidade() {
        System.out.print("Digite o nome da cidade: ");
        String cidade = scanner.nextLine();

        List<Endereco> enderecos = enderecoRepository.buscarPorCidade(cidade);

        if (enderecos.isEmpty()) {
            System.out.println("Nenhum endereço encontrado nessa cidade.");
            return;
        }

        System.out.println("\n=== Endereços Encontrados ===");
        for (Endereco endereco : enderecos) {
            System.out.println("ID: " + endereco.getId() +
                    " | CEP: " + endereco.getCep() +
                    " | Rua: " + endereco.getRua() + ", " + endereco.getNumero() +
                    " | Bairro: " + endereco.getBairro());
        }
    }

    private void removerEndereco() {
        System.out.print("Digite o ID do endereço que deseja remover: ");
        int id = Integer.parseInt(scanner.nextLine());

        Optional<Endereco> endereco = enderecoRepository.buscarPorId(id);

        if (!endereco.isPresent()) {
            System.out.println("Endereço não encontrado.");
            return;
        }

        System.out.println("Tem certeza que deseja remover este endereço? (S/N)");
        exibirDetalhesEndereco(endereco.get());

        String confirmacao = scanner.nextLine();

        if (confirmacao.equalsIgnoreCase("S")) {
            enderecoRepository.remover(id);
            System.out.println("Endereço removido com sucesso!");
        } else {
            System.out.println("Operação cancelada.");
        }
    }

    private void exibirDetalhesEndereco(Endereco endereco) {
        System.out.println("\n=== Detalhes do Endereço ===");
        System.out.println("ID: " + endereco.getId());
        System.out.println("CEP: " + endereco.getCep());
        System.out.println("Rua: " + endereco.getRua() + ", " + endereco.getNumero());
        System.out.println("Bairro: " + endereco.getBairro());
        System.out.println("Cidade: " + endereco.getCidade() + "/" + endereco.getEstado());
        if (endereco.getComplemento() != null && !endereco.getComplemento().isEmpty()) {
            System.out.println("Complemento: " + endereco.getComplemento());
        }
    }
}
