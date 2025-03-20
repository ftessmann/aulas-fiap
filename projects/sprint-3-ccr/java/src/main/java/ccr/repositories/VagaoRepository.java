package ccr.repositories;

import ccr.entities.Vagao;
import ccr.entities.Trem;
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

public class VagaoRepository extends CrudRepositoryImpl<Vagao> {

    private static final Logger log = LogManager.getLogger(VagaoRepository.class);

    private final TremRepository tremRepository;

    public VagaoRepository(TremRepository tremRepository) {
        this.tremRepository = tremRepository;
    }

    public VagaoRepository() {
        this.tremRepository = null;
    }

    @Override
    protected String getTableName() {
        return "T_CCR_VAGAO";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO T_CCR_VAGAO(numeracao, T_CCR_TREM_id_trem, dt_criacao, dt_atualizacao, dt_exclusao) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE T_CCR_VAGAO SET numeracao = ?, T_CCR_TREM_id_trem = ?, " +
                "dt_atualizacao = ?, dt_exclusao = ? WHERE id_vagao = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM T_CCR_VAGAO WHERE id_vagao = ? AND dt_exclusao IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM T_CCR_VAGAO WHERE dt_exclusao IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE T_CCR_VAGAO SET dt_exclusao = ? WHERE id_vagao = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Vagao vagao) throws SQLException {
        int index = 1;
        stmt.setString(index++, vagao.getNumeracao());

        if (vagao.getTrem() != null) {
            stmt.setInt(index++, vagao.getTrem().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        LocalDateTime now = LocalDateTime.now();
        vagao.setCreatedAt(now);
        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, vagao.getUpdatedAt() != null ?
                Timestamp.valueOf(vagao.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, vagao.getDeletedAt() != null ?
                Timestamp.valueOf(vagao.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Vagao vagao) throws SQLException {

        LocalDateTime now = LocalDateTime.now();
        vagao.setUpdatedAt(now);

        int index = 1;
        stmt.setString(index++, vagao.getNumeracao());

        if (vagao.getTrem() != null) {
            stmt.setInt(index++, vagao.getTrem().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, vagao.getDeletedAt() != null ?
                Timestamp.valueOf(vagao.getDeletedAt()) : null);

        stmt.setInt(index++, vagao.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 5;
    }

    @Override
    protected Vagao mapResultSetToEntity(ResultSet rs) throws SQLException {
        Vagao vagao = new Vagao();
        vagao.setId(rs.getInt("id_vagao"));
        vagao.setNumeracao(rs.getString("numeracao"));

        Timestamp createdAt = rs.getTimestamp("dt_criacao");
        Timestamp updatedAt = rs.getTimestamp("dt_atualizacao");
        Timestamp deletedAt = rs.getTimestamp("dt_exclusao");

        if (createdAt != null) {
            vagao.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            vagao.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            vagao.setDeletedAt(deletedAt.toLocalDateTime());
        }

        int tremId = rs.getInt("T_CCR_TREM_id_trem");
        if (!rs.wasNull() && tremRepository != null) {
            tremRepository.buscarPorId(tremId)
                    .ifPresent(vagao::setTrem);
        }

        return vagao;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<Vagao> vagao = buscarPorId(id);
                vagao.ifPresent(v -> {
                    v.setDeletedAt(LocalDateTime.now());
                    storage.put(id, v);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover vagão", e);
        }
    }

    // metodos adicionais para vagao

    public List<Vagao> buscarPorNumeracao(String numeracao) {
        List<Vagao> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_VAGAO WHERE numeracao = ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, numeracao);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vagao vagao = mapResultSetToEntity(rs);
                    resultado.add(vagao);
                    storage.put(vagao.getId(), vagao);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar vagões por numeração", e);
        }

        return resultado;
    }

    public List<Vagao> buscarPorTrem(int tremId) {
        List<Vagao> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_VAGAO WHERE T_CCR_TREM_id_trem = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, tremId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vagao vagao = mapResultSetToEntity(rs);
                    resultado.add(vagao);
                    storage.put(vagao.getId(), vagao);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar vagões por trem", e);
        }

        return resultado;
    }
}
