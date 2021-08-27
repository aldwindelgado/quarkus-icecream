package io.aldwindelgado.ingredient.service.datasource;

import com.jparams.verifier.tostring.ToStringVerifier;
import io.aldwindelgado.product.service.datasource.Product;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
class IngredientTest {

    @Test
    void testToStringMethod() {
        ToStringVerifier.forClass(Ingredient.class)
            .withPrefabValue(Product.class, new Product())
            .withIgnoredFields("products")
            .verify();
    }

    @Test
    void testEqualsAndHashCodeMethod() {
        EqualsVerifier.forClass(Ingredient.class)
            .withIgnoredFields("products")
            .verify();
    }
}
