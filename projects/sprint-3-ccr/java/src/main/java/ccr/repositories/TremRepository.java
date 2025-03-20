package ccr.repositories;

import ccr.entities.Trem;
import ccr.entities.Estacao;
import ccr.entities.Linha;
import ccr.entities.Usuario;
import ccr.entities.Vagao;
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

public class TremRepository extends CrudRepositoryImpl<Trem> {

    private static final Logger log = LogManager.getLogger(TremRepository.class);

    private final LinhaRepository linhaRepository;
    private final EstacaoRepository estacaoRepository;
    private final UsuarioRepository usuarioRepository;
    private final VagaoRepository vagaoRepository;

    public TremRepository(
            LinhaRepository linhaRepository,
            EstacaoRepository estacaoRepository,
            UsuarioRepository usuarioRepository,
            VagaoRepository vagaoRepository
    ) {
        this.linhaRepository = linhaRepository;
        this.estacaoRepository = estacaoRepository;
        this.usuarioRepository = usuarioRepository;
        this.vagaoRepository = vagaoRepository;
    }

    public TremRepository(LinhaRepository linhaRepository, EstacaoRepository estacaoRepository) {
        this.linhaRepository = linhaRepository;
        this.estacaoRepository = estacaoRepository;
        this.usuarioRepository = null;
        this.vagaoRepository = null;
    }

