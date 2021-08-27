package io.aldwindelgado.ingredient.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.aldwindelgado.ingredient.api.exchange.IngredientRequestDto;
import io.aldwindelgado.ingredient.api.exchange.IngredientResponseDto;
import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.product.service.datasource.Product;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValue;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * @author Aldwin Delgado
 */
class IngredientMapperTest {

    private final IngredientMapper mapper = Mappers.getMapper(IngredientMapper.class);

    @DisplayName("Test toResponseDto mapping")
    @Nested
    class ToResponseDto {

        @Test
        void toResponseDto_whenProductHasValue_thenReturnResponse() {
            Ingredient ingredient = new Ingredient();
            ingredient.setName("test ingredient");
            ingredient.setProducts(List.of(buildProductEntity()));

            final var actualResponse = mapper.toResponseDto(ingredient);

            final var expectedResponse = new IngredientResponseDto("test ingredient", List.of("test product name"));
            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void toResponseDto_whenProductIsEmpty_thenReturnResponseWithEmptyProduct() {
            Ingredient ingredient = new Ingredient();
            ingredient.setName("test ingredient");
            ingredient.setProducts(Collections.emptyList());

            final var actualResponse = mapper.toResponseDto(ingredient);

            final var expectedResponse = new IngredientResponseDto("test ingredient", Collections.emptyList());
            assertEquals(expectedResponse, actualResponse);
        }

    }

    @DisplayName("Test toResponseDtos mapping")
    @Nested
    class ToResponseDtos {

        @Test
        void toResponseDtos_whenProductHasValue_thenReturnResponse() {
            Ingredient ingredient = new Ingredient();
            ingredient.setName("test ingredient");
            ingredient.setProducts(List.of(buildProductEntity()));

            final var actualResponse = mapper.toResponseDtos(List.of(ingredient));

            final var expectedResponse = List.of(
                new IngredientResponseDto("test ingredient", List.of("test product name")));
            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void toResponseDtos_whenProductIsEmpty_thenReturnResponseWithEmptyProduct() {
            Ingredient ingredient = new Ingredient();
            ingredient.setName("test ingredient");
            ingredient.setProducts(Collections.emptyList());

            final var actualResponse = mapper.toResponseDtos(List.of(ingredient));

            final var expectedResponse = List.of(new IngredientResponseDto("test ingredient", Collections.emptyList()));
            assertEquals(expectedResponse, actualResponse);
        }
    }

    @DisplayName("Test toEntity mapping")
    @Nested
    class ToEntity {

        @Test
        void toEntity_thenReturnEntity() {
            IngredientRequestDto request = new IngredientRequestDto();
            request.setName("test ingredient");

            final var actualRequest = mapper.toEntity(request);

            final var expected = new Ingredient();
            expected.setName("new ingredient");
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
