package io.aldwindelgado.ingredient.api;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.aldwindelgado.ingredient.api.exchange.IngredientRequestDto;
import io.aldwindelgado.ingredient.api.exchange.IngredientResponseDto;
import io.aldwindelgado.ingredient.service.IngredientService;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotAcceptableException;
import javax.ws.rs.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Quarkus not yet properly supports multiple @Nested of JUnit 5
 *
 * @author Aldwin Delgado
 */
@QuarkusTest
class IngredientResourceTest {

    private static final String PROBLEM_JSON_CONTENT_TYPE = "application/problem+json";
    private final static String GET_BY_NAME_PATH = "{name}";

    @InjectMock
    IngredientService service;

    @Nested
    @TestHTTPEndpoint(IngredientResource.class)
    class GetIngredientByName {

        @Test
        void getByName_whenFound_thenReturn200() {
            final var response = new IngredientResponseDto("cream", List.of("Banana Split"));
            Mockito.when(service.getByName(anyString())).thenReturn(response);

            RestAssured.given()
                .pathParam("name", "cream")
                .when().get(GET_BY_NAME_PATH)
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("name", Matchers.equalTo("cream"))
                .body("products[0]", Matchers.equalTo("Banana Split"));
        }

        @Test
        void getByName_whenServiceThrowsBadRequestException_thenReturn400() {
            BadRequestException exception = new BadRequestException("Mocked exception message");
            Mockito.when(service.getByName(anyString())).thenThrow(exception);

            RestAssured.given()
                .pathParam("name", "cream")
                .when().get(GET_BY_NAME_PATH)
                .then()
                .statusCode(400)
                .contentType(PROBLEM_JSON_CONTENT_TYPE)
                .body("$", Matchers.hasKey("timestamp"))
                .body("message", Matchers.equalTo("Mocked exception message"));
        }

        @Test
        void getByName_whenServiceThrowsNotFoundException_thenReturn404() {
            NotFoundException exception = new NotFoundException("Mocked exception message");
            Mockito.when(service.getByName(anyString())).thenThrow(exception);

            RestAssured.given()
                .pathParam("name", "cream")
                .when().get(GET_BY_NAME_PATH)
                .then()
                .statusCode(404)
                .contentType(PROBLEM_JSON_CONTENT_TYPE)
                .body("$", Matchers.hasKey("timestamp"))
                .body("message", Matchers.equalTo("Mocked exception message"));
        }

        @Test
        void getByName_whenServiceThrowsNotHandledException_thenReturn500() {
            NotAcceptableException exception = new NotAcceptableException("Mocked exception message");
            Mockito.when(service.getByName(anyString())).thenThrow(exception);

            RestAssured.given()
                .pathParam("name", "cream")
                .when().get(GET_BY_NAME_PATH)
                .then()
                .statusCode(500)
                .contentType(PROBLEM_JSON_CONTENT_TYPE)
                .body("$", Matchers.hasKey("timestamp"))
                .body("message", Matchers.equalTo("Mocked exception message"));
        }
    }

    @Nested
    @TestHTTPEndpoint(IngredientResource.class)
    class GetAllIngredients {

        @Test
        void getAll_whenFound_thenReturn200() {
            final var response = List.of(new IngredientResponseDto("cream", List.of("Banana Split")));
            Mockito.when(service.getAll()).thenReturn(response);

            RestAssured.given()
                .when().get()
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("[0].name", Matchers.equalTo("cream"))
                .body("[0].products[0]", Matchers.equalTo("Banana Split"));

        }

        @Test
        void getAll_whenServiceThrowsNotFoundException_thenReturn404() {
            NotFoundException exception = new NotFoundException("Mocked exception message");
            Mockito.when(service.getAll()).thenThrow(exception);

            RestAssured.given()
                .when().get()
                .then()
                .statusCode(404)
                .contentType(PROBLEM_JSON_CONTENT_TYPE)
                .body("$", Matchers.hasKey("timestamp"))
                .body("message", Matchers.equalTo("Mocked exception message"));
        }

        @Test
        void getAll_whenServiceThrowsNotHandledException_thenReturn500() {
            NotAcceptableException exception = new NotAcceptableException("Mocked exception message");
            Mockito.when(service.getAll()).thenThrow(exception);

            RestAssured.given()
                .when().get()
                .then()
                .statusCode(500)
                .contentType(PROBLEM_JSON_CONTENT_TYPE)
                .body("$", Matchers.hasKey("timestamp"))
                .body("message", Matchers.equalTo("Mocked exception message"));
        }
    }


    @Nested
    @TestHTTPEndpoint(IngredientResource.class)
    class CreateIngredients {

        @Test
        void create_whenSuccess_thenReturn201() throws JsonProcessingException {
            final var mapper = new ObjectMapper();
            final var rawJson = mapper.createObjectNode();
            rawJson.put("name", "cream");
            final var request = mapper.writeValueAsString(rawJson);

            final var response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post()
                .then()
                .statusCode(201)
                .header("Location", Matchers.endsWith("/ingredients/cream"))
                .extract().asString();

            assertTrue(response.isEmpty(), "Response should be empty");
        }

        @Test
        void create_whenServiceThrowsBadRequestException_thenReturn400() throws JsonProcessingException {
            BadRequestException exception = new BadRequestException("Mocked exception message");
            Mockito.doThrow(exception).when(service).create(any(IngredientRequestDto.class));

            final var mapper = new ObjectMapper();
            final var rawJson = mapper.createObjectNode();
            rawJson.put("name", "cream");
            final var request = mapper.writeValueAsString(rawJson);

            RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post()
                .then()
                .statusCode(400)
                .contentType(PROBLEM_JSON_CONTENT_TYPE)
                .body("$", Matchers.hasKey("timestamp"))
                .body("message", Matchers.equalTo("Mocked exception message"));
        }

        @Test
        void create_whenServiceThrowsNotHandledException_thenReturn500() throws JsonProcessingException {
            NotAcceptableException exception = new NotAcceptableException("Mocked exception message");
            Mockito.doThrow(exception).when(service).create(any(IngredientRequestDto.class));

            final var mapper = new ObjectMapper();
            final var rawJson = mapper.createObjectNode();
            rawJson.put("name", "cream");
            final var request = mapper.writeValueAsString(rawJson);

            RestAssured.given()
                .contentType(ContentType.JSON)
                .body(request)
                .when().post()
                .then()
                .statusCode(500)
                .contentType(PROBLEM_JSON_CONTENT_TYPE)
                .body("$", Matchers.hasKey("timestamp"))
                .body("message", Matchers.equalTo("Mocked exception message"));
        }
    }
}
