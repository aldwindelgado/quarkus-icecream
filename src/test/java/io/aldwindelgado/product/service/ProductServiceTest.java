package io.aldwindelgado.product.service;

import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.product.api.exchange.ProductRequestDto;
import io.aldwindelgado.product.api.exchange.ProductResponseDto;
import io.aldwindelgado.product.service.datasource.Product;
import io.aldwindelgado.product.service.datasource.ProductRepository;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValue;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
@QuarkusTest
class ProductServiceTest {

    @Inject
    ProductService service;

    @Inject
    ProductRepository repository;

    public static class MockNotEmptyProductRepository extends ProductRepository {

        @Override
        public List<Product> getAll() {
            final var sourcingValue = new SourcingValue();
            sourcingValue.setName("existing sourcing value");

            final var ingredient = new Ingredient();
            ingredient.setName("existing ingredient");

            final var mockedProduct = new Product();
            mockedProduct.setName("existing product");
            mockedProduct.setDescription("product description");
            mockedProduct.setImageClosed("/files/images/closed/img.jpg");
            mockedProduct.setImageOpen("/files/images/open/img.jpg");
            mockedProduct.setAllergyInfo("product allergy info");
            mockedProduct.setDietaryCertifications("product dietary certifications");
            mockedProduct.setStory("product story");
            mockedProduct.addSourcingValues(Collections.singletonList(sourcingValue));
            mockedProduct.addIngredients(Collections.singletonList(ingredient));

            return Collections.singletonList(mockedProduct);
        }

        @Override
        public Optional<Product> getByName(String name) {
            final var sourcingValue = new SourcingValue();
            sourcingValue.setName("existing sourcing value");

            final var ingredient = new Ingredient();
            ingredient.setName("existing ingredient");

            final var mockedProduct = new Product();
            mockedProduct.setName("existing product");
            mockedProduct.setDescription("product description");
            mockedProduct.setImageClosed("/files/images/closed/img.jpg");
            mockedProduct.setImageOpen("/files/images/open/img.jpg");
            mockedProduct.setAllergyInfo("product allergy info");
            mockedProduct.setDietaryCertifications("product dietary certifications");
            mockedProduct.setStory("product story");
            mockedProduct.addSourcingValues(Collections.singletonList(sourcingValue));
            mockedProduct.addIngredients(Collections.singletonList(ingredient));

            return Optional.of(mockedProduct);
        }
    }

    public static class MockErrorProductRepository extends ProductRepository {

        @Override
        public List<Product> getAll() {
            return Collections.emptyList();
        }

        @Override
        public Optional<Product> getByName(String name) {
            return Optional.empty();
        }

        @Override
        public void save(Product entity) {
            final var constraintViolateException = new ConstraintViolationException("Duplicate product name",
                new SQLException("sql.errorcode.mocked"), "unq_product_name");
            throw new PersistenceException("Duplicate product name", constraintViolateException);
        }
    }

    @DisplayName("Test getAll methods")
    @Nested
    class GetAll {

        @Test
        void getAll_whenRecordIsFound_thenReturnResponse() {
            QuarkusMock.installMockForInstance(new MockNotEmptyProductRepository(), repository);

            final var actual = service.getAll();

            final var expected = List.of(
                ProductResponseDto.builder()
                    .name("existing product")
                    .description("product description")
                    .imageClosed("/files/images/closed/img.jpg")
                    .imageOpen("/files/images/open/img.jpg")
                    .allergyInfo("product allergy info")
                    .dietaryCertifications("product dietary certifications")
                    .story("product story")
                    .ingredients(Collections.singletonList("existing ingredient"))
                    .sourcingValues(Collections.singletonList("existing sourcing value"))
                    .build()
            );

            Assertions.assertEquals(expected, actual);
        }

        @Test
        void getAll_whenNoRecordFound_thenThrowNotFoundException() {
            QuarkusMock.installMockForInstance(new MockErrorProductRepository(), repository);
            Assertions.assertThrows(
                NotFoundException.class,
                () -> service.getAll(),
                "No product exists"
            );
        }
    }

    @DisplayName("Test getByName methods")
    @Nested
    class GetByName {

        @Test
        void getByName_whenRecordIsFound_thenReturnResponse() {
            QuarkusMock.installMockForInstance(new MockNotEmptyProductRepository(), repository);
            final var actual = service.getByName("existing product");

            final var expected = ProductResponseDto.builder()
                .name("existing product")
                .description("product description")
                .imageClosed("/files/images/closed/img.jpg")
                .imageOpen("/files/images/open/img.jpg")
                .allergyInfo("product allergy info")
                .dietaryCertifications("product dietary certifications")
                .story("product story")
                .ingredients(Collections.singletonList("existing ingredient"))
                .sourcingValues(Collections.singletonList("existing sourcing value"))
                .build();

            Assertions.assertEquals(expected, actual);
        }

        @Test
        void getByName_whenNameIsNull_thenThrowBadRequestException() {
            Assertions.assertThrows(
                BadRequestException.class,
                () -> service.getByName(null),
                "Product's name is required"
            );
        }

        @Test
        void getByName_whenNameIsNotFound_thenThrowNotFoundException() {
            QuarkusMock.installMockForInstance(new MockErrorProductRepository(), repository);
            Assertions.assertThrows(
                NotFoundException.class,
                () -> service.getByName("existing product"),
                "Product with name 'existing product' does not exist"
            );
        }
    }

    @DisplayName("Test save method")
    @Nested
    class Save {

        @Test
        void save_whenUniqueConstraintIsTriggered_thenThrowBadException() {
            QuarkusMock.installMockForInstance(new MockErrorProductRepository(), repository);

            final var request = new ProductRequestDto();
            request.setName("new product");
            request.setDescription("new description");
            request.setImageClosed("/files/images/closed/img1.jpg");
            request.setImageOpen("/files/images/open/img1.jpg");
            request.setAllergyInfo("new allergy info");
            request.setDietaryCertifications("new dietary certifications");
            request.setStory("new story");
            request.setIngredients(List.of("existing ingredient 1", "existing ingredient 2"));
            request.setSourcingValues(List.of("existing sourcing value 1", "existing sourcing value 2"));

            Assertions.assertThrows(
                BadRequestException.class,
                () -> service.create(request),
                "Duplicate product name"
            );
        }
    }
}
