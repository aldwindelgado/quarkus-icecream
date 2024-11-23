package io.aldwindelgado.sourcingvalue.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueRequestDto;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueResponseDto;
import io.aldwindelgado.sourcingvalue.service.SourcingValueService;
import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Quarkus not yet properly supports multiple @Nested of JUnit 5
 *
 * @author Aldwin Delgado
 */
@QuarkusTest
@TestHTTPEndpoint(SourcingValueResource.class)
class SourcingValueResourceTest {

    private static final String PROBLEM_JSON_CONTENT_TYPE = "application/problem+json";
    private static final String GET_BY_NAME_PATH = "{name}";

    @InjectMock
    SourcingValueService service;

    @Test
    void getByName_whenFound_thenReturn200() {
        final var response = new SourcingValueResponseDto("Non-GMO", List.of("Banana Split"));
        Mockito.when(service.getByName(anyString())).thenReturn(response);

        RestAssured.given()
            .pathParam("name", "non-gmo")
            .when().get(GET_BY_NAME_PATH)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("name", Matchers.equalTo("Non-GMO"))
            .body("products[0]", Matchers.equalTo("Banana Split"));
    }

    @Test
    void getByName_whenServiceThrowsBadRequestException_thenReturn400() {
        BadRequestException exception = new BadRequestException("Mocked response message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "non-gmo")
            .when().get(GET_BY_NAME_PATH)
            .then()
            .statusCode(400)
            .contentType(PROBLEM_JSON_CONTENT_TYPE)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getByName_whenServiceThrowsNotFoundException_thenReturn404() {
        NotFoundException exception = new NotFoundException("Mocked response message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "non-gmo")
            .when().get(GET_BY_NAME_PATH)
            .then()
            .statusCode(404)
            .contentType(PROBLEM_JSON_CONTENT_TYPE)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getByName_whenServiceThrowsNotFoundException_thenReturn500() {
        NotAcceptableException exception = new NotAcceptableException("Mocked response message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "non-gmo")
            .when().get(GET_BY_NAME_PATH)
            .then()
            .statusCode(500)
            .contentType(PROBLEM_JSON_CONTENT_TYPE)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getAll_whenFound_thenReturn200() {
        final var response = List.of(new SourcingValueResponseDto("Non-GMO", List.of("Banana Split")));
        Mockito.when(service.getAll()).thenReturn(response);

        RestAssured.given()
            .when().get()
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("[0].name", Matchers.equalTo("Non-GMO"))
            .body("[0].products[0]", Matchers.equalTo("Banana Split"));
    }

    @Test
    void getAll_whenServiceThrowsNotFoundException_thenReturn404() {
        NotFoundException exception = new NotFoundException("Mocked response message");
        Mockito.when(service.getAll()).thenThrow(exception);

        RestAssured.given()
            .when().get()
            .then()
            .statusCode(404)
            .contentType(PROBLEM_JSON_CONTENT_TYPE)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getAll_whenServiceThrowsNotHandledException_thenReturn500() {
        NotAcceptableException exception = new NotAcceptableException("Mocked response message");
        Mockito.when(service.getAll()).thenThrow(exception);

        RestAssured.given()
            .when().get()
            .then()
            .statusCode(500)
            .contentType(PROBLEM_JSON_CONTENT_TYPE)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void create_whenSuccess_thenReturn201() throws JsonProcessingException {
        final var mapper = new ObjectMapper();
        final var rawJson = mapper.createObjectNode();
        rawJson.put("name", "non-gmo");
        final var request = mapper.writeValueAsString(rawJson);

        final var response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post()
            .then()
            .statusCode(201)
            .header("Location", Matchers.endsWith("/sourcing-values/non-gmo"))
            .extract().asString();

        // we expect the response should be empty
        Assertions.assertEquals("", response);
    }

    @Test
    void create_whenServiceThrowsBadRequestException_thenReturn400() throws JsonProcessingException {
        BadRequestException exception = new BadRequestException("Mocked response message");
        Mockito.doThrow(exception).when(service).create(any(SourcingValueRequestDto.class));

        final var mapper = new ObjectMapper();
        final var rawJson = mapper.createObjectNode();
        rawJson.put("name", "non-gmo");
        final var request = mapper.writeValueAsString(rawJson);

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post()
            .then()
            .statusCode(400)
            .contentType(PROBLEM_JSON_CONTENT_TYPE)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void create_whenServiceThrowsNotHandledException_thenReturn500() throws JsonProcessingException {
        NotAcceptableException exception = new NotAcceptableException("Mocked response message");
        Mockito.doThrow(exception).when(service).create(any(SourcingValueRequestDto.class));

        final var mapper = new ObjectMapper();
        final var rawJson = mapper.createObjectNode();
        rawJson.put("name", "non-gmo");
        final var request = mapper.writeValueAsString(rawJson);

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post()
            .then()
            .statusCode(500)
            .contentType(PROBLEM_JSON_CONTENT_TYPE)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }
}
