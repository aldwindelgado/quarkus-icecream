package io.aldwindelgado.product.api;

import static org.junit.jupiter.api.Assertions.*;

import io.aldwindelgado.product.service.ProductService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

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
            .build();

        RestAssured.given()
            .pathParam("name", )
    }
}
