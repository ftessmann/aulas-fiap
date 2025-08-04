package ccr.repositories;

import ccr.entities.Linha;
import ccr.entities.Estacao;
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

public class LinhaRepository extends CrudRepositoryImpl<Linha> {

    private static final Logger log = LogManager.getLogger(LinhaRepository.class);

    private final EstacaoRepository estacaoRepository;
    private final TremRepository tremRepository;

    public LinhaRepository(EstacaoRepository estacaoRepository, TremRepository tremRepository) {
        this.estacaoRepository = estacaoRepository;
        this.tremRepository = tremRepository;
    }

    public LinhaRepository() {
        this.estacaoRepository = null;
        this.tremRepository = null;
    }

    @Override
    protected String getTableName() {
        return "T_CCR_LINHA";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO T_CCR_LINHA(nm_linha, dt_criacao, dt_atualizacao, dt_exclusao) " +
                "VALUES (?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE T_CCR_LINHA SET nm_linha = ?, dt_atualizacao = ?, dt_exclusao = ? " +
                "WHERE id_linha = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM T_CCR_LINHA WHERE id_linha = ? AND dt_exclusao IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM T_CCR_LINHA WHERE dt_exclusao IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE T_CCR_LINHA SET dt_exclusao = ? WHERE id_linha = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Linha linha) throws SQLException {
        int index = 1;
        stmt.setString(index++, linha.getNome());

        LocalDateTime now = LocalDateTime.now();
        linha.setCreatedAt(now);
        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, linha.getUpdatedAt() != null ?
                Timestamp.valueOf(linha.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, linha.getDeletedAt() != null ?
                Timestamp.valueOf(linha.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Linha linha) throws SQLException {

        LocalDateTime now = LocalDateTime.now();
        linha.setUpdatedAt(now);

        int index = 1;
        stmt.setString(index++, linha.getNome());
        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, linha.getDeletedAt() != null ?
                Timestamp.valueOf(linha.getDeletedAt()) : null);

        stmt.setInt(index++, linha.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 4;
    }

    @Override
    protected Linha mapResultSetToEntity(ResultSet rs) throws SQLException {
        Linha linha = new Linha();
        linha.setId(rs.getInt("id_linha"));
        linha.setNome(rs.getString("nm_linha"));

        Timestamp createdAt = rs.getTimestamp("dt_criacao");
        Timestamp updatedAt = rs.getTimestamp("dt_atualizacao");
        Timestamp deletedAt = rs.getTimestamp("dt_exclusao");

        if (createdAt != null) {
            linha.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            linha.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            linha.setDeletedAt(deletedAt.toLocalDateTime());
        }

        if (estacaoRepository != null) {
            linha.setEstacoes(carregarEstacoes(linha.getId()));
        } else {
            linha.setEstacoes(new ArrayList<>());
        }

        if (tremRepository != null) {
            linha.setTrens(carregarTrens(linha.getId()));
        } else {
            linha.setTrens(new ArrayList<>());
        }

        return linha;
    }

    public ArrayList<Estacao> carregarEstacoes(int linhaId) {
        ArrayList<Estacao> estacoes = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT e.* FROM T_CCR_ESTACAO e " +
                             "JOIN T_CCR_LINHA_ESTACAO le ON e.id_estacao = le.T_CCR_ESTACAO_id " +
                             "WHERE le.T_CCR_LINHA_id = ? AND e.dt_exclusao IS NULL AND le.dt_exclusao IS NULL " +
                             "ORDER BY le.ordem")) {

            stmt.setInt(1, linhaId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Estacao estacao = estacaoRepository.mapResultSetToEntity(rs);
                    estacoes.add(estacao);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao carregar estações da linha", e);
        }

        return estacoes;
    }

    public ArrayList<Trem> carregarTrens(int linhaId) {
        ArrayList<Trem> trens = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_TREM WHERE T_CCR_LINHA_id_linha = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, linhaId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trem trem = tremRepository.mapResultSetToEntity(rs);
                    trens.add(trem);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao carregar trens da linha", e);
        }

        return trens;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<Linha> linha = buscarPorId(id);
                linha.ifPresent(l -> {
                    l.setDeletedAt(LocalDateTime.now());
                    storage.put(id, l);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover linha", e);
        }
    }

    // metodos adicionais para linha

    public Optional<Linha> buscarPorNome(String nome) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_LINHA WHERE nm_linha = ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, nome);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Linha linha = mapResultSetToEntity(rs);
                    storage.put(linha.getId(), linha);
                    return Optional.of(linha);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar linha por nome", e);
        }

        return Optional.empty();
    }

    public void adicionarEstacao(Linha linha, Estacao estacao, int ordem) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "INSERT INTO T_CCR_LINHA_ESTACAO(T_CCR_LINHA_id, T_CCR_ESTACAO_id, ordem, dt_criacao) " +
                             "VALUES (?, ?, ?, ?) " +
                             "ON CONFLICT (T_CCR_LINHA_id, ordem) DO UPDATE SET T_CCR_ESTACAO_id = ?, dt_atualizacao = ?")) {

            LocalDateTime now = LocalDateTime.now();
            stmt.setInt(1, linha.getId());
            stmt.setInt(2, estacao.getId());
            stmt.setInt(3, ordem);
            stmt.setTimestamp(4, Timestamp.valueOf(now));
            stmt.setInt(5, estacao.getId());
            stmt.setTimestamp(6, Timestamp.valueOf(now));

            stmt.executeUpdate();

            if (linha.getEstacoes() == null) {
                linha.setEstacoes(new ArrayList<>());
            }

            // add a estação na posição correta ou substituir se já existir uma na mesma ordem
            boolean encontrado = false;
            for (int i = 0; i < linha.getEstacoes().size(); i++) {
                if (i == ordem) {
                    linha.getEstacoes().set(i, estacao);
                    encontrado = true;
                    break;
                }
            }

            if (!encontrado) {
                while (linha.getEstacoes().size() <= ordem) {
                    linha.getEstacoes().add(null);
                }
                linha.getEstacoes().set(ordem, estacao);
            }

        } catch (SQLException e) {
            log.error("Erro ao adicionar estação à linha", e);
        }
    }

    public void removerEstacao(Linha linha, Estacao estacao) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "UPDATE T_CCR_LINHA_ESTACAO SET dt_exclusao = ? " +
                             "WHERE T_CCR_LINHA_id = ? AND T_CCR_ESTACAO_id = ? AND dt_exclusao IS NULL")) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, linha.getId());
            stmt.setInt(3, estacao.getId());

            stmt.executeUpdate();

            if (linha.getEstacoes() != null) {
                linha.getEstacoes().removeIf(e -> e != null && e.getId() == estacao.getId());
            }

        } catch (SQLException e) {
            log.error("Erro ao remover estação da linha", e);
        }
    }

    public void reordenarEstacoes(Linha linha, List<Estacao> novaOrdem) {
        try (var connection = DatabaseConfig.getConnection()) {
            connection.setAutoCommit(false);

            try {
                try (var stmt = connection.prepareStatement(
                        "UPDATE T_CCR_LINHA_ESTACAO SET dt_exclusao = ? " +
                                "WHERE T_CCR_LINHA_id = ? AND dt_exclusao IS NULL")) {

                    stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
                    stmt.setInt(2, linha.getId());
                    stmt.executeUpdate();
                }

                try (var stmt = connection.prepareStatement(
                        "INSERT INTO T_CCR_LINHA_ESTACAO(T_CCR_LINHA_id, T_CCR_ESTACAO_id, ordem, dt_criacao) " +
                                "VALUES (?, ?, ?, ?)")) {

                    LocalDateTime now = LocalDateTime.now();
                    for (int i = 0; i < novaOrdem.size(); i++) {
                        Estacao estacao = novaOrdem.get(i);
                        if (estacao != null) {
                            stmt.setInt(1, linha.getId());
                            stmt.setInt(2, estacao.getId());
                            stmt.setInt(3, i);
                            stmt.setTimestamp(4, Timestamp.valueOf(now));
                            stmt.addBatch();
                        }
                    }

                    stmt.executeBatch();
                }

                connection.commit();

                linha.setEstacoes(new ArrayList<>(novaOrdem));

            } catch (SQLException e) {
                connection.rollback();
                throw e;
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            log.error("Erro ao reordenar estações da linha", e);
        }
    }
}
