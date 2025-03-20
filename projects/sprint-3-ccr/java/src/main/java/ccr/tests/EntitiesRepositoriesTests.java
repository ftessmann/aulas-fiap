package ccr.tests;

import ccr.entities.Cargo;
import ccr.entities.Endereco;
import ccr.entities.Usuario;
import ccr.infrastructure.DatabaseConfig;
import ccr.repositories.CargoRepository;
import ccr.repositories.EnderecoRepository;
import ccr.repositories.UsuarioRepository;
import ccr.utils.CpfGenerator;
import ccr.utils.EmailGenerator;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EntitiesRepositoriesTests {

    private EnderecoRepository enderecoRepository;
    private UsuarioRepository usuarioRepository;
    private CargoRepository cargoRepository;

    public void executarTestes() {
        inicializarRepositorios();

        System.out.println("=== Iniciando testes ===");

        testeCriarEndereco();
        testeBuscarEnderecoPorId();
        testeBuscarEnderecoPorCep();
        testeAtualizarEndereco();
        testeAutenticarUsuario();
        testeBuscarUsuarioPorCpf();
        testeBuscarUsuariosPorCargo();
        testeAlterarSenhaUsuario();
        testeRemoverUsuario();

        System.out.println("=== Testes concluídos ===");
    }

    private void inicializarRepositorios() {
        enderecoRepository = new EnderecoRepository();
        cargoRepository = new CargoRepository();
        usuarioRepository = new UsuarioRepository(enderecoRepository, cargoRepository);
    }

    private void testeCriarEndereco() {
        System.out.println("\n>> Teste: Criar Endereço");

        Endereco endereco = new Endereco();
        endereco.setCep("12345-678");
        endereco.setRua("Rua de Teste");
        endereco.setNumero("123");
        endereco.setBairro("Bairro Teste");
        endereco.setCidade("Cidade Teste");
        endereco.setEstado("SP");
        endereco.setComplemento("Apto 101");

        Endereco enderecoSalvo = enderecoRepository.salvar(endereco);

        if (enderecoSalvo != null && enderecoSalvo.getId() > 0) {
            System.out.println("Endereço criado com sucesso (ID: " + enderecoSalvo.getId() + ")");
            System.out.println("CEP: " + enderecoSalvo.getCep());
            System.out.println("Rua: " + enderecoSalvo.getRua());
        } else {
            System.out.println("Falha ao criar endereço");
        }
    }

    private void testeBuscarEnderecoPorId() {
        System.out.println("\n>> Teste: Buscar Endereço por ID");

        Endereco endereco = new Endereco();
        endereco.setCep("98765-432");
        endereco.setRua("Rua de Busca");
        endereco.setNumero("456");
        endereco.setBairro("Bairro Busca");
        endereco.setCidade("Cidade Busca");
        endereco.setEstado("RJ");

        Endereco enderecoSalvo = enderecoRepository.salvar(endereco);

        Optional<Endereco> enderecoBuscado = enderecoRepository.buscarPorId(enderecoSalvo.getId());

        if (enderecoBuscado.isPresent()) {
            System.out.println("✓ Endereço encontrado por ID");
            System.out.println("  ID: " + enderecoBuscado.get().getId());
            System.out.println("  CEP: " + enderecoBuscado.get().getCep());
        } else {
            System.out.println("✗ Falha ao buscar endereço por ID");
        }
    }

    private void testeBuscarEnderecoPorCep() {
        System.out.println("\n>> Teste: Buscar Endereço por CEP");

        Endereco endereco = new Endereco();
        endereco.setCep("11111-222");
        endereco.setRua("Rua CEP");
        endereco.setNumero("789");
        endereco.setCidade("Cidade CEP");
        endereco.setBairro("Bairro CEP");
        endereco.setEstado("MG");

        enderecoRepository.salvar(endereco);

        List<Endereco> enderecosPorCep = enderecoRepository.buscarPorCep("11111-222");

        if (!enderecosPorCep.isEmpty()) {
            System.out.println("✓ Endereço encontrado por CEP");
            System.out.println("  Quantidade encontrada: " + enderecosPorCep.size());
            System.out.println("  CEP do primeiro: " + enderecosPorCep.get(0).getCep());
        } else {
            System.out.println("✗ Falha ao buscar endereço por CEP");
        }
    }

    private void testeAtualizarEndereco() {
        System.out.println("\n>> Teste: Atualizar Endereço");

        Endereco endereco = new Endereco();
        endereco.setCep("33333-444");
        endereco.setRua("Rua Original");
        endereco.setNumero("100");
        endereco.setBairro("Bairro Original");
        endereco.setCidade("Cidade Original");
        endereco.setEstado("ES");

        Endereco enderecoSalvo = enderecoRepository.salvar(endereco);

        enderecoSalvo.setRua("Rua Atualizada");
        enderecoSalvo.setCidade("Cidade Atualizada");

        enderecoRepository.atualizar(enderecoSalvo.getId(), enderecoSalvo);

        Optional<Endereco> enderecoAtualizado = enderecoRepository.buscarPorId(enderecoSalvo.getId());

        if (enderecoAtualizado.isPresent() &&
                "Rua Atualizada".equals(enderecoAtualizado.get().getRua()) &&
                "Cidade Atualizada".equals(enderecoAtualizado.get().getCidade())) {
            System.out.println("✓ Endereço atualizado com sucesso");
            System.out.println("  Nova rua: " + enderecoAtualizado.get().getRua());
            System.out.println("  Nova cidade: " + enderecoAtualizado.get().getCidade());
        } else {
            System.out.println("✗ Falha ao atualizar endereço");
        }
    }

    private void testeAutenticarUsuario() {
        System.out.println("\n>> Teste: Autenticar Usuário");

        var email = EmailGenerator.gerarEmail();

        Endereco endereco = new Endereco();
        endereco.setCep("21000-123");
        endereco.setRua("Rua da Maria");
        endereco.setNumero("42");
        endereco.setBairro("Bairro Autenticação");
        endereco.setCidade("Rio de Janeiro");
        endereco.setEstado("RJ");
        endereco.setComplemento("Bloco A");

        Endereco enderecoSalvo = enderecoRepository.salvar(endereco);

        Usuario usuario = new Usuario();
        usuario.setNome("Maria Souza");
        usuario.setCpf(CpfGenerator.gerarCpf(false));
        usuario.setEmail(email);
        usuario.setSenha("senha456");
        usuario.setTelefone("21988887777");
        usuario.setCargo(Cargo.ADMIN);
        usuario.setEndereco(enderecoSalvo);

        usuarioRepository.salvar(usuario);

        Optional<Usuario> usuarioAutenticado = usuarioRepository.autenticar(email, "senha456");

        if (usuarioAutenticado.isPresent()) {
            System.out.println("✓ Autenticação bem-sucedida");
            System.out.println("  Nome: " + usuarioAutenticado.get().getNome());
        } else {
            System.out.println("✗ Falha na autenticação com credenciais corretas");
        }

        Optional<Usuario> usuarioNaoAutenticado = usuarioRepository.autenticar("maria@teste.com", "senhaerrada");

        if (!usuarioNaoAutenticado.isPresent()) {
            System.out.println("✓ Autenticação com senha incorreta falhou corretamente");
        } else {
            System.out.println("✗ Autenticação com senha incorreta não deveria ter sucesso");
        }
    }

    private void testeBuscarUsuarioPorCpf() {
        System.out.println("\n>> Teste: Buscar Usuário por CPF");

        var cpf = CpfGenerator.gerarCpf(false);

        Endereco endereco = new Endereco();
        endereco.setCep("31000-456");
        endereco.setRua("Rua do Carlos");
        endereco.setNumero("789");
        endereco.setBairro("Bairro CPF");
        endereco.setCidade("Belo Horizonte");
        endereco.setEstado("MG");

        Endereco enderecoSalvo = enderecoRepository.salvar(endereco);

        Usuario usuario = new Usuario();
        usuario.setNome("Carlos Pereira");
        usuario.setCpf(cpf);
        usuario.setEmail(EmailGenerator.gerarEmail());
        usuario.setSenha("senha789");
        usuario.setTelefone("31977776666");
        usuario.setCargo(Cargo.SEGURANCA);
        usuario.setEndereco(enderecoSalvo);

        usuarioRepository.salvar(usuario);

        Optional<Usuario> usuarioBuscado = usuarioRepository.buscarPorCpf(cpf);

        if (usuarioBuscado.isPresent()) {
            System.out.println("✓ Usuário encontrado por CPF");
            System.out.println("  Nome: " + usuarioBuscado.get().getNome());
            System.out.println("  CPF: " + usuarioBuscado.get().getCpf());
        } else {
            System.out.println("✗ Falha ao buscar usuário por CPF");
        }
    }

    private void testeBuscarUsuariosPorCargo() {
        System.out.println("\n>> Teste: Buscar Usuários por Cargo");

        for (int i = 1; i <= 3; i++) {
            Endereco endereco = new Endereco();
            endereco.setCep("11000-" + (100 + i));
            endereco.setRua("Rua da Manutenção " + i);
            endereco.setNumero("" + (100 + i));
            endereco.setBairro("Bairro Manutenção");
            endereco.setCidade("São Paulo");
            endereco.setEstado("SP");

            Endereco enderecoSalvo = enderecoRepository.salvar(endereco);

            Usuario usuario = new Usuario();
            usuario.setNome("Funcionário " + i);
            usuario.setCpf(CpfGenerator.gerarCpf(false));
            usuario.setEmail(EmailGenerator.gerarEmail());
            usuario.setSenha("senha" + i);
            usuario.setTelefone("1198765432" + i);
            usuario.setCargo(Cargo.MANUTENCAO);
            usuario.setEndereco(enderecoSalvo);

            usuarioRepository.salvar(usuario);
        }

        List<Usuario> funcionarios = usuarioRepository.buscarPorCargo(Cargo.MANUTENCAO);

        if (!funcionarios.isEmpty() && funcionarios.size() >= 3) {
            System.out.println("✓ Usuários encontrados por cargo");
            System.out.println("  Quantidade encontrada: " + funcionarios.size());
            System.out.println("  Cargo do primeiro: " + funcionarios.get(0).getCargo());
        } else {
            System.out.println("✗ Falha ao buscar usuários por cargo");
        }
    }

    private void testeAlterarSenhaUsuario() {
        System.out.println("\n>> Teste: Alterar Senha de Usuário");

        var email = EmailGenerator.gerarEmail();

        Endereco endereco = new Endereco();
        endereco.setCep("51000-789");
        endereco.setRua("Rua da Ana");
        endereco.setNumero("555");
        endereco.setBairro("Bairro Senha");
        endereco.setCidade("Porto Alegre");
        endereco.setEstado("RS");

        Endereco enderecoSalvo = enderecoRepository.salvar(endereco);

        Usuario usuario = new Usuario();
        usuario.setNome("Ana Lima");
        usuario.setCpf(CpfGenerator.gerarCpf(false));
        usuario.setEmail(email);
        usuario.setSenha("senhaoriginal");
        usuario.setTelefone("51980142354");
        usuario.setCargo(Cargo.GERENTE);
        usuario.setEndereco(enderecoSalvo);

        Usuario usuarioSalvo = usuarioRepository.salvar(usuario);

        boolean senhaAlterada = usuarioRepository.alterarSenha(usuarioSalvo.getId(), "senhaoriginal", "senhanova");

        if (senhaAlterada) {
            System.out.println("✓ Senha alterada com sucesso");

            Optional<Usuario> usuarioAutenticado = usuarioRepository.autenticar(email, "senhanova");

            if (usuarioAutenticado.isPresent()) {
                System.out.println("✓ Autenticação com nova senha bem-sucedida");
            } else {
                System.out.println("✗ Falha ao autenticar com nova senha");
            }
        } else {
            System.out.println("✗ Falha ao alterar senha");
        }
    }

    private void testeRemoverUsuario() {
        System.out.println("\n>> Teste: Remover Usuário");

        Endereco endereco = new Endereco();
        endereco.setCep("12000-321");
        endereco.setRua("Rua do Pedro");
        endereco.setNumero("999");
        endereco.setBairro("Bairro Remoção");
        endereco.setCidade("São José dos Campos");
        endereco.setEstado("SP");

        Endereco enderecoSalvo = enderecoRepository.salvar(endereco);

        Usuario usuario = new Usuario();
        usuario.setNome("Pedro Santos");
        usuario.setCpf(CpfGenerator.gerarCpf(false));
        usuario.setEmail(EmailGenerator.gerarEmail());
        usuario.setSenha("senha123");
        usuario.setTelefone("12984782532");
        usuario.setCargo(Cargo.SOCORRISTA);
        usuario.setEndereco(enderecoSalvo);

        Usuario usuarioSalvo = usuarioRepository.salvar(usuario);

        usuarioRepository.remover(usuarioSalvo.getId());

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement("SELECT * FROM T_CCR_USUARIO WHERE id_usuario = ?")) {

            stmt.setInt(1, usuarioSalvo.getId());

            try (var rs = stmt.executeQuery()) {
                if (rs.next() && rs.getTimestamp("dt_exclusao") != null) {
                    System.out.println("✓ Usuário removido com sucesso (marcado como excluído)");
                } else {
                    System.out.println("✗ Falha ao remover usuário");
                }
            }
        } catch (SQLException e) {
            System.out.println("✗ Erro ao verificar remoção: " + e.getMessage());
        }
    }
}
