package ccr.repositories;

import ccr.entities.Endereco;
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

public class EnderecoRepository extends CrudRepositoryImpl<Endereco> {

    private static final Logger log = LogManager.getLogger(EnderecoRepository.class);

    @Override
    protected String getTableName() {
        return "T_CCR_ENDERECO";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO T_CCR_ENDERECO(cep, nm_rua, nr_endereco, nm_bairro, nm_cidade, nm_estado, complemento, " +
                "dt_criacao, dt_atualizacao, dt_exclusao) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE T_CCR_ENDERECO SET cep = ?, nm_rua = ?, nr_endereco = ?, nm_bairro = ?, nm_cidade = ?, " +
                "nm_estado = ?, complemento = ?, dt_atualizacao = ?, dt_exclusao = ? WHERE id_endereco = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM T_CCR_ENDERECO WHERE id_endereco = ? AND dt_exclusao IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM T_CCR_ENDERECO WHERE dt_exclusao IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE T_CCR_ENDERECO SET dt_exclusao = ? WHERE id_endereco = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Endereco endereco) throws SQLException {
        int index = 1;
        stmt.setString(index++, endereco.getCep());
        stmt.setString(index++, endereco.getRua());
        stmt.setString(index++, endereco.getNumero());
        stmt.setString(index++, endereco.getBairro());
        stmt.setString(index++, endereco.getCidade());
        stmt.setString(index++, endereco.getEstado());
        stmt.setString(index++, endereco.getComplemento());

        LocalDateTime now = LocalDateTime.now();
        endereco.setCreatedAt(now);
        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, endereco.getUpdatedAt() != null ?
                Timestamp.valueOf(endereco.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, endereco.getDeletedAt() != null ?
                Timestamp.valueOf(endereco.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Endereco endereco) throws SQLException {
        int index = 1;
        stmt.setString(index++, endereco.getCep());
        stmt.setString(index++, endereco.getRua());
        stmt.setString(index++, endereco.getNumero());
        stmt.setString(index++, endereco.getBairro());
        stmt.setString(index++, endereco.getCidade());
        stmt.setString(index++, endereco.getEstado());
        stmt.setString(index++, endereco.getComplemento());

        LocalDateTime now = LocalDateTime.now();
        endereco.setUpdatedAt(now);
        stmt.setTimestamp(index++, Timestamp.valueOf(now));
        stmt.setTimestamp(index++, endereco.getDeletedAt() != null ?
                Timestamp.valueOf(endereco.getDeletedAt()) : null);

        stmt.setInt(index++, endereco.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 10;
    }

    @Override
    protected Endereco mapResultSetToEntity(ResultSet rs) throws SQLException {
        Endereco endereco = new Endereco();
        endereco.setId(rs.getInt("id_endereco"));
        endereco.setCep(rs.getString("cep"));
        endereco.setRua(rs.getString("nm_rua"));
        endereco.setNumero(rs.getString("nr_endereco"));
        endereco.setBairro(rs.getString("nm_bairro"));
        endereco.setCidade(rs.getString("nm_cidade"));
        endereco.setEstado(rs.getString("nm_estado"));
        endereco.setComplemento(rs.getString("complemento"));

        Timestamp createdAt = rs.getTimestamp("dt_criacao");
        Timestamp updatedAt = rs.getTimestamp("dt_atualizacao");
        Timestamp deletedAt = rs.getTimestamp("dt_exclusao");

        if (createdAt != null) {
            endereco.setCreatedAt(createdAt.toLocalDateTime());
        }
        if (updatedAt != null) {
            endereco.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            endereco.setDeletedAt(deletedAt.toLocalDateTime());
        }

        return endereco;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<Endereco> endereco = buscarPorId(id);
                endereco.ifPresent(e -> {
                    e.setDeletedAt(LocalDateTime.now());
                    storage.put(id, e);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover endereço", e);
        }
    }

    // metodos adicionais para endereco

    public List<Endereco> buscarPorCep(String cep) {
        List<Endereco> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_ENDERECO WHERE cep = ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, cep);

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Endereco endereco = mapResultSetToEntity(rs);
                    resultado.add(endereco);
                    storage.put(endereco.getId(), endereco);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar endereços por CEP", e);
        }

        return resultado;
    }

    public List<Endereco> buscarPorCidade(String cidade) {
        List<Endereco> resultado = new ArrayList<>();

        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(
                     "SELECT * FROM T_CCR_ENDERECO WHERE nm_cidade LIKE ? AND dt_exclusao IS NULL")) {

            stmt.setString(1, "%" + cidade + "%");

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Endereco endereco = mapResultSetToEntity(rs);
                    resultado.add(endereco);
                    storage.put(endereco.getId(), endereco);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar endereços por cidade", e);
        }

        return resultado;
    }
}
