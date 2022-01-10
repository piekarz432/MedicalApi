package pl.com.medicalApi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

public abstract class AbstractEndpointIntegrationTest {

    @Autowired
    protected NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    protected TestRestTemplate testRestTemplate;

    @LocalServerPort
    protected int port;

    protected static String url(int port) {
        return "http://localhost:" + port + "/britemed";
    }

    protected void clear(Long id, String tableName) {
        String deleteQuery = "DELETE FROM " + tableName + " WHERE ID = :ID";
        SqlParameterSource namedParameters = new MapSqlParameterSource("ID", id);

        jdbcTemplate.update(deleteQuery, namedParameters);
    }

    protected void updateStatus(Long id, String tableName) {
        String updateQuery = "UPDATE " + tableName + " SET STATUS = :STATUS WHERE ID = :ID";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();

        namedParameters.addValue("ID", id);
        namedParameters.addValue("STATUS", "ACTIVE");

        jdbcTemplate.update(updateQuery, namedParameters);
    }
}
