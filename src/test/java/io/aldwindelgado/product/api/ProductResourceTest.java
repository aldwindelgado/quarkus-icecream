package io.aldwindelgado.product.api;

import static org.mockito.ArgumentMatchers.anyString;

import io.aldwindelgado.product.service.ProductService;
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
class ProductResourceTest {

    private final static String GET_BY_NAME_URI = "/products/{name}";
    private final static String GET_ALL_URI = "/products";

    @InjectMock
    ProductService service;

    @Test
    void getByName_whenFound_thenReturn200() {
        final var response = ProductResponseDto.builder()
            .name("Banana Split")
            .imageClosed(
                "/files/live/sites/systemsite/files/flavors/products/us/pint/open-closed-pints/banana-split-landing.png")
            .imageOpen(
                "/files/live/sites/systemsite/files/flavors/products/us/pint/open-closed-pints/banana-split-landing-open.png")
            .description("Banana & Strawberry Ice Creams with Walnuts, Fudge Chunks & a Fudge Swirl")
            .story(
                "We turned the classic ice cream parlor sundae you've always loved into the at-home flavor creation you've always wanted. Enjoy!")
            .allergyInfo("may contain other tree nuts")
            .dietaryCertifications("Kosher")
            .build();
        Mockito.when(service.getByName(anyString())).thenReturn(response);

        RestAssured.given()
            .pathParam("name", "Banana Split")
            .when().get(GET_BY_NAME_URI)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("name", Matchers.equalTo("Banana Split"))
            .body("imageClosed", Matchers.equalTo(
                "/files/live/sites/systemsite/files/flavors/products/us/pint/open-closed-pints/banana-split-landing.png"))
            .body("imageOpen", Matchers.equalTo(
                "/files/live/sites/systemsite/files/flavors/products/us/pint/open-closed-pints/banana-split-landing-open.png"))
            .body("description",
                Matchers.equalTo("Banana & Strawberry Ice Creams with Walnuts, Fudge Chunks & a Fudge Swirl"))
            .body("story", Matchers.equalTo(
                "We turned the classic ice cream parlor sundae you've always loved into the at-home flavor creation you've always wanted. Enjoy!"))
            .body("allergyInfo", Matchers.equalTo("may contain other tree nuts"))
            .body("dietaryCertifications", Matchers.equalTo("Kosher"));
    }

    @Test
    void getByName_whenServiceThrowsBadRequestException_thenReturn400() {
        BadRequestException exception = new BadRequestException("Mocked message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "Banana Split")
            .when().get(GET_BY_NAME_URI)
            .then()
            .statusCode(400)
            .contentType("application/problem+json")
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked message"));
    }

    @Test
    void getByName_whenServiceThrowsNotFoundException_thenReturn404() {
        NotFoundException exception = new NotFoundException("Mocked message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "Banana Split")
            .when().get(GET_BY_NAME_URI)
            .then()
            .statusCode(404)
            .contentType("application/problem+json")
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked message"));
    }

    @Test
    void getAll_whenFound_thenReturn200() {
        final var response = List.of(
            ProductResponseDto.builder()
                .name("Banana Split")
                .imageClosed(
                    "/files/live/sites/systemsite/files/flavors/products/us/pint/open-closed-pints/banana-split-landing.png")
                .imageOpen(
                    "/files/live/sites/systemsite/files/flavors/products/us/pint/open-closed-pints/banana-split-landing-open.png")
                .description("Banana & Strawberry Ice Creams with Walnuts, Fudge Chunks & a Fudge Swirl")
                .story(
                    "We turned the classic ice cream parlor sundae you've always loved into the at-home flavor creation you've always wanted. Enjoy!")
                .allergyInfo("may contain other tree nuts")
                .dietaryCertifications("Kosher")
                .build()
        );
        Mockito.when(service.getAll()).thenReturn(response);

        RestAssured.given()
            .when().get(GET_ALL_URI)
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("[0].name", Matchers.equalTo("Banana Split"))
            .body("[0].imageClosed", Matchers.equalTo(
                "/files/live/sites/systemsite/files/flavors/products/us/pint/open-closed-pints/banana-split-landing.png"))
            .body("[0].imageOpen", Matchers.equalTo(
                "/files/live/sites/systemsite/files/flavors/products/us/pint/open-closed-pints/banana-split-landing-open.png"))
            .body("[0].description",
                Matchers.equalTo("Banana & Strawberry Ice Creams with Walnuts, Fudge Chunks & a Fudge Swirl"))
            .body("[0].story", Matchers.equalTo(
                "We turned the classic ice cream parlor sundae you've always loved into the at-home flavor creation you've always wanted. Enjoy!"))
            .body("[0].allergyInfo", Matchers.equalTo("may contain other tree nuts"))
            .body("[0].dietaryCertifications", Matchers.equalTo("Kosher"));
    }

    @Test
    void getAll_whenServiceThrowsNotFoundException_thenReturn400() {
        NotFoundException exception = new NotFoundException("Mocked message");
        Mockito.when(service.getAll()).thenThrow(exception);

        RestAssured.given()
            .when().get(GET_ALL_URI)
            .then()
            .statusCode(404)
            .contentType("application/problem+json")
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked message"));
    }

}
