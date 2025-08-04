package ccr.repositories;

import ccr.entities.Equipe;
import ccr.entities.Usuario;
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

public class EquipeRepository extends CrudRepositoryImpl<Equipe> {

    private static final Logger log = LogManager.getLogger(EquipeRepository.class);

    private final UsuarioRepository usuarioRepository;
    private final LocalizacaoRepository localizacaoRepository;
    private final EstacaoRepository estacaoRepository;

    public EquipeRepository(
            UsuarioRepository usuarioRepository,
            LocalizacaoRepository localizacaoRepository,
            EstacaoRepository estacaoRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.localizacaoRepository = localizacaoRepository;
        this.estacaoRepository = estacaoRepository;
    }

    @Override
    protected String getTableName() {
        return "T_CCR_EQUIPE";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO T_CCR_EQUIPE(nm_equipe, estacao_base_id, T_CCR_LOCALIZACAO_id, dt_criacao, dt_atualizacao, dt_exclusao) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE T_CCR_EQUIPE SET nm_equipe = ?, estacao_base_id = ?, T_CCR_LOCALIZACAO_id = ?, " +
                "dt_atualizacao = ?, dt_exclusao = ? WHERE id_equipe = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM T_CCR_EQUIPE WHERE id_equipe = ? AND dt_exclusao IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM T_CCR_EQUIPE WHERE dt_exclusao IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE T_CCR_EQUIPE SET dt_exclusao = ? WHERE id_equipe = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Equipe equipe) throws SQLException {
        int index = 1;
        stmt.setString(index++, equipe.getNome());

        if (equipe.getBase() != null) {
            stmt.setInt(index++, equipe.getBase().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (equipe.getLocalizacao() != null) {
            stmt.setInt(index++, equipe.getLocalizacao().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        stmt.setTimestamp(index++, Timestamp.valueOf(LocalDateTime.now())); // dt_criacao
        stmt.setTimestamp(index++, equipe.getUpdatedAt() != null ?
                Timestamp.valueOf(equipe.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, equipe.getDeletedAt() != null ?
                Timestamp.valueOf(equipe.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Equipe equipe) throws SQLException {
        equipe.setUpdatedAt(LocalDateTime.now());

        int index = 1;
        stmt.setString(index++, equipe.getNome());

        if (equipe.getBase() != null) {
            stmt.setInt(index++, equipe.getBase().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (equipe.getLocalizacao() != null) {
            stmt.setInt(index++, equipe.getLocalizacao().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        stmt.setTimestamp(index++, Timestamp.valueOf(equipe.getUpdatedAt()));
        stmt.setTimestamp(index++, equipe.getDeletedAt() != null ?
                Timestamp.valueOf(equipe.getDeletedAt()) : null);

        stmt.setInt(index++, equipe.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 6;
    }

    @Override
    protected Equipe mapResultSetToEntity(ResultSet rs) throws SQLException {
        Equipe equipe = new Equipe();
        equipe.setId(rs.getInt("id_equipe"));
        equipe.setNome(rs.getString("nm_equipe"));

        Timestamp createdAt = rs.getTimestamp("dt_criacao");
        Timestamp updatedAt = rs.getTimestamp("dt_atualizacao");
        Timestamp deletedAt = rs.getTimestamp("dt_exclusao");

        if (updatedAt != null) {
            equipe.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            equipe.setDeletedAt(deletedAt.toLocalDateTime());
        }

        int localizacaoId = rs.getInt("T_CCR_LOCALIZACAO_id");
        if (!rs.wasNull() && localizacaoRepository != null) {
            localizacaoRepository.buscarPorId(localizacaoId)
                    .ifPresent(equipe::setLocalizacao);
        }

        int estacaoId = rs.getInt("estacao_base_id");
        if (!rs.wasNull() && estacaoRepository != null) {
            estacaoRepository.buscarPorId(estacaoId)
                    .ifPresent(equipe::setBase);
        }

        equipe.setIntegrantes(carregarIntegrantes(equipe.getId()));

        return equipe;
    }

    private ArrayList<Usuario> carregarIntegrantes(int equipeId) {
        ArrayList<Usuario> integrantes = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT T_CCR_USUARIO_id FROM T_CCR_EQUIPE_USUARIO WHERE T_CCR_EQUIPE_id = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, equipeId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int usuarioId = rs.getInt("T_CCR_USUARIO_id");
                    usuarioRepository.buscarPorId(usuarioId)
                            .ifPresent(integrantes::add);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao carregar integrantes da equipe", e);
        }

        return integrantes;
    }

    public void adicionarIntegrante(Equipe equipe, Usuario usuario) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "INSERT INTO T_CCR_EQUIPE_USUARIO(T_CCR_EQUIPE_id, T_CCR_USUARIO_id, dt_criacao) VALUES (?, ?, ?)")) {

            stmt.setInt(1, equipe.getId());
            stmt.setInt(2, usuario.getId());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

            if (equipe.getIntegrantes() == null) {
                equipe.setIntegrantes(new ArrayList<>());
            }
            equipe.getIntegrantes().add(usuario);

        } catch (SQLException e) {
            log.error("Erro ao adicionar integrante à equipe", e);
        }
    }

    public void removerIntegrante(Equipe equipe, Usuario usuario) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "UPDATE T_CCR_EQUIPE_USUARIO SET dt_exclusao = ? WHERE T_CCR_EQUIPE_id = ? AND T_CCR_USUARIO_id = ?")) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, equipe.getId());
            stmt.setInt(3, usuario.getId());
            stmt.executeUpdate();

            if (equipe.getIntegrantes() != null) {
                equipe.getIntegrantes().removeIf(u -> u.getId() == usuario.getId());
            }

        } catch (SQLException e) {
            log.error("Erro ao remover integrante da equipe", e);
        }
    }

    public List<Equipe> buscarPorNome(String nome) {
        List<Equipe> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_EQUIPE WHERE nm_equipe LIKE ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, "%" + nome + "%");

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar equipes por nome", e);
        }

        return resultado;
    }

    public List<Equipe> buscarPorLocalizacao(int localizacaoId) {
        List<Equipe> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_EQUIPE WHERE T_CCR_LOCALIZACAO_id = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, localizacaoId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar equipes por localização", e);
        }

        return resultado;
    }

    public List<Equipe> buscarPorEstacaoBase(int estacaoId) {
        List<Equipe> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_EQUIPE WHERE estacao_base_id = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, estacaoId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    resultado.add(mapResultSetToEntity(rs));
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar equipes por estação base", e);
        }

        return resultado;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<Equipe> equipe = buscarPorId(id);
                equipe.ifPresent(e -> {
                    e.setDeletedAt(LocalDateTime.now());
                    storage.put(id, e);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover equipe", e);
        }
    }
}
