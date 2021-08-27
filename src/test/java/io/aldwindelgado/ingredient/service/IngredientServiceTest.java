package io.aldwindelgado.ingredient.service;

import io.aldwindelgado.ingredient.api.exchange.IngredientRequestDto;
import io.aldwindelgado.ingredient.api.exchange.IngredientResponseDto;
import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.ingredient.service.datasource.IngredientRepository;
import io.aldwindelgado.product.service.datasource.Product;
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
class IngredientServiceTest {

    @Inject
    IngredientService service;

    @Inject
    IngredientRepository repository;

    public static class MockNotEmptyIngredientRepository extends IngredientRepository {

        @Override
        public List<Ingredient> getAll() {
            final var product = new Product();
            product.setName("existing product");
            product.setDescription("product description");
            product.setImageClosed("/files/images/closed/img.jpg");
            product.setImageOpen("/files/images/open/img.jpg");
            product.setAllergyInfo("product allergy info");
            product.setDietaryCertifications("product dietary certifications");

            final var ingredient = new Ingredient();
            ingredient.setName("existing ingredient");
            ingredient.setProducts(List.of(product));

            return Collections.singletonList(ingredient);
        }

        @Override
        public Optional<Ingredient> getByName(String name) {
            final var product = new Product();
            product.setName("existing product");
            product.setDescription("product description");
            product.setImageClosed("/files/images/closed/img.jpg");
            product.setImageOpen("/files/images/open/img.jpg");
            product.setAllergyInfo("product allergy info");
            product.setDietaryCertifications("product dietary certifications");

            final var ingredient = new Ingredient();
            ingredient.setName("existing ingredient");
            ingredient.setProducts(List.of(product));

            return Optional.of(ingredient);
        }
    }

    public static class MockErrorIngredientRepository extends IngredientRepository {

        @Override
        public List<Ingredient> getAll() {
            return Collections.emptyList();
        }

        @Override
        public Optional<Ingredient> getByName(String name) {
            return Optional.empty();
        }

        @Override
        public void save(Ingredient ingredient) {
            final var constraintViolateException = new ConstraintViolationException("Duplicate ingredient name",
                new SQLException("sql.errorcode.mocked"), "unq_ingredient_name");
            throw new PersistenceException("Duplicate ingredient name", constraintViolateException);
        }
    }

    @DisplayName("Test getAll methods")
    @Nested
    class GetAll {

        @Test
        void getAll_whenRecordIsFound_thenReturnResponse() {
            QuarkusMock.installMockForInstance(new MockNotEmptyIngredientRepository(), repository);
            final var actual = service.getAll();
            final var expected = List.of(
                new IngredientResponseDto("existing ingredient", List.of("existing product"))
            );

            Assertions.assertEquals(expected, actual);
        }

        @Test
        void getAll_whenNoRecordFound_thenThrowNotFoundException() {
            QuarkusMock.installMockForInstance(new MockErrorIngredientRepository(), repository);
            Assertions.assertThrows(
                NotFoundException.class,
                () -> service.getAll(),
                "No ingredient exists"
            );
        }
    }

    @DisplayName("Test getByName methods")
    @Nested
    class GetByName {

        @Test
        void getByName_whenRecordIsFound_thenReturnResponse() {
            QuarkusMock.installMockForInstance(new MockNotEmptyIngredientRepository(), repository);
            final var actual = service.getByName("existing ingredient");

            final var expected = new IngredientResponseDto("existing ingredient", List.of("existing product"));
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void getByName_whenNameIsNull_thenThrowBadRequestException() {
            Assertions.assertThrows(
                BadRequestException.class,
                () -> service.getByName(null),
                "Ingredient's name is required"
            );
        }

        @Test
        void getByName_whenNameIsNotFound_thenThrowNotFoundException() {
            QuarkusMock.installMockForInstance(new MockErrorIngredientRepository(), repository);
            Assertions.assertThrows(
                NotFoundException.class,
                () -> service.getByName("existing ingredient"),
                "Ingredient with name 'existing ingredient' does not exist"
            );
        }
    }

    @DisplayName("Test save method")
    @Nested
    class Save {

        @Test
        void save_whenUniqueConstraintIsTriggered_thenThrowBadRequestException() {
            QuarkusMock.installMockForInstance(new MockErrorIngredientRepository(), repository);
            final var request = new IngredientRequestDto();
            request.setName("new ingredient");

            Assertions.assertThrows(
                BadRequestException.class,
                () -> service.create(request),
                "Duplicate ingredient name"
            );
        }
    }
}
