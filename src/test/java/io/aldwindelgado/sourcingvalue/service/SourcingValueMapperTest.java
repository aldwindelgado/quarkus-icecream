package io.aldwindelgado.sourcingvalue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.aldwindelgado.ingredient.service.Ingredient;
import io.aldwindelgado.product.service.Product;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueRequestDto;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueResponseDto;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * @author Aldwin Delgado
 */
class SourcingValueMapperTest {

    private final SourcingValueMapper mapper = Mappers.getMapper(SourcingValueMapper.class);

    @DisplayName("Test toResponseDto mapping")
    @Nested
    class ToResponseDto {

        @Test
        void toResponse_whenProductHasValue_thenReturnResponse() {
            SourcingValue sourcingValue = new SourcingValue();
            sourcingValue.setName("test sourcing value");
            sourcingValue.setProducts(List.of(buildProductEntity()));

            final var actualResponse = mapper.toResponseDto(sourcingValue);

            final var expectedResponse = new SourcingValueResponseDto("test sourcing value", List.of("test product name"));
            assertEquals(expectedResponse, actualResponse);

        }

        @Test
        void toResponse_whenProductIsEmpty_thenReturnResponseWithEmptyProducts() {
            SourcingValue sourcingValue = new SourcingValue();
            sourcingValue.setName("test sourcing value");
            sourcingValue.setProducts(Collections.emptyList());

            final var actualResponse = mapper.toResponseDto(sourcingValue);

            final var expectedResponse = new SourcingValueResponseDto("test sourcing value", Collections.emptyList());
            assertEquals(expectedResponse, actualResponse);
        }

    }

    @DisplayName("Test toResponseDtos mapping")
    @Nested
    class ToResponseDtos {

        @Test
        void toResponseDtos_whenProductHasValue_thenReturnResponse() {
            SourcingValue sourcingValue = new SourcingValue();
            sourcingValue.setName("test sourcing value");
            sourcingValue.setProducts(List.of(buildProductEntity()));

            final var actualResponse = mapper.toResponseDtos(List.of(sourcingValue));

            List<SourcingValueResponseDto> expectedResponse = List.of(
                new SourcingValueResponseDto("test sourcing value", List.of("test product name")));
            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void toResponseDtos_whenProductIsEmpty_thenReturnResponseWithEmptyProduct() {
            SourcingValue sourcingValue = new SourcingValue();
            sourcingValue.setName("test sourcing value");
            sourcingValue.setProducts(Collections.emptyList());

            final var actualResponse = mapper.toResponseDtos(List.of(sourcingValue));

            List<SourcingValueResponseDto> expectedResponse = List.of(
                new SourcingValueResponseDto("test sourcing value", Collections.emptyList()));
            assertEquals(expectedResponse, actualResponse);
        }

    }

    @DisplayName("Test toEntity mapping")
    @Nested
    class ToEntity {

        @Test
        void toEntity_thenReturnEntity() {
            SourcingValueRequestDto request = new SourcingValueRequestDto();
            request.setName("new sourcing value");

            final var actualRequest = mapper.toEntity(request);

            final var expected = new SourcingValue();
            expected.setName("new sourcing value");
            expected.setProducts(Collections.emptyList());
            assertEquals(expected, actualRequest);
        }
    }

    private static Product buildProductEntity() {
        SourcingValue sourcingValue = new SourcingValue();
        sourcingValue.setName("sourcing value");

        Ingredient ingredient = new Ingredient();
        ingredient.setName("ingredient");

        Product product = new Product();
        product.setName("test product name");
        product.setImageClosed("/files/images/closed/image.jpg");
        product.setImageOpen("/files/images/open/image.jpg");
        product.setDescription("product description");
        product.setStory("product story");
        product.setAllergyInfo("product allergy info");
        product.setDietaryCertifications("product dietary certifications");
        product.setSourcingValues(List.of(sourcingValue));
        product.setIngredients(List.of(ingredient));
        return product;
    }
}
