package io.aldwindelgado.product.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.aldwindelgado.product.api.exchange.ProductRequestDto;
import io.aldwindelgado.product.api.exchange.ProductResponseDto;
import io.aldwindelgado.product.service.ProductService;
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
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author Aldwin Delgado
 */
@QuarkusTest
@TestHTTPEndpoint(ProductResource.class)
class ProductResourceTest {

    private static final String PROBLEM_JSON_CONTENT_TYPE = "application/problem+json";
    private static final String GET_BY_NAME_PATH = "{name}";

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
            .when().get(GET_BY_NAME_PATH)
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
        BadRequestException exception = new BadRequestException("Mocked exception message");
        Mockito.when(service.getByName(anyString())).thenThrow(exception);

        RestAssured.given()
            .pathParam("name", "Banana Split")
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
            .pathParam("name", "Banana Split")
            .when().get(GET_BY_NAME_PATH)
            .then()
            .statusCode(404)
            .contentType(PROBLEM_JSON_CONTENT_TYPE)
            .body("$", Matchers.hasKey("timestamp"))
            .body("message", Matchers.equalTo("Mocked exception message"));
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
            .when().get()
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
    void create_whenSuccess_thenReturn201() throws JsonProcessingException {
        final var mapper = new ObjectMapper();
        final var sourcingValues = mapper.createArrayNode();
        sourcingValues.add("fairtrade");
        sourcingValues.add("non-gmo");
        final var ingredients = mapper.createArrayNode();
        ingredients.add("milk");
        ingredients.add("cream");
        final var rawJson = mapper.createObjectNode();
        rawJson.put("name", "new product");
        rawJson.put("imageClosed", "/files/test/cl-img.jpg");
        rawJson.put("imageOpen", "/files/test/op-img.jpg");
        rawJson.put("description", "description of new product");
        rawJson.put("story", "story of new product");
        rawJson.put("allergyInfo", "can contain some nuts");
        rawJson.set("ingredients", ingredients);
        rawJson.set("sourcingValues", sourcingValues);
        final var request = mapper.writeValueAsString(rawJson);

        final var response = RestAssured.given()
            .contentType(ContentType.JSON)
            .body(request)
            .when().post()
            .then()
            .statusCode(201)
            .header("Location", Matchers.endsWith("/products/new%20product"))
            .extract()
            .asString();

        assertEquals("", response);
    }

    @Test
    void create_whenServiceThrowsBadRequestException_thenReturn400() throws JsonProcessingException {
        BadRequestException exception = new BadRequestException("Mocked exception message");
        Mockito.doThrow(exception).when(service).create(any(ProductRequestDto.class));

        final var mapper = new ObjectMapper();
        final var sourcingValues = mapper.createArrayNode();
        sourcingValues.add("fairtrade");
        sourcingValues.add("non-gmo");
        final var ingredients = mapper.createArrayNode();
        ingredients.add("milk");
        ingredients.add("cream");
        final var rawJson = mapper.createObjectNode();
        rawJson.put("name", "new product");
        rawJson.put("imageClosed", "/files/test/cl-img.jpg");
        rawJson.put("imageOpen", "/files/test/op-img.jpg");
        rawJson.put("description", "description of new product");
        rawJson.put("story", "story of new product");
        rawJson.put("allergyInfo", "can contain some nuts");
        rawJson.set("ingredients", ingredients);
        rawJson.set("sourcingValues", sourcingValues);
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
    void create_whenServiceThrowsNotHandledException_thenReturn400() throws JsonProcessingException {
        NotAcceptableException exception = new NotAcceptableException("Mocked exception message");
        Mockito.doThrow(exception).when(service).create(any(ProductRequestDto.class));

        final var mapper = new ObjectMapper();
        final var sourcingValues = mapper.createArrayNode();
        sourcingValues.add("fairtrade");
        sourcingValues.add("non-gmo");
        final var ingredients = mapper.createArrayNode();
        ingredients.add("milk");
        ingredients.add("cream");
        final var rawJson = mapper.createObjectNode();
        rawJson.put("name", "new product");
        rawJson.put("imageClosed", "/files/test/cl-img.jpg");
        rawJson.put("imageOpen", "/files/test/op-img.jpg");
        rawJson.put("description", "description of new product");
        rawJson.put("story", "story of new product");
        rawJson.put("allergyInfo", "can contain some nuts");
        rawJson.set("ingredients", ingredients);
        rawJson.set("sourcingValues", sourcingValues);
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
