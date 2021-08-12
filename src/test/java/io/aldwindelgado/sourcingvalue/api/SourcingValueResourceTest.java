package io.aldwindelgado.sourcingvalue.api;

import static org.mockito.ArgumentMatchers.anyString;

import io.aldwindelgado.sourcingvalue.service.SourcingValueService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.List;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author Aldwin Delgado
 */
@QuarkusTest
class SourcingValueResourceTest {

    private final static String GET_BY_NAME_URI = "/sourcing-values/{name}";
    private final static String GET_ALL_URI = "/sourcing-values";

    @InjectMock
    SourcingValueService service;

    @Test
    void getByName_whenFound_thenReturn200() {
        final var response = new SourcingValueResponseDto("Non-GMO", List.of("Banana Split"));
        Mockito.when(service.getByName(anyString())).thenReturn(response);

        RestAssured.given()
            .pathParam("name", "non-gmo")
            .when().get(GET_BY_NAME_URI)
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
            .when().get(GET_BY_NAME_URI)
            .then()
            .statusCode(400)
            .contentType("application/problem+json")
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getByName_whenServiceThrowsNotFoundException_thenReturn404() {
        NotFoundException exception = new NotFoundException("Mocked response message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "non-gmo")
            .when().get(GET_BY_NAME_URI)
            .then()
            .statusCode(404)
            .contentType("application/problem+json")
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }

    @Test
    void getAll_whenFound_thenReturn200() {
        final var response = List.of(new SourcingValueResponseDto("Non-GMO", List.of("Banana Split")));
        Mockito.when(service.getAll()).thenReturn(response);

        RestAssured.given()
            .when().get(GET_ALL_URI)
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
            .when().get(GET_ALL_URI)
            .then()
            .statusCode(404)
            .contentType("application/problem+json")
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked response message"));
    }
}
