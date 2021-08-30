package io.aldwindelgado.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.product.api.exchange.ProductRequestDto;
import io.aldwindelgado.product.api.exchange.ProductResponseDto;
import io.aldwindelgado.product.service.datasource.Product;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValue;
import io.quarkus.test.junit.QuarkusTest;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

/**
 * @author Aldwin Delgado
 */
@QuarkusTest
class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @DisplayName("Test toResponseDto mapping")
    @Nested
    class ToResponseDto {

        @Test
        void toResponseDto_thenReturnResponse() {
            var product = buildProductEntity();

            final var actualDto = mapper.toResponseDto(product);

            final var expectedDto = ProductResponseDto.builder()
                .name("test product name")
                .imageClosed("/files/images/closed/image.jpg")
                .imageOpen("/files/images/open/image.jpg")
                .description("product description")
                .story("product story")
                .allergyInfo("product allergy info")
                .dietaryCertifications("product dietary certifications")
                .ingredients(List.of("existing ingredient"))
                .sourcingValues(List.of("existing sourcing value"))
                .build();
            assertEquals(expectedDto, actualDto);
        }

        @Test
        void toResponseDto_whenIngredientsAreEmpty_thenReturnResponse() {
            var product = buildProductEntity();
            product.setIngredients(Collections.emptyList());

            final var actualDto = mapper.toResponseDto(product);

            final var expectedDto = ProductResponseDto.builder()
                .name("test product name")
                .imageClosed("/files/images/closed/image.jpg")
                .imageOpen("/files/images/open/image.jpg")
                .description("product description")
                .story("product story")
                .allergyInfo("product allergy info")
                .dietaryCertifications("product dietary certifications")
                .ingredients(Collections.emptyList())
                .sourcingValues(List.of("existing sourcing value"))
                .build();
            assertEquals(expectedDto, actualDto);
        }

        @Test
        void toResponseDto_whenSourcingValuesAreEmpty_thenReturnResponse() {
            var product = buildProductEntity();
            product.setSourcingValues(Collections.emptyList());

            final var actualDto = mapper.toResponseDto(product);

            final var expectedDto = ProductResponseDto.builder()
                .name("test product name")
                .imageClosed("/files/images/closed/image.jpg")
                .imageOpen("/files/images/open/image.jpg")
                .description("product description")
                .story("product story")
                .allergyInfo("product allergy info")
                .dietaryCertifications("product dietary certifications")
                .ingredients(List.of("existing ingredient"))
                .sourcingValues(Collections.emptyList())
                .build();
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
        void toResponseDto_thenReturnResponse() {
            var product = buildProductEntity();

            final var actualDto = mapper.toResponseDtos(List.of(product));

            final var expectedDto = List.of(ProductResponseDto.builder()
                .name("test product name")
                .imageClosed("/files/images/closed/image.jpg")
                .imageOpen("/files/images/open/image.jpg")
                .description("product description")
                .story("product story")
                .allergyInfo("product allergy info")
                .dietaryCertifications("product dietary certifications")
                .ingredients(List.of("existing ingredient"))
                .sourcingValues(List.of("existing sourcing value"))
                .build());
            assertEquals(expectedDto, actualDto);
        }

        @Test
        void toResponseDto_whenIngredientsAreEmpty_thenReturnResponse() {
            var product = buildProductEntity();
            product.setIngredients(Collections.emptyList());

            final var actualDto = mapper.toResponseDtos(List.of(product));

            final var expectedDto = List.of(ProductResponseDto.builder()
                .name("test product name")
                .imageClosed("/files/images/closed/image.jpg")
                .imageOpen("/files/images/open/image.jpg")
                .description("product description")
                .story("product story")
                .allergyInfo("product allergy info")
                .dietaryCertifications("product dietary certifications")
                .ingredients(Collections.emptyList())
                .sourcingValues(List.of("existing sourcing value"))
                .build());
            assertEquals(expectedDto, actualDto);
        }

        @Test
        void toResponseDto_whenSourcingValuesAreEmpty_thenReturnResponse() {
            var product = buildProductEntity();
            product.setSourcingValues(Collections.emptyList());

            final var actualDto = mapper.toResponseDtos(List.of(product));

            final var expectedDto = List.of(ProductResponseDto.builder()
                .name("test product name")
                .imageClosed("/files/images/closed/image.jpg")
                .imageOpen("/files/images/open/image.jpg")
                .description("product description")
                .story("product story")
                .allergyInfo("product allergy info")
                .dietaryCertifications("product dietary certifications")
                .ingredients(List.of("existing ingredient"))
                .sourcingValues(Collections.emptyList())
                .build());
            assertEquals(expectedDto, actualDto);
        }

    }

    @DisplayName("Test toEntity mapping")
    @Nested
    class ToEntity {

        @Disabled("Searching how to mock the EntityManager to tests")
        @Test
        void testToEntity_thenReturnEntity() {
//            QuarkusMock.installMockForInstance();
//            final var mockedIngredientFromDb = new Ingredient();
//            mockedIngredientFromDb.setName("existing ingredient");
//            mockedIngredientFromDb.setProducts(Collections.emptyList());
//
//            final var mockedSourcingValueFromDb = new SourcingValue();
//            mockedSourcingValueFromDb.setName("existing sourcing value");
//            mockedSourcingValueFromDb.setProducts(Collections.emptyList());
//            Mockito.when(entityManager.createNativeQuery(anyString(), Ingredient.class)
//                    .setParameter(anyString(), anyString())
//                    .getResultList())
//                .thenReturn(List.of(mockedIngredientFromDb));
//            Mockito.when(entityManager.createNativeQuery(anyString(), SourcingValue.class)
//                    .setParameter(anyString(), anyString())
//                    .getResultList())
//                .thenReturn(List.of(mockedSourcingValueFromDb));

            var request = new ProductRequestDto();
            request.setName("new product");
            request.setImageClosed("/files/images/closed/image.jpg");
            request.setImageOpen("/files/images/open/image.jpg");
            request.setDescription("new description");
            request.setStory("new story");
            request.setAllergyInfo("new allergy info");
            request.setDietaryCertifications("new dietary certification");
            request.setSourcingValues(List.of("existing sourcing value"));
            request.setIngredients(List.of("existing ingredients"));
            final var actualEntity = mapper.toEntity(request);

            var expectedSourcingValue = new SourcingValue();
            expectedSourcingValue.setName("existing sourcing value");
            expectedSourcingValue.setProducts(Collections.emptyList());

            var expectedIngredient = new Ingredient();
            expectedIngredient.setName("existing ingredients");
            expectedIngredient.setProducts(Collections.emptyList());

            var expectedEntity = new Product();
            expectedEntity.setName("new product");
            expectedEntity.setImageClosed("/files/images/closed/image.jpg");
            expectedEntity.setImageOpen("/files/images/open/image.jpg");
            expectedEntity.setDescription("new description");
            expectedEntity.setStory("new story");
            expectedEntity.setAllergyInfo("new allergy info");
            expectedEntity.setDietaryCertifications("new dietary certification");
            expectedEntity.setIngredients(List.of(expectedIngredient));
            expectedEntity.setSourcingValues(List.of(expectedSourcingValue));

            assertEquals(expectedEntity, actualEntity);
        }

        @Test
        void testToEntity_whenInputIsNull_thenReturnNull() {
            final var actualEntity = mapper.toEntity(null);

            assertNull(actualEntity);
        }
    }

    private static Product buildProductEntity() {
        var sourcingValue = new SourcingValue();
        sourcingValue.setName("existing sourcing value");

        var ingredient = new Ingredient();
        ingredient.setName("existing ingredient");

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
