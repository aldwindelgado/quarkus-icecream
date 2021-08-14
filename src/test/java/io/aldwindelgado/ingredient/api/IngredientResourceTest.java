package io.aldwindelgado.ingredient.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.aldwindelgado.ingredient.api.exchange.IngredientRequestDto;
import io.aldwindelgado.ingredient.api.exchange.IngredientResponseDto;
import io.aldwindelgado.ingredient.service.IngredientService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;
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
class IngredientResourceTest {

    private static final String APPLICATION_PROBLEM_JSON = "application/problem+json";
    private final static String GET_BY_NAME_URI = "/ingredients/{name}";
    private final static String BASE_URI = "/ingredients";

    @InjectMock
    IngredientService service;

    @Test
    void getByName_whenFound_thenReturn200() {
        final var response = new IngredientResponseDto("cream", List.of("Banana Split"));
        Mockito.when(service.getByName(anyString())).thenReturn(response);

        RestAssured.given()
            .pathParam("name", "cream")
            .when().get(GET_BY_NAME_URI)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("name", Matchers.equalTo("cream"))
            .body("products[0]", Matchers.equalTo("Banana Split"));
    }

    @Test
    void getByName_whenServiceThrowsBadRequestException_thenReturn400() {
        BadRequestException exception = new BadRequestException("Mocked response message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "cream")
            .when().get(GET_BY_NAME_URI)
            .then()
            .statusCode(400)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getByName_whenServiceThrowsNotFoundException_thenReturn404() {
        NotFoundException exception = new NotFoundException("Mocked response message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "cream")
            .when().get(GET_BY_NAME_URI)
            .then()
            .statusCode(404)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getByName_whenServiceThrowsNotHandledException_thenReturn500() {
        NotAcceptableException exception = new NotAcceptableException("Mocked response message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "cream")
            .when().get(GET_BY_NAME_URI)
            .then()
            .statusCode(500)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getAll_whenFound_thenReturn200() {
        final var response = List.of(new IngredientResponseDto("cream", List.of("Banana Split")));
        Mockito.when(service.getAll()).thenReturn(response);

        RestAssured.given()
            .when().get(BASE_URI)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("[0].name", Matchers.equalTo("cream"))
            .body("[0].products[0]", Matchers.equalTo("Banana Split"));

    }

    @Test
    void getAll_whenServiceThrowsNotFoundException_thenReturn404() {
        NotFoundException exception = new NotFoundException("Mocked response message");
        Mockito.when(service.getAll()).thenThrow(exception);

        RestAssured.given()
            .when().get(BASE_URI)
            .then()
            .statusCode(404)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getAll_whenServiceThrowsNotHandledException_thenReturn500() {
        NotAcceptableException exception = new NotAcceptableException("Mocked response message");
        Mockito.when(service.getAll()).thenThrow(exception);

        RestAssured.given()
            .when().get(BASE_URI)
            .then()
            .statusCode(500)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void create_whenSuccess_thenReturn201() throws JsonProcessingException {
        final var mapper = new ObjectMapper();
        final var rawJson = mapper.createObjectNode();
        rawJson.put("name", "cream");
        final var request = mapper.writeValueAsString(rawJson);

        final var response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post(BASE_URI)
            .then()
            .statusCode(201)
            .header("Location", Matchers.endsWith("/ingredients/cream"))
            .extract().asString();

        // we expect the response should be empty
        Assertions.assertEquals("", response);
    }

    @Test
    void create_whenServiceThrowsBadRequestException_thenReturn400() throws JsonProcessingException {
        BadRequestException exception = new BadRequestException("Mocked response message");
        Mockito.doThrow(exception).when(service).create(any(IngredientRequestDto.class));

        final var mapper = new ObjectMapper();
        final var rawJson = mapper.createObjectNode();
        rawJson.put("name", "cream");
        final var request = mapper.writeValueAsString(rawJson);

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post(BASE_URI)
            .then()
            .statusCode(400)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void create_whenServiceThrowsNotHandledException_thenReturn400() throws JsonProcessingException {
        NotAcceptableException exception = new NotAcceptableException("Mocked response message");
        Mockito.doThrow(exception).when(service).create(any(IngredientRequestDto.class));

        final var mapper = new ObjectMapper();
        final var rawJson = mapper.createObjectNode();
        rawJson.put("name", "cream");
        final var request = mapper.writeValueAsString(rawJson);

        RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post(BASE_URI)
            .then()
            .statusCode(500)
            .contentType(APPLICATION_PROBLEM_JSON)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }
}
