package io.aldwindelgado.product.service.datasource;

import com.jparams.verifier.tostring.ToStringVerifier;
import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValue;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
class ProductTest {

    @Test
    void testToStringMethod() {
        ToStringVerifier.forClass(Product.class)
            .withIgnoredFields("ingredients", "sourcingValues")
            .verify();
    }

    @Test
    void testEqualsAndHashCodeMethod() {
        var sourcingValue = new SourcingValue();
        sourcingValue.setId(1L);
        sourcingValue.setName("sourcing value");
        var sourcingValue2 = new SourcingValue();
        sourcingValue2.setId(2L);
        sourcingValue2.setName("sourcing value 2");

        var ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("ingredient");
        var ingredient2 = new Ingredient();
        ingredient2.setId(2L);
        ingredient2.setName("ingredient 2");

        EqualsVerifier.simple().forClass(Product.class)
            .suppress(Warning.SURROGATE_KEY)
            .withNonnullFields("id")
            .withPrefabValues(Ingredient.class, ingredient, ingredient2)
            .withPrefabValues(SourcingValue.class, sourcingValue, sourcingValue2)
            .verify();
    }
}
