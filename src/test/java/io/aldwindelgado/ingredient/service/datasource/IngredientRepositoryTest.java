package io.aldwindelgado.ingredient.service.datasource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
@QuarkusTest
class IngredientRepositoryTest {

    @Inject
    IngredientRepository repository;

    @Test
    void testGetAllMethod() {
        final var allIngredients = repository.getAll();

        assertEquals(128, allIngredients.size());
    }

    @Test
    void testGetByNameMethod() {
        final var existingIngredient = repository.getByName("cream");

        assertTrue(existingIngredient.isPresent());
    }

    @TestTransaction
    @Test
    void testSaveMethod() {
        var newIngredient = new Ingredient();
        newIngredient.setName("new ingredient");

        repository.save(newIngredient);

        final var newPersistedIngredient = repository.getByName("NEW ingredient");
        assertTrue(newPersistedIngredient.isPresent());
    }
}
