package ccr.repositories;

import ccr.entities.Gravidade;
import ccr.infrastructure.DatabaseConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GravidadeRepository {

    private static final Logger log = LogManager.getLogger(GravidadeRepository.class);
    private final Map<Gravidade, Integer> gravidadeIdMap = new HashMap<>();
    private final Map<Integer, Gravidade> idGravidadeMap = new HashMap<>();

    public GravidadeRepository() {
        carregarGravidades();
    }

    private void carregarGravidades() {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement("SELECT id_gravidade, nm_gravidade FROM T_CCR_GRAVIDADE WHERE dt_exclusao IS NULL")) {

            try (var rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id_gravidade");
                    String nome = rs.getString("nm_gravidade");

                    try {
                        Gravidade gravidade = Gravidade.valueOf(nome);
                        gravidadeIdMap.put(gravidade, id);
                        idGravidadeMap.put(id, gravidade);
                    } catch (IllegalArgumentException e) {
                        log.error("Gravidade inválida encontrada no banco: {}", nome, e);
                    }
                }
            }
        } catch (SQLException e) {
            log.error("Erro ao carregar gravidades do banco de dados", e);
        }
    }

    public int getGravidadeId(Gravidade gravidade) {
        Integer id = gravidadeIdMap.get(gravidade);
        if (id == null) {
            log.error("ID não encontrado para a gravidade: {}", gravidade);
            return -1;
        }
        return id;
    }

    public Gravidade getGravidadeById(int id) {
        return idGravidadeMap.get(id);
    }

    public void setGravidadeAsId(PreparedStatement stmt, int parameterIndex, Gravidade gravidade) throws SQLException {
        if (gravidade == null) {
            stmt.setNull(parameterIndex, java.sql.Types.INTEGER);
        } else {
            stmt.setInt(parameterIndex, getGravidadeId(gravidade));
        }
    }
}
