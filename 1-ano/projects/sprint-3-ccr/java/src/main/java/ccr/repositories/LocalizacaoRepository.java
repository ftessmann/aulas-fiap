package ccr.repositories;

import ccr.entities.Localizacao;
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


public class LocalizacaoRepository extends CrudRepositoryImpl<Localizacao> {

    private static final Logger log = LogManager.getLogger(LocalizacaoRepository.class);

    @Override
    protected String getTableName() {
        return "LOCALIZACAO";
    }

    @Override
    protected String getInsertQuery() {
        return "INSERT INTO LOCALIZACAO(LONGITUDE, LATITUDE, CREATED_AT, UPDATED_AT, DELETED_AT) " +
                "VALUES (?, ?, ?, ?, ?)";
    }

    @Override
    protected String getUpdateQuery() {
        return "UPDATE LOCALIZACAO SET LONGITUDE = ?, LATITUDE = ?, UPDATED_AT = ?, DELETED_AT = ? WHERE ID = ?";
    }

    @Override
    protected String getFindByIdQuery() {
        return "SELECT * FROM LOCALIZACAO WHERE ID = ? AND DELETED_AT IS NULL";
    }

    @Override
    protected String getFindAllQuery() {
        return "SELECT * FROM LOCALIZACAO WHERE DELETED_AT IS NULL";
    }

    @Override
    protected String getDeleteQuery() {
        return "UPDATE LOCALIZACAO SET DELETED_AT = ? WHERE ID = ?";
    }

    @Override
    protected void prepareStatementForInsert(PreparedStatement stmt, Localizacao localizacao) throws SQLException {
        int index = 1;
        stmt.setString(index++, localizacao.getLongitude());
        stmt.setString(index++, localizacao.getLatitude());

        stmt.setTimestamp(index++, Timestamp.valueOf(localizacao.getCreatedAt()));
        stmt.setTimestamp(index++, localizacao.getUpdatedAt() != null ?
                Timestamp.valueOf(localizacao.getUpdatedAt()) : null);
        stmt.setTimestamp(index++, localizacao.getDeletedAt() != null ?
                Timestamp.valueOf(localizacao.getDeletedAt()) : null);
    }

    @Override
    protected void prepareStatementForUpdate(PreparedStatement stmt, Localizacao localizacao) throws SQLException {

        localizacao.setUpdatedAt(LocalDateTime.now());

        int index = 1;
        stmt.setString(index++, localizacao.getLongitude());
        stmt.setString(index++, localizacao.getLatitude());

        stmt.setTimestamp(index++, Timestamp.valueOf(localizacao.getUpdatedAt()));
        stmt.setTimestamp(index++, localizacao.getDeletedAt() != null ?
                Timestamp.valueOf(localizacao.getDeletedAt()) : null);

        stmt.setInt(index++, localizacao.getId());
    }

    @Override
    protected int getUpdateQueryIdParameterIndex() {
        return 5;
    }

    @Override
    protected Localizacao mapResultSetToEntity(ResultSet rs) throws SQLException {
        Localizacao localizacao = new Localizacao();
        localizacao.setId(rs.getInt("ID"));
        localizacao.setLongitude(rs.getString("LONGITUDE"));
        localizacao.setLatitude(rs.getString("LATITUDE"));

        Timestamp updatedAt = rs.getTimestamp("UPDATED_AT");
        Timestamp deletedAt = rs.getTimestamp("DELETED_AT");

        if (updatedAt != null) {
            localizacao.setUpdatedAt(updatedAt.toLocalDateTime());
        }
        if (deletedAt != null) {
            localizacao.setDeletedAt(deletedAt.toLocalDateTime());
        }

        return localizacao;
    }

    @Override
    public void remover(int id) {
        try (var connection = DatabaseConfig.getConnection();
             var stmt = connection.prepareStatement(getDeleteQuery())) {

            stmt.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setInt(2, id);

            int result = stmt.executeUpdate();

            if (result > 0) {
                Optional<Localizacao> localizacao = buscarPorId(id);
                localizacao.ifPresent(l -> {
                    l.setDeletedAt(LocalDateTime.now());
                    storage.put(id, l);
                });
            }
        } catch (SQLException e) {
            log.error("Erro ao remover localização", e);
        }
    }

    // metodos para localizacao1

    /**
     * busca localizações dentro de um raio específico
     * @param latitude latitude central
     * @param longitude longitude central
     * @param raioKm raio em quilômetros
     * @return lista de localizações dentro do raio especificado
     */
    public List<Localizacao> buscarPorRaio(String latitude, String longitude, double raioKm) {
        List<Localizacao> resultado = new ArrayList<>();

        try {
            double lat1 = Double.parseDouble(latitude);
            double lon1 = Double.parseDouble(longitude);

            List<Localizacao> todas = listarTodos();

            for (Localizacao loc : todas) {
                try {
                    double lat2 = Double.parseDouble(loc.getLatitude());
                    double lon2 = Double.parseDouble(loc.getLongitude());

                    double distanciaKm = calcularDistancia(lat1, lon1, lat2, lon2);

                    if (distanciaKm <= raioKm) {
                        resultado.add(loc);
                    }
                } catch (NumberFormatException e) {
                    log.warn("Coordenadas inválidas para localização ID: " + loc.getId(), e);
                }
            }
        } catch (NumberFormatException e) {
            log.error("Coordenadas de referência inválidas", e);
        }

        return resultado;
    }

    /**
     * calcula a distância entre dois pontos usando a fórmula de Haversine
     * @param lat1 latitude do ponto 1
     * @param lon1 longitude do ponto 1
     * @param lat2 latitude do ponto 2
     * @param lon2 longitude do ponto 2
     * @return distância em quilômetros
     */
    private double calcularDistancia(double lat1, double lon1, double lat2, double lon2) {

        final double R = 6371.0;

        double lat1Rad = Math.toRadians(lat1);
        double lon1Rad = Math.toRadians(lon1);
        double lat2Rad = Math.toRadians(lat2);
        double lon2Rad = Math.toRadians(lon2);

        double dLat = lat2Rad - lat1Rad;
        double dLon = lon2Rad - lon1Rad;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(lat1Rad) * Math.cos(lat2Rad) *
                        Math.sin(dLon/2) * Math.sin(dLon/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double distancia = R * c;

        return distancia;
    }

    /**
     * busca localizações por proximidade com ordenação
     * @param latitude latitude central
     * @param longitude longitude central
     * @return lista de localizações ordenadas por proximidade
     */
    public List<Localizacao> buscarPorProximidade(String latitude, String longitude) {
        List<Localizacao> todas = listarTodos();

        try {
            double lat1 = Double.parseDouble(latitude);
            double lon1 = Double.parseDouble(longitude);

            todas.sort((loc1, loc2) -> {
                try {
                    double lat2 = Double.parseDouble(loc1.getLatitude());
                    double lon2 = Double.parseDouble(loc1.getLongitude());
                    double dist1 = calcularDistancia(lat1, lon1, lat2, lon2);

                    double lat3 = Double.parseDouble(loc2.getLatitude());
                    double lon3 = Double.parseDouble(loc2.getLongitude());
                    double dist2 = calcularDistancia(lat1, lon1, lat3, lon3);

                    return Double.compare(dist1, dist2);
                } catch (NumberFormatException e) {
                    return 0;
                }
            });
        } catch (NumberFormatException e) {
            log.error("Coordenadas de referência inválidas", e);
        }

        return todas;
    }
}
