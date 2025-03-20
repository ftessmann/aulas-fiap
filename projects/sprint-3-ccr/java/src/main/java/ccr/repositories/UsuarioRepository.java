package ccr.repositories;

import ccr.entities.Usuario;
import ccr.entities.Cargo;
import ccr.infrastructure.DatabaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UsuarioRepository extends CrudRepositoryImpl<Usuario> {

    private static final Logger log = LogManager.getLogger(UsuarioRepository.class);

    private final EnderecoRepository enderecoRepository;
    private final CargoRepository cargoRepository;

    public UsuarioRepository(EnderecoRepository enderecoRepository, CargoRepository cargoRepository) {
        this.enderecoRepository = enderecoRepository;
        this.cargoRepository = cargoRepository;
    }

    public UsuarioRepository() {
        this.enderecoRepository = null;
        this.cargoRepository = new CargoRepository();
    }

    @Override
    protected String getTableName() {
        return "T_CCR_USUARIO";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO T_CCR_USUARIO(nm_usuario, cpf_usuario, email_usuario, senha_usuario, telefone_usuario, " +
                "T_CCR_ENDERECO_id_endereco, T_CCR_CARGO_TIPO_id, dt_criacao, dt_atualizacao, dt_exclusao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE T_CCR_USUARIO SET nm_usuario = ?, cpf_usuario = ?, email_usuario = ?, senha_usuario = ?, " +
                "telefone_usuario = ?, T_CCR_ENDERECO_id_endereco = ?, T_CCR_CARGO_TIPO_id = ?, " +
                "dt_atualizacao = ?, dt_exclusao = ? WHERE id_usuario = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM T_CCR_USUARIO WHERE id_usuario = ? AND dt_exclusao IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM T_CCR_USUARIO WHERE dt_exclusao IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE T_CCR_USUARIO SET dt_exclusao = ? WHERE id_usuario = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Usuario usuario) throws SQLException {
        int index = 1;
        stmt.setString(index++, usuario.getNome());
        stmt.setString(index++, usuario.getCpf());
        stmt.setString(index++, usuario.getEmail());
        stmt.setString(index++, usuario.getSenha());
        stmt.setString(index++, usuario.getTelefone());

        if (usuario.getEndereco() != null) {
            stmt.setInt(index++, usuario.getEndereco().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (usuario.getCargo() != null) {
            int cargoId = cargoRepository.getCargoId(usuario.getCargo());
            stmt.setInt(index++, cargoId);
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        LocalDateTime now = LocalDateTime.now();
        stmt.setTimestamp(index++, Timestamp.valueOf(now));

        stmt.setTimestamp(index++, usuario.getUpdatedAt() != null ?
                Timestamp.valueOf(usuario.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, usuario.getDeletedAt() != null ?
                Timestamp.valueOf(usuario.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Usuario usuario) throws SQLException {

        usuario.setUpdatedAt(LocalDateTime.now());

        int index = 1;
        stmt.setString(index++, usuario.getNome());
        stmt.setString(index++, usuario.getCpf());
        stmt.setString(index++, usuario.getEmail());
        stmt.setString(index++, usuario.getSenha());
        stmt.setString(index++, usuario.getTelefone());

        if (usuario.getEndereco() != null) {
            stmt.setInt(index++, usuario.getEndereco().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (usuario.getCargo() != null) {
            stmt.setInt(index++, cargoRepository.getCargoId(usuario.getCargo()));
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        // conversão nativa do timestamp -> LocalDateTime
        stmt.setTimestamp(index++, Timestamp.valueOf(usuario.getUpdatedAt()));
        stmt.setTimestamp(index++, usuario.getDeletedAt() != null ?
                Timestamp.valueOf(usuario.getDeletedAt()) : null);

        stmt.setInt(index++, usuario.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 10;
    }

    @Override
    protected Usuario mapResultSetToEntity(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setId(rs.getInt("id_usuario"));
        usuario.setNome(rs.getString("nm_usuario"));
        usuario.setCpf(rs.getString("cpf_usuario"));
        usuario.setEmail(rs.getString("email_usuario"));
        usuario.setSenha(rs.getString("senha_usuario"));
        usuario.setTelefone(rs.getString("telefone_usuario"));

        Timestamp createdAt = rs.getTimestamp("dt_criacao");
        Timestamp updatedAt = rs.getTimestamp("dt_atualizacao");
        Timestamp deletedAt = rs.getTimestamp("dt_exclusao");

        if (createdAt != null) {
            usuario.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            usuario.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            usuario.setDeletedAt(deletedAt.toLocalDateTime());
        }

        // carrega o endereço
        int enderecoId = rs.getInt("T_CCR_ENDERECO_id_endereco");
        if (!rs.wasNull() && enderecoRepository != null) {
            enderecoRepository.buscarPorId(enderecoId)
                    .ifPresent(usuario::setEndereco);
        }

        // carrega o cargo
        int cargoId = rs.getInt("T_CCR_CARGO_TIPO_id");
        if (!rs.wasNull()) {
            Cargo cargo = cargoRepository.getCargoById(cargoId);
            usuario.setCargo(cargo);
        }

        return usuario;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<Usuario> usuario = buscarPorId(id);
                usuario.ifPresent(u -> {
                    u.setDeletedAt(LocalDateTime.now());
                    storage.put(id, u);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover usuário", e);
        }
    }

    // metodos adicionais para usuário

    public Optional<Usuario> buscarPorCpf(String cpf) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_USUARIO WHERE cpf_usuario = ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, cpf);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapResultSetToEntity(rs);
                    storage.put(usuario.getId(), usuario);
                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar usuário por CPF", e);
        }

        return Optional.empty();
    }

    public Optional<Usuario> buscarPorEmail(String email) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_USUARIO WHERE email_usuario = ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, email);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapResultSetToEntity(rs);
                    storage.put(usuario.getId(), usuario);
                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar usuário por email", e);
        }

        return Optional.empty();
    }

    public List<Usuario> buscarPorNome(String nome) {
        List<Usuario> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_USUARIO WHERE nm_usuario LIKE ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, "%" + nome + "%");

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = mapResultSetToEntity(rs);
                    resultado.add(usuario);
                    storage.put(usuario.getId(), usuario);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar usuários por nome", e);
        }

        return resultado;
    }

    public List<Usuario> buscarPorCargo(Cargo cargo) {
        List<Usuario> resultado = new ArrayList<>();

        int cargoId = cargoRepository.getCargoId(cargo);
        if (cargoId == -1) {
            return resultado;
        }

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_USUARIO WHERE T_CCR_CARGO_TIPO_id = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, cargoId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Usuario usuario = mapResultSetToEntity(rs);
                    resultado.add(usuario);
                    storage.put(usuario.getId(), usuario);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar usuários por cargo", e);
        }

        return resultado;
    }

    // TODO: Passar hash da senha quando implementar front
    public Optional<Usuario> autenticar(String email, String senha) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_USUARIO WHERE email_usuario = ? AND senha_usuario = ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, email);
            stmt.setString(2, senha);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = mapResultSetToEntity(rs);
                    storage.put(usuario.getId(), usuario);
                    return Optional.of(usuario);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao autenticar usuário", e);
        }

        return Optional.empty();
    }

    public boolean alterarSenha(int usuarioId, String senhaAtual, String novaSenha) {
        Optional<Usuario> usuarioOpt = buscarPorId(usuarioId);

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            if (usuario.getSenha().equals(senhaAtual)) {
                try (var connection = DatabaseConfig.getConnection();
                     var stmt = connection.prepareStatement(
                             "UPDATE T_CCR_USUARIO SET senha_usuario = ?, dt_atualizacao = ? WHERE id_usuario = ?")) {

                    stmt.setString(1, novaSenha);
                    stmt.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
                    stmt.setInt(3, usuarioId);

                    int result = stmt.executeUpdate();

                    if (result > 0) {
                        usuario.setSenha(novaSenha);
                        usuario.setUpdatedAt(LocalDateTime.now());
                        storage.put(usuarioId, usuario);
                        return true;
                    }
                } catch (SQLException e) {
                    log.error("Erro ao alterar senha do usuário", e);
                }
            }
        }

        return false;
    }
}
