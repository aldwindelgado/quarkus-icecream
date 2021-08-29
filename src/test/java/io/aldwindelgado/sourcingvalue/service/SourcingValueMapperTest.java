package io.aldwindelgado.sourcingvalue.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.product.service.datasource.Product;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueRequestDto;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueResponseDto;
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
class SourcingValueMapperTest {

    private final SourcingValueMapper mapper = Mappers.getMapper(SourcingValueMapper.class);

    @DisplayName("Test toResponseDto mapping")
    @Nested
    class ToResponseDto {

        @Test
        void toResponseDto_whenProductHasValue_thenReturnResponse() {
            var sourcingValue = new SourcingValue();
            sourcingValue.setName("test sourcing value");
            sourcingValue.setProducts(List.of(buildProductEntity()));

            final var actualDto = mapper.toResponseDto(sourcingValue);

            final var expectedDto = new SourcingValueResponseDto("test sourcing value", List.of("test product name"));
            assertEquals(expectedDto, actualDto);

        }

        @Test
        void toResponseDto_whenProductIsEmpty_thenReturnResponseWithEmptyProducts() {
            var sourcingValue = new SourcingValue();
            sourcingValue.setName("test sourcing value");
            sourcingValue.setProducts(Collections.emptyList());

            final var actualDto = mapper.toResponseDto(sourcingValue);

            final var expectedDto = new SourcingValueResponseDto("test sourcing value", Collections.emptyList());
            assertEquals(expectedDto, actualDto);
        }

        @Test
        void toResponseDto_whenInputIsNull_thenReturnNull() {
            final var actualDto = mapper.toResponseDto(null);

            assertNull(actualDto);
        }
    }

    @DisplayName("Test toResponseDtos mapping")
    @Nested
    class ToResponseDtos {

        @Test
        void toResponseDtos_whenProductHasValue_thenReturnResponse() {
            var sourcingValue = new SourcingValue();
            sourcingValue.setName("test sourcing value");
            sourcingValue.setProducts(List.of(buildProductEntity()));

            final var actualDto = mapper.toResponseDtos(List.of(sourcingValue));

            final var expectedDto = List.of(
                new SourcingValueResponseDto("test sourcing value", List.of("test product name")));
            assertEquals(expectedDto, actualDto);
        }

        @Test
        void toResponseDtos_whenProductIsEmpty_thenReturnResponseWithEmptyProduct() {
            var sourcingValue = new SourcingValue();
            sourcingValue.setName("test sourcing value");
            sourcingValue.setProducts(Collections.emptyList());

            final var actualDto = mapper.toResponseDtos(List.of(sourcingValue));

            final var expectedDto = List.of(
                new SourcingValueResponseDto("test sourcing value", Collections.emptyList()));
            assertEquals(expectedDto, actualDto);
        }

    }

    @DisplayName("Test toEntity mapping")
    @Nested
    class ToEntity {

        @Test
        void toEntity_thenReturnEntity() {
            var request = new SourcingValueRequestDto();
            request.setName("new sourcing value");

            final var actualEntity = mapper.toEntity(request);

            var expectedEntity = new SourcingValue();
            expectedEntity.setName("new sourcing value");
            expectedEntity.setProducts(Collections.emptyList());
            assertEquals(expectedEntity, actualEntity);
        }

        @Test
        void toEntity_whenInputIsNull_thenReturnNull() {
            final var actualEntity = mapper.toEntity(null);

            assertNull(actualEntity);
        }
    }

    private static Product buildProductEntity() {
        var sourcingValue = new SourcingValue();
        sourcingValue.setName("sourcing value");

        var ingredient = new Ingredient();
        ingredient.setName("ingredient");

        var product = new Product();
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
