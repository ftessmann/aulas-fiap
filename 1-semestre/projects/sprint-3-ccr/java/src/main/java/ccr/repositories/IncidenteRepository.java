package ccr.repositories;

import ccr.entities.Incidente;
import ccr.entities.Usuario;
import ccr.entities.Equipe;
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

public class IncidenteRepository extends CrudRepositoryImpl<Incidente> {

    private static final Logger log = LogManager.getLogger(IncidenteRepository.class);

    private final UsuarioRepository usuarioRepository;
    private final LocalizacaoRepository localizacaoRepository;
    private final GravidadeRepository gravidadeRepository;
    private final EquipeRepository equipeRepository;

    public IncidenteRepository(
            UsuarioRepository usuarioRepository,
            LocalizacaoRepository localizacaoRepository,
            GravidadeRepository gravidadeRepository,
            EquipeRepository equipeRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.localizacaoRepository = localizacaoRepository;
        this.gravidadeRepository = gravidadeRepository;
        this.equipeRepository = equipeRepository;
    }

    public IncidenteRepository(
            UsuarioRepository usuarioRepository,
            LocalizacaoRepository localizacaoRepository,
            GravidadeRepository gravidadeRepository
    ) {
        this.usuarioRepository = usuarioRepository;
        this.localizacaoRepository = localizacaoRepository;
        this.gravidadeRepository = gravidadeRepository;
        this.equipeRepository = null;
    }

