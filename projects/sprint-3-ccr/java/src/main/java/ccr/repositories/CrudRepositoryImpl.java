package ccr.repositories;

import ccr.entities.BaseModel;
import ccr.infrastructure.DatabaseConfig;

import java.sql.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// recebe o tipo T genérico que extende BaseModel
public abstract class CrudRepositoryImpl<T extends BaseModel> implements CrudRepository<T> {
    private static final Logger log = LogManager.getLogger(CrudRepositoryImpl.class);

    protected Map<Integer, T> storage = new HashMap<>();

    protected abstract String getTableName();

    // prepara o stmt para insert
    protected abstract void prepareStatementForInsert(PreparedStatement stmt, T object) throws SQLException;

    // prepara stmt para update
    protected abstract void prepareStatementForUpdate(PreparedStatement stmt, T object) throws SQLException;

    // converte o resultado em objeto
    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    // obtem a query de inserção
    protected abstract String getInsertQuery();

    // obte a query de atualizacao
    protected abstract String getUpdateQuery();

    // obtem a query para deletar - soft delete, seta a dt_exclusao, mantendo o registro no banco
    protected String getDeleteQuery() {
        return "UPDATE " + getTableName() + " SET dt_exclusao = ? WHERE " + getIdColumnName() + " = ?";
    }

    // obtem a query de consulta por id
    protected String getFindByIdQuery() {
        return "SELECT * FROM " + getTableName() + " WHERE " + getIdColumnName() + " = ? AND dt_exclusao IS NULL";
    }

    // obtem a query de consulta para todos
    protected String getFindAllQuery() {
        return "SELECT * FROM " + getTableName() + " WHERE dt_exclusao IS NULL";
    }

    // obtem o nome da coluna de id seguindo o padrão do banco, "id_" + nome da tabela
    protected String getIdColumnName() {
        String tableName = getTableName();
        if (tableName.startsWith("T_CCR_")) {
            tableName = tableName.substring(6).toLowerCase();
        }
        return "id_" + tableName;
    }

    // implementação padrão de métodos para repositories
    @Override
    public T salvar(T object) {
        if (object.getId() == 0) {
            return adicionar(object);
        } else {
            atualizar(object.getId(), object);
            return object;
        }
    }

    // implementação padrão de métodos para repositories
    public T adicionar(T object) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement stmt = connection.prepareStatement(
                     getInsertQuery(),
                     new String[] {getIdColumnName()})) {

            prepareStatementForInsert(stmt, object);
            stmt.executeUpdate();

            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int id = rs.getInt(1);
                    object.setId(id);
                    storage.put(id, object);
                }
            }

            return object;
        } catch (SQLException e) {
            log.error("Erro ao adicionar objeto", e);
            return null;
        }
    }

    // implementação padrão de métodos para repositories
    @Override
    public void atualizar(int id, T object) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement stmt = connection.prepareStatement(getUpdateQuery())) {

            object.setId(id);
            prepareStatementForUpdate(stmt, object);

            int result = stmt.executeUpdate();
            if (result > 0) {
                storage.put(id, object);
            }
        } catch (SQLException e) {
            log.error("Erro ao atualizar objeto", e);
        }
    }

    protected int getUpdateQueryIdParameterIndex() {
        return 5; // valos ajustado conforme o repository
    }

    @Override
    public void remover(T object) {
        remover(object.getId());
    }

    // implementação padrão de métodos para repositories
    @Override
    public void remover(int id) {
        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, java.sql.Timestamp.valueOf(java.time.LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<T> entity = buscarPorId(id);
                entity.ifPresent(e -> {
                    e.setDeletedAt(java.time.LocalDateTime.now());
                    storage.put(id, e);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover objeto", e);
        }
    }

    // métodos presentes, mas não utilizados por boas práticas
    @Override
    public void delete(T object) {
        remover(object);
    }

    @Override
    public void deleteById(int id) {
        remover(id);
    }

    // implementação padrão de métodos para repositories
    @Override
    public List<T> listarTodos() {
        List<T> result = new ArrayList<>();

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement stmt = connection.prepareStatement(getFindAllQuery());
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                T entity = mapResultSetToEntity(rs);
                result.add(entity);
                storage.put(entity.getId(), entity);
            }
        } catch (SQLException e) {
            log.error("Erro ao listar objetos", e);
        }

        return result;
    }

    @Override
    public List<T> listar() {
        return listarTodos();
    }

    @Override
    public Optional<T> buscarPorId(int id) {
        // verifica primeiro se há em memória antes de executar a query para preservar requests
        if (storage.containsKey(id)) {
            return Optional.of(storage.get(id));
        }

        try (Connection connection = DatabaseConfig.getConnection();
             PreparedStatement stmt = connection.prepareStatement(getFindByIdQuery())) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    T entity = mapResultSetToEntity(rs);
                    storage.put(entity.getId(), entity);
                    return Optional.of(entity);
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao buscar objeto por ID", e);
        }

        return Optional.empty();
    }
}
