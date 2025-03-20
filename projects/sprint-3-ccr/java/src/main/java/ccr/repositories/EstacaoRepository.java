package ccr.repositories;

import ccr.entities.Estacao;
import ccr.entities.Endereco;
import ccr.entities.Plataforma;
import ccr.entities.Linha;
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

public class EstacaoRepository extends CrudRepositoryImpl<Estacao> {

    private static final Logger log = LogManager.getLogger(EstacaoRepository.class);

    private final EnderecoRepository enderecoRepository;
    private final PlataformaRepository plataformaRepository;
    private final LinhaRepository linhaRepository;

    public EstacaoRepository(
            EnderecoRepository enderecoRepository,
            PlataformaRepository plataformaRepository,
            LinhaRepository linhaRepository
    ) {
        this.enderecoRepository = enderecoRepository;
        this.plataformaRepository = plataformaRepository;
        this.linhaRepository = linhaRepository;
    }

    public EstacaoRepository(EnderecoRepository enderecoRepository) {
        this.enderecoRepository = enderecoRepository;
        this.plataformaRepository = null;
        this.linhaRepository = null;
    }

    @Override
    protected String getTableName() {
        return "T_CCR_ESTACAO";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO T_CCR_ESTACAO(nm_estacao, T_CCR_ENDERECO_id_endereco, dt_criacao, dt_atualizacao, dt_exclusao) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE T_CCR_ESTACAO SET nm_estacao = ?, T_CCR_ENDERECO_id_endereco = ?, " +
                "dt_atualizacao = ?, dt_exclusao = ? WHERE id_estacao = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM T_CCR_ESTACAO WHERE id_estacao = ? AND dt_exclusao IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM T_CCR_ESTACAO WHERE dt_exclusao IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE T_CCR_ESTACAO SET dt_exclusao = ? WHERE id_estacao = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Estacao estacao) throws SQLException {
        int index = 1;
        stmt.setString(index++, estacao.getNome());

        if (estacao.getEndereco() != null) {
            stmt.setInt(index++, estacao.getEndereco().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        LocalDateTime now = LocalDateTime.now();
        estacao.setCreatedAt(now);
        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, estacao.getUpdatedAt() != null ?
                Timestamp.valueOf(estacao.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, estacao.getDeletedAt() != null ?
                Timestamp.valueOf(estacao.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Estacao estacao) throws SQLException {

        LocalDateTime now = LocalDateTime.now();
        estacao.setUpdatedAt(now);

        int index = 1;
        stmt.setString(index++, estacao.getNome());

        if (estacao.getEndereco() != null) {
            stmt.setInt(index++, estacao.getEndereco().getId());
        } else {
            stmt.setNull(index++, java.sql.Types.INTEGER);
        }

        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, estacao.getDeletedAt() != null ?
                Timestamp.valueOf(estacao.getDeletedAt()) : null);

        stmt.setInt(index++, estacao.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 5;
    }

    @Override
    protected Estacao mapResultSetToEntity(ResultSet rs) throws SQLException {
        Estacao estacao = new Estacao();
        estacao.setId(rs.getInt("id_estacao"));
        estacao.setNome(rs.getString("nm_estacao"));

        Timestamp createdAt = rs.getTimestamp("dt_criacao");
        Timestamp updatedAt = rs.getTimestamp("dt_atualizacao");
        Timestamp deletedAt = rs.getTimestamp("dt_exclusao");

        if (createdAt != null) {
            estacao.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            estacao.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            estacao.setDeletedAt(deletedAt.toLocalDateTime());
        }

        int enderecoId = rs.getInt("T_CCR_ENDERECO_id_endereco");
        if (!rs.wasNull() && enderecoRepository != null) {
            enderecoRepository.buscarPorId(enderecoId)
                    .ifPresent(estacao::setEndereco);
        }

        if (plataformaRepository != null) {
            estacao.setPlataformas(carregarPlataformas(estacao.getId()));
        } else {
            estacao.setPlataformas(new ArrayList<>());
        }

        if (linhaRepository != null) {
            estacao.setLinhas(carregarLinhas(estacao.getId()));
        } else {
            estacao.setLinhas(new ArrayList<>());
        }

        return estacao;
    }
    // rodar este metodo sem plataformas inseridas irá causar um NullPointer
    private ArrayList<Plataforma> carregarPlataformas(int estacaoId) {
        ArrayList<Plataforma> plataformas = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_PLATAFORMA WHERE T_CCR_ESTACAO_id_estacao = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, estacaoId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Plataforma plataforma = plataformaRepository.mapResultSetToEntity(rs);
                    plataformas.add(plataforma);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao carregar plataformas da estação", e);
        }

        return plataformas;
    }

    // rodar este metodo sem linhas inseridas irá causar um NullPointer
    private ArrayList<Linha> carregarLinhas(int estacaoId) {
        ArrayList<Linha> linhas = new ArrayList<>();
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT l.* FROM T_CCR_LINHA l " +
                             "JOIN T_CCR_LINHA_ESTACAO le ON l.id_linha = le.T_CCR_LINHA_id " +
                             "WHERE le.T_CCR_ESTACAO_id = ? AND l.dt_exclusao IS NULL AND le.dt_exclusao IS NULL " +
                             "ORDER BY le.ordem")) {

            stmt.setInt(1, estacaoId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Linha linha = linhaRepository.mapResultSetToEntity(rs);
                    linhas.add(linha);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao carregar linhas da estação", e);
        }

        return linhas;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {

                Optional<Estacao> estacao = buscarPorId(id);
                estacao.ifPresent(e -> {
                    e.setDeletedAt(LocalDateTime.now());
                    storage.put(id, e);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover estação", e);
        }
    }

    // metodos adicionais para Estacao

    public Optional<Estacao> buscarPorNome(String nome) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_ESTACAO WHERE nm_estacao = ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, nome);

            try (var rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Estacao estacao = mapResultSetToEntity(rs);
                    storage.put(estacao.getId(), estacao);
                    return Optional.of(estacao);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar estação por nome", e);
        }

        return Optional.empty();
    }

    public List<Estacao> buscarPorNomeParcial(String nome) {
        List<Estacao> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_ESTACAO WHERE nm_estacao LIKE ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, "%" + nome + "%");

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Estacao estacao = mapResultSetToEntity(rs);
                    resultado.add(estacao);
                    storage.put(estacao.getId(), estacao);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar estações por nome parcial", e);
        }

        return resultado;
    }

