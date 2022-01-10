package pl.com.britenet.javastepone.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.test.context.ActiveProfiles;
import pl.com.britenet.javastepone.AbstractEndpointIntegrationTest;
import pl.com.britenet.javastepone.service.dtos.visit.GetVisitDetailsDto;
import pl.com.britenet.javastepone.service.dtos.visit.GetVisitDto;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpStatus.OK;
import static pl.com.britenet.javastepone.utils.PatientTestUtils.patientPath;
import static pl.com.britenet.javastepone.utils.VisitTestUtils.createValidPostVisitDto;
import static pl.com.britenet.javastepone.utils.VisitTestUtils.visitPath;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = "test")
class VisitControllerTest extends AbstractEndpointIntegrationTest {

    @DisplayName("Should save visit")
    @Test
    void create_WhenVisitIsValid_ShouldSaveVisit() {
        //given
        long patientId = 1000001L;
        var visit = createValidPostVisitDto();

        //when
        var response = testRestTemplate
                .withBasicAuth("test1", "test")
                .postForEntity(url(port) + patientPath + "/" + patientId + visitPath, visit, GetVisitDto.class);
        HttpStatus httpStatus = response.getStatusCode();

        var id = Objects.requireNonNull(response.getBody()).getId();

        //then
        assertEquals(OK, httpStatus);

        //clear
        clear(id, "VISIT");
    }

    @DisplayName("Should get all visit")
    @Test
    void getAll_WhenPatientIdIsValid_ShouldGetAllVisit() {
        //given
        long patientId = 1000002L;
        int numberOfVisits = 2;

        //when
        var response = testRestTemplate
                .withBasicAuth("test1", "test")
                .getForEntity(url(port) + patientPath + "/" + patientId + visitPath, GetVisitDto[].class);
        HttpStatus httpStatus = response.getStatusCode();

        int visitCount = Objects.requireNonNull(response.getBody()).length;

        //then
        assertEquals(OK, httpStatus);
        assertEquals(numberOfVisits, visitCount);
    }

    @DisplayName("Should get visit details")
    @Test
    void get_WhenPatientIdAndVisitIdIsValid_ShouldGetVisitDetails() {
        //given
        long patientId = 1000001L;
        long visitId = 1000001L;

        //when
        var response = testRestTemplate
                .withBasicAuth("test1", "test")
                .getForEntity(url(port) + patientPath + "/" + patientId + visitPath + "/" + visitId, GetVisitDetailsDto.class);
        HttpStatus httpStatus = response.getStatusCode();

        //then
        assertEquals(OK, httpStatus);
    }

    @DisplayName("Should delete visit")
    @Test
    void delete_WhenPatientIdAndVisitIdIsValid_ShouldDeleteVisit() {
        //given
        long patientId = 1000001L;
        long visitId = 1000001L;
        MapSqlParameterSource in = new MapSqlParameterSource();
        String sql = "UPDATE VISIT SET STATUS = :STATUS WHERE ID = :ID";
        in.addValue("ID", visitId);
        in.addValue("STATUS", "ACTIVE");

        //when
        var response = testRestTemplate
                .withBasicAuth("test1", "test")
                .exchange(url(port) + patientPath + "/" + patientId + visitPath + "/" + visitId, DELETE, null, String.class);
        HttpStatus httpStatus = response.getStatusCode();
        //then
        assertEquals(OK, httpStatus);

        //clean
        jdbcTemplate.update(sql, in);
    }
}