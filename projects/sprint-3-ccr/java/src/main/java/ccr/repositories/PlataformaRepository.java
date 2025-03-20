package ccr.repositories;

import ccr.entities.Plataforma;
import ccr.entities.Estacao;
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

public class PlataformaRepository extends CrudRepositoryImpl<Plataforma> {

    private static final Logger log = LogManager.getLogger(PlataformaRepository.class);

    private final EstacaoRepository estacaoRepository;

    public PlataformaRepository(EstacaoRepository estacaoRepository) {
        this.estacaoRepository = estacaoRepository;
    }

    public PlataformaRepository() {
        this.estacaoRepository = null;
    }

    @Override
    protected String getTableName() {
        return "T_CCR_PLATAFORMA";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO T_CCR_PLATAFORMA(numero, T_CCR_ESTACAO_id_estacao, dt_criacao, dt_atualizacao, dt_exclusao) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE T_CCR_PLATAFORMA SET numero = ?, T_CCR_ESTACAO_id_estacao = ?, " +
                "dt_atualizacao = ?, dt_exclusao = ? WHERE id_plataforma = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM T_CCR_PLATAFORMA WHERE id_plataforma = ? AND dt_exclusao IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM T_CCR_PLATAFORMA WHERE dt_exclusao IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE T_CCR_PLATAFORMA SET dt_exclusao = ? WHERE id_plataforma = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Plataforma plataforma) throws SQLException {
        int index = 1;
        stmt.setString(index++, plataforma.getNumero());

        if (plataforma.getEstacao() != null) {
            stmt.setInt(index++, plataforma.getEstacao().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        LocalDateTime now = LocalDateTime.now();
        plataforma.setCreatedAt(now);
        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, plataforma.getUpdatedAt() != null ?
                Timestamp.valueOf(plataforma.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, plataforma.getDeletedAt() != null ?
                Timestamp.valueOf(plataforma.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Plataforma plataforma) throws SQLException {

        LocalDateTime now = LocalDateTime.now();
        plataforma.setUpdatedAt(now);

        int index = 1;
        stmt.setString(index++, plataforma.getNumero());

        if (plataforma.getEstacao() != null) {
            stmt.setInt(index++, plataforma.getEstacao().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, plataforma.getDeletedAt() != null ?
                Timestamp.valueOf(plataforma.getDeletedAt()) : null);

        stmt.setInt(index++, plataforma.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 5;
    }

    @Override
    protected Plataforma mapResultSetToEntity(ResultSet rs) throws SQLException {
        Plataforma plataforma = new Plataforma();
        plataforma.setId(rs.getInt("id_plataforma"));
        plataforma.setNumero(rs.getString("numero"));

        Timestamp createdAt = rs.getTimestamp("dt_criacao");
        Timestamp updatedAt = rs.getTimestamp("dt_atualizacao");
        Timestamp deletedAt = rs.getTimestamp("dt_exclusao");

        if (createdAt != null) {
            plataforma.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            plataforma.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            plataforma.setDeletedAt(deletedAt.toLocalDateTime());
        }

        int estacaoId = rs.getInt("T_CCR_ESTACAO_id_estacao");
        if (!rs.wasNull() && estacaoRepository != null) {
            estacaoRepository.buscarPorId(estacaoId)
                    .ifPresent(plataforma::setEstacao);
        }

        return plataforma;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<Plataforma> plataforma = buscarPorId(id);
                plataforma.ifPresent(p -> {
                    p.setDeletedAt(LocalDateTime.now());
                    storage.put(id, p);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover plataforma", e);
        }
    }

    // metodos adicionais para plataforma

    public List<Plataforma> buscarPorNumero(String numero) {
        List<Plataforma> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_PLATAFORMA WHERE numero = ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, numero);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Plataforma plataforma = mapResultSetToEntity(rs);
                    resultado.add(plataforma);
                    storage.put(plataforma.getId(), plataforma);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar plataformas por número", e);
        }

        return resultado;
    }

    public List<Plataforma> buscarPorEstacao(int estacaoId) {
        List<Plataforma> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_PLATAFORMA WHERE T_CCR_ESTACAO_id_estacao = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, estacaoId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Plataforma plataforma = mapResultSetToEntity(rs);
                    resultado.add(plataforma);
                    storage.put(plataforma.getId(), plataforma);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar plataformas por estação", e);
        }

        return resultado;
    }
}
