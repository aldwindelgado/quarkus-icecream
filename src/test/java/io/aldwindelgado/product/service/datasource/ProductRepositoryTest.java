package io.aldwindelgado.product.service.datasource;

import static org.junit.jupiter.api.Assertions.*;

import io.aldwindelgado.ingredient.service.datasource.IngredientRepository;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValueRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import java.util.List;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
@QuarkusTest
class ProductRepositoryTest {

    private static final int PRODUCT_COUNT_FROM_DB = 56;

    @Inject
    ProductRepository repository;

    @Inject
    IngredientRepository ingredientRepository;

    @Inject
    SourcingValueRepository sourcingValueRepository;

    @Test
    void testGetAllQuery() {
        final var allProducts = repository.getAll();

        assertEquals(PRODUCT_COUNT_FROM_DB, allProducts.size());
    }

    @Test
    void testGetByNameQuery() {
        final var existingProduct = repository.getByName("banana SPLIT");

        assertTrue(existingProduct.isPresent());
    }

    @TestTransaction
    @Test
    void testSaveQuery() {
        final var existingIngredient = ingredientRepository.getByName("cream");
        final var existingSourcingValue = sourcingValueRepository.getByName("fairtrade");

        var newProduct = new Product();
        newProduct.setName("new product");
        newProduct.setDescription("new product description");
        newProduct.setImageClosed("/files/images/cl-img.jpg");
        newProduct.setImageOpen("/files/images/op-img.jpg");
        newProduct.setStory("new product story");
        newProduct.setAllergyInfo("new product allergy info");
        newProduct.setDietaryCertifications("new product dietary certifications");
        newProduct.setIngredients(List.of(existingIngredient.get()));
        newProduct.setSourcingValues(List.of(existingSourcingValue.get()));

        repository.save(newProduct);

        final var newPersistedProduct = repository.getByName("new PRODUCT");
        assertTrue(newPersistedProduct.isPresent());
    }
}