    @Override
    protected String getTableName() {
        return "T_CCR_TREM";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO T_CCR_TREM(modelo, T_CCR_LINHA_id_linha, estacao_inicial_id, estacao_final_id, " +
                "dt_criacao, dt_atualizacao, dt_exclusao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE T_CCR_TREM SET modelo = ?, T_CCR_LINHA_id_linha = ?, estacao_inicial_id = ?, " +
                "estacao_final_id = ?, dt_atualizacao = ?, dt_exclusao = ? WHERE id_trem = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM T_CCR_TREM WHERE id_trem = ? AND dt_exclusao IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM T_CCR_TREM WHERE dt_exclusao IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE T_CCR_TREM SET dt_exclusao = ? WHERE id_trem = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Trem trem) throws SQLException {
        int index = 1;
        stmt.setString(index++, trem.getModelo());

        if (trem.getLinha() != null) {
            stmt.setInt(index++, trem.getLinha().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (trem.getEstacaoInicial() != null) {
            stmt.setInt(index++, trem.getEstacaoInicial().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (trem.getEstacaoFinal() != null) {
            stmt.setInt(index++, trem.getEstacaoFinal().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        LocalDateTime now = LocalDateTime.now();
        trem.setCreatedAt(now);
        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, trem.getUpdatedAt() != null ?
                Timestamp.valueOf(trem.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, trem.getDeletedAt() != null ?
                Timestamp.valueOf(trem.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Trem trem) throws SQLException {

        LocalDateTime now = LocalDateTime.now();
        trem.setUpdatedAt(now);

        int index = 1;
        stmt.setString(index++, trem.getModelo());

        if (trem.getLinha() != null) {
            stmt.setInt(index++, trem.getLinha().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (trem.getEstacaoInicial() != null) {
            stmt.setInt(index++, trem.getEstacaoInicial().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        if (trem.getEstacaoFinal() != null) {
            stmt.setInt(index++, trem.getEstacaoFinal().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, trem.getDeletedAt() != null ?
                Timestamp.valueOf(trem.getDeletedAt()) : null);

        stmt.setInt(index++, trem.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 7;
    }

    @Override
    protected Trem mapResultSetToEntity(ResultSet rs) throws SQLException {
        Trem trem = new Trem();
        trem.setId(rs.getInt("id_trem"));
        trem.setModelo(rs.getString("modelo"));

        Timestamp createdAt = rs.getTimestamp("dt_criacao");
        Timestamp updatedAt = rs.getTimestamp("dt_atualizacao");
        Timestamp deletedAt = rs.getTimestamp("dt_exclusao");

        if (createdAt != null) {
            trem.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            trem.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            trem.setDeletedAt(deletedAt.toLocalDateTime());
        }

        int linhaId = rs.getInt("T_CCR_LINHA_id_linha");
        if (!rs.wasNull() && linhaRepository != null) {
            linhaRepository.buscarPorId(linhaId)
                    .ifPresent(trem::setLinha);
        }

        int estacaoInicialId = rs.getInt("estacao_inicial_id");
        if (!rs.wasNull() && estacaoRepository != null) {
            estacaoRepository.buscarPorId(estacaoInicialId)
                    .ifPresent(trem::setEstacaoInicial);
        }

        int estacaoFinalId = rs.getInt("estacao_final_id");
        if (!rs.wasNull() && estacaoRepository != null) {
            estacaoRepository.buscarPorId(estacaoFinalId)
                    .ifPresent(trem::setEstacaoFinal);
        }

        if (usuarioRepository != null) {
            trem.setCondutores(carregarCondutores(trem.getId()));
        } else {
            trem.setCondutores(new ArrayList<>());
        }

        if (vagaoRepository != null) {
            trem.setVagoes(carregarVagoes(trem.getId()));
        } else {
            trem.setVagoes(new ArrayList<>());
        }

        return trem;
    }

    // metodo pode gerar NullPointer caso não tenha condutores
    public ArrayList<Usuario> carregarCondutores(int tremId) {
        ArrayList<Usuario> condutores = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT T_CCR_USUARIO_id FROM T_CCR_TREM_CONDUTOR " +
                             "WHERE T_CCR_TREM_id = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, tremId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int usuarioId = rs.getInt("T_CCR_USUARIO_id");
                    usuarioRepository.buscarPorId(usuarioId)
                            .ifPresent(condutores::add);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao carregar condutores do trem", e);
        }

        return condutores;
    }

    // metodo pode gerar NullPointer caso não tenha vagoes
    public ArrayList<Vagao> carregarVagoes(int tremId) {
        ArrayList<Vagao> vagoes = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_VAGAO WHERE T_CCR_TREM_id_trem = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, tremId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Vagao vagao = vagaoRepository.mapResultSetToEntity(rs);
                    vagoes.add(vagao);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao carregar vagões do trem", e);
        }

        return vagoes;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<Trem> trem = buscarPorId(id);
                trem.ifPresent(t -> {
                    t.setDeletedAt(LocalDateTime.now());
                    storage.put(id, t);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover trem", e);
        }
    }

    // metodos adicionais para trem

    public List<Trem> buscarPorModelo(String modelo) {
        List<Trem> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_TREM WHERE modelo LIKE ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, "%" + modelo + "%");

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trem trem = mapResultSetToEntity(rs);
                    resultado.add(trem);
                    storage.put(trem.getId(), trem);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar trens por modelo", e);
        }

        return resultado;
    }

    public List<Trem> buscarPorLinha(int linhaId) {
        List<Trem> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_TREM WHERE T_CCR_LINHA_id_linha = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, linhaId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Trem trem = mapResultSetToEntity(rs);
                    resultado.add(trem);
                    storage.put(trem.getId(), trem);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar trens por linha", e);
        }

        return resultado;
    }

    public void adicionarCondutor(Trem trem, Usuario condutor) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "INSERT INTO T_CCR_TREM_CONDUTOR(T_CCR_TREM_id, T_CCR_USUARIO_id, dt_criacao) " +
                             "VALUES (?, ?, ?)")) {

            stmt.setInt(1, trem.getId());
            stmt.setInt(2, condutor.getId());
            stmt.setTimestamp(3, Timestamp.valueOf(LocalDateTime.now()));
            stmt.executeUpdate();

            if (trem.getCondutores() == null) {
                trem.setCondutores(new ArrayList<>());
            }
            trem.getCondutores().add(condutor);

        } catch (SQLException e) {
            log.error("Erro ao adicionar condutor ao trem", e);
        }
    }

    public void removerCondutor(Trem trem, Usuario condutor) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "UPDATE T_CCR_TREM_CONDUTOR SET dt_exclusao = ? " +
                             "WHERE T_CCR_TREM_id = ? AND T_CCR_USUARIO_id = ? AND dt_exclusao IS NULL")) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, trem.getId());
            stmt.setInt(3, condutor.getId());
            stmt.executeUpdate();

            if (trem.getCondutores() != null) {
                trem.getCondutores().removeIf(c -> c.getId() == condutor.getId());
            }

        } catch (SQLException e) {
            log.error("Erro ao remover condutor do trem", e);
        }
    }

    public void adicionarVagao(Trem trem, Vagao vagao) {
        if (vagaoRepository != null) {
            vagao.setTrem(trem);
            vagaoRepository.salvar(vagao);

            if (trem.getVagoes() == null) {
                trem.setVagoes(new ArrayList<>());
            }
            trem.getVagoes().add(vagao);
        }
    }
}
