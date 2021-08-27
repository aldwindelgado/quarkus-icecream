package io.aldwindelgado.product.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.product.api.exchange.ProductRequestDto;
import io.aldwindelgado.product.api.exchange.ProductResponseDto;
import io.aldwindelgado.product.service.datasource.Product;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValue;
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
class ProductMapperTest {

    private final ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @DisplayName("Test toResponseDto mapping")
    @Nested
    class ToResponseDto {

        @Test
        void toResponseDto_thenReturnResponse() {
            Product product = buildProductEntity();

            final var actualResponse = mapper.toResponseDto(product);

            final var expectedResponse = ProductResponseDto.builder()
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
            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void toResponseDto_whenIngredientsAreEmpty_thenReturnResponse() {
            Product product = buildProductEntity();
            product.setIngredients(Collections.emptyList());

            final var actualResponse = mapper.toResponseDto(product);

            final var expectedResponse = ProductResponseDto.builder()
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
            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void toResponseDto_whenSourcingValuesAreEmpty_thenReturnResponse() {
            Product product = buildProductEntity();
            product.setSourcingValues(Collections.emptyList());

            final var actualResponse = mapper.toResponseDto(product);

            final var expectedResponse = ProductResponseDto.builder()
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
            assertEquals(expectedResponse, actualResponse);
        }

    }

    @DisplayName("Test toResponseDtos mapping")
    @Nested
    class ToResponseDtos {

        @Test
        void toResponseDto_thenReturnResponse() {
            Product product = buildProductEntity();

            final var actualResponse = mapper.toResponseDtos(List.of(product));

            final var expectedResponse = List.of(ProductResponseDto.builder()
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
            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void toResponseDto_whenIngredientsAreEmpty_thenReturnResponse() {
            Product product = buildProductEntity();
            product.setIngredients(Collections.emptyList());

            final var actualResponse = mapper.toResponseDtos(List.of(product));

            final var expectedResponse = List.of(ProductResponseDto.builder()
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
            assertEquals(expectedResponse, actualResponse);
        }

        @Test
        void toResponseDto_whenSourcingValuesAreEmpty_thenReturnResponse() {
            Product product = buildProductEntity();
            product.setSourcingValues(Collections.emptyList());

            final var actualResponse = mapper.toResponseDtos(List.of(product));

            final var expectedResponse = List.of(ProductResponseDto.builder()
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
            assertEquals(expectedResponse, actualResponse);
        }

    }

    @Disabled("Mock test entity manager")
    @DisplayName("Test toEntity mapping")
    @Nested
    class ToEntity {

        @Test
        void testToEntity_thenReturn() {
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

            ProductRequestDto request = new ProductRequestDto();
            request.setName("new product");
            request.setImageClosed("/files/images/closed/image.jpg");
            request.setImageOpen("/files/images/open/image.jpg");
            request.setDescription("new description");
            request.setStory("new story");
            request.setAllergyInfo("new allergy info");
            request.setDietaryCertifications("new dietary certification");
            request.setSourcingValues(List.of("existing sourcing value"));
            request.setIngredients(List.of("existing ingredients"));
            final var actualProduct = mapper.toEntity(request);

            final var expectedSourcingValue = new SourcingValue();
            expectedSourcingValue.setName("existing sourcing value");
            expectedSourcingValue.setProducts(Collections.emptyList());

            final var expectedIngredient = new Ingredient();
            expectedIngredient.setName("existing ingredients");
            expectedIngredient.setProducts(Collections.emptyList());

            final var expected = new Product();
            expected.setName("new product");
            expected.setImageClosed("/files/images/closed/image.jpg");
            expected.setImageOpen("/files/images/open/image.jpg");
            expected.setDescription("new description");
            expected.setStory("new story");
            expected.setAllergyInfo("new allergy info");
            expected.setDietaryCertifications("new dietary certification");
            expected.setIngredients(List.of(expectedIngredient));
            expected.setSourcingValues(List.of(expectedSourcingValue));

            assertEquals(expected, actualProduct);
        }

    }

    private static Product buildProductEntity() {
        SourcingValue sourcingValue = new SourcingValue();
        sourcingValue.setName("existing sourcing value");

        Ingredient ingredient = new Ingredient();
        ingredient.setName("existing ingredient");

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