    public List<Estacao> buscarPorEndereco(int enderecoId) {
        List<Estacao> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_ESTACAO WHERE T_CCR_ENDERECO_id_endereco = ? AND dt_exclusao IS NULL")) {

            stmt.setInt(1, enderecoId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Estacao estacao = mapResultSetToEntity(rs);
                    resultado.add(estacao);
                    storage.put(estacao.getId(), estacao);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar estações por endereço", e);
        }

        return resultado;
    }

    public List<Estacao> buscarPorLinha(int linhaId) {
        List<Estacao> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT e.* FROM T_CCR_ESTACAO e " +
                             "JOIN T_CCR_LINHA_ESTACAO le ON e.id_estacao = le.T_CCR_ESTACAO_id " +
                             "WHERE le.T_CCR_LINHA_id = ? AND e.dt_exclusao IS NULL AND le.dt_exclusao IS NULL " +
                             "ORDER BY le.ordem")) {

            stmt.setInt(1, linhaId);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Estacao estacao = mapResultSetToEntity(rs);
                    resultado.add(estacao);
                    storage.put(estacao.getId(), estacao);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar estações por linha", e);
        }

        return resultado;
    }

    public void adicionarPlataforma(Estacao estacao, Plataforma plataforma) {
        if (plataformaRepository != null) {
            plataforma.setEstacao(estacao);
            plataformaRepository.salvar(plataforma);

            if (estacao.getPlataformas() == null) {
                estacao.setPlataformas(new ArrayList<>());
            }
            estacao.getPlataformas().add(plataforma);
        }
    }

}