    @Override
    protected String getTableName() {
        return "T_CCR_INCIDENTE";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO T_CCR_INCIDENTE(nm_incidente, descricao_incidente, T_CCR_USUARIO_id_usuario, " +
                "T_CCR_GRAVIDADE_id, T_CCR_LOCALIZACAO_id, dt_incidente, dt_criacao, dt_atualizacao, dt_exclusao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE T_CCR_INCIDENTE SET nm_incidente = ?, descricao_incidente = ?, T_CCR_USUARIO_id_usuario = ?, " +
                "T_CCR_GRAVIDADE_id = ?, T_CCR_LOCALIZACAO_id = ?, dt_incidente = ?, " +
                "dt_atualizacao = ?, dt_exclusao = ? WHERE id_incidente = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM T_CCR_INCIDENTE WHERE id_incidente = ? AND dt_exclusao IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM T_CCR_INCIDENTE WHERE dt_exclusao IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE T_CCR_INCIDENTE SET dt_exclusao = ? WHERE id_incidente = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Incidente incidente) throws SQLException {
        int index = 1;
        stmt.setString(index++, incidente.getNome());
        stmt.setString(index++, incidente.getDescricao());

        if (incidente.getCriador() != null) {
            stmt.setInt(index++, incidente.getCriador().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (incidente.getGravidade() != null) {
            gravidadeRepository.setGravidadeAsId(stmt, index++, incidente.getGravidade());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (incidente.getLocalizacao() != null) {
            stmt.setInt(index++, incidente.getLocalizacao().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (incidente.getDate() != null) {
            stmt.setTimestamp(index++, Timestamp.valueOf(incidente.getDate()));
        } else {
            stmt.setTimestamp(index++, Timestamp.valueOf(LocalDateTime.now()));
        }

        LocalDateTime now = LocalDateTime.now();
        incidente.setCreatedAt(now);
        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, incidente.getUpdatedAt() != null ?
                Timestamp.valueOf(incidente.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, incidente.getDeletedAt() != null ?
                Timestamp.valueOf(incidente.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Incidente incidente) throws SQLException {

        LocalDateTime now = LocalDateTime.now();
        incidente.setUpdatedAt(now);

        int index = 1;
        stmt.setString(index++, incidente.getNome());
        stmt.setString(index++, incidente.getDescricao());


        if (incidente.getCriador() != null) {
            stmt.setInt(index++, incidente.getCriador().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }


        if (incidente.getGravidade() != null) {
            gravidadeRepository.setGravidadeAsId(stmt, index++, incidente.getGravidade());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (incidente.getLocalizacao() != null) {
            stmt.setInt(index++, incidente.getLocalizacao().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (incidente.getDate() != null) {
            stmt.setTimestamp(index++, Timestamp.valueOf(incidente.getDate()));
        } else {
            stmt.setTimestamp(index++, Timestamp.valueOf(LocalDateTime.now()));
        }

        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, incidente.getDeletedAt() != null ?
                Timestamp.valueOf(incidente.getDeletedAt()) : null);

        stmt.setInt(index++, incidente.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 9;
    }

    @Override
    protected Incidente mapResultSetToEntity(ResultSet rs) throws SQLException {
        Incidente incidente = new Incidente();
        incidente.setId(rs.getInt("id_incidente"));
        incidente.setNome(rs.getString("nm_incidente"));
        incidente.setDescricao(rs.getString("descricao_incidente"));

        Timestamp createdAt = rs.getTimestamp("dt_criacao");
        Timestamp updatedAt = rs.getTimestamp("dt_atualizacao");
        Timestamp deletedAt = rs.getTimestamp("dt_exclusao");
        Timestamp incidenteDate = rs.getTimestamp("dt_incidente");

        if (createdAt != null) {
            incidente.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            incidente.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            incidente.setDeletedAt(deletedAt.toLocalDateTime());
        }
        if (incidenteDate != null) {
            incidente.setDate(incidenteDate.toLocalDateTime());
        }

        int usuarioId = rs.getInt("T_CCR_USUARIO_id_usuario");
        if (!rs.wasNull() && usuarioRepository != null) {
            usuarioRepository.buscarPorId(usuarioId)
                    .ifPresent(incidente::setCriador);
        }

        int localizacaoId = rs.getInt("T_CCR_LOCALIZACAO_id");
        if (!rs.wasNull() && localizacaoRepository != null) {
            localizacaoRepository.buscarPorId(localizacaoId)
                    .ifPresent(incidente::setLocalizacao);
        }

        int gravidadeId = rs.getInt("T_CCR_GRAVIDADE_id");
        if (!rs.wasNull()) {
            incidente.setGravidade(gravidadeRepository.getGravidadeById(gravidadeId));
        }

        return incidente;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<Incidente> incidente = buscarPorId(id);
                incidente.ifPresent(i -> {
                    i.setDeletedAt(LocalDateTime.now());
                    storage.put(id, i);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover incidente", e);
        }
    }

    // metodos adicionais para incidente

    public List<Incidente> buscarPorNome(String nome) {
        List<Incidente> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_INCIDENTE WHERE nm_incidente LIKE ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, "%" + nome + "%");

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Incidente incidente = mapResultSetToEntity(rs);
                    resultado.add(incidente);
                    storage.put(incidente.getId(), incidente);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar incidentes por nome", e);
        }

        return resultado;
    }

    public List<Incidente> buscarPorGravidade(int gravidadeId) {
        List<Incidente> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_INCIDENTE WHERE T_CCR_GRAVIDADE_id = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, gravidadeId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Incidente incidente = mapResultSetToEntity(rs);
                    resultado.add(incidente);
                    storage.put(incidente.getId(), incidente);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar incidentes por gravidade", e);
        }

        return resultado;
    }

    public List<Incidente> buscarPorUsuario(int usuarioId) {
        List<Incidente> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_INCIDENTE WHERE T_CCR_USUARIO_id_usuario = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, usuarioId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Incidente incidente = mapResultSetToEntity(rs);
                    resultado.add(incidente);
                    storage.put(incidente.getId(), incidente);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar incidentes por usuário", e);
        }

        return resultado;
    }

    public void associarEquipe(Incidente incidente, Equipe equipe) {
        if (equipeRepository == null) {
            log.error("EquipeRepository não inicializado");
            return;
        }

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "INSERT INTO T_CCR_INCIDENTE_EQUIPE(T_CCR_INCIDENTE_id, T_CCR_EQUIPE_id, dt_criacao) " +
                             "VALUES (?, ?, ?) ON CONFLICT (T_CCR_INCIDENTE_id, T_CCR_EQUIPE_id) DO NOTHING")) {

            stmt.setInt(1, incidente.getId());
            stmt.setInt(2, equipe.getId());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

        } catch (SQLException e) {
            log.error("Erro ao associar equipe ao incidente", e);
        }
    }

    public void desassociarEquipe(Incidente incidente, Equipe equipe) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "UPDATE T_CCR_INCIDENTE_EQUIPE SET dt_exclusao = ? " +
                             "WHERE T_CCR_INCIDENTE_id = ? AND T_CCR_EQUIPE_id = ? AND dt_exclusao IS NULL")) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, incidente.getId());
            stmt.setInt(3, equipe.getId());
            stmt.executeUpdate();

        } catch (SQLException e) {
            log.error("Erro ao desassociar equipe do incidente", e);
        }
    }

    public List<Equipe> listarEquipesDoIncidente(int incidenteId) {
        List<Equipe> equipes = new ArrayList<>();

        if (equipeRepository == null) {
            log.error("EquipeRepository não inicializado");
            return equipes;
        }

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT T_CCR_EQUIPE_id FROM T_CCR_INCIDENTE_EQUIPE " +
                             "WHERE T_CCR_INCIDENTE_id = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, incidenteId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int equipeId = rs.getInt("T_CCR_EQUIPE_id");
                    equipeRepository.buscarPorId(equipeId)
                            .ifPresent(equipes::add);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao listar equipes do incidente", e);
        }

        return equipes;
    }
}
