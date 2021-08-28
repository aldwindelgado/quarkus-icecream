package io.aldwindelgado.sourcingvalue.service.datasource;

import com.jparams.verifier.tostring.ToStringVerifier;
import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.product.service.datasource.Product;
import java.util.List;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
class SourcingValueTest {

    @Test
    void testToStringMethod() {
        ToStringVerifier.forClass(SourcingValue.class)
            .withIgnoredFields("products")
            .verify();
    }

    @Test
    void testEqualsAndHashCodeMethod() {
        var sourcingValue = new SourcingValue();
        sourcingValue.setId(1L);
        sourcingValue.setName("sourcing value");

        var ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("ingredient");

        var product = new Product();
        product.setId(1L);
        product.setName("test product name");
        product.setImageClosed("/files/images/closed/image.jpg");
        product.setImageOpen("/files/images/open/image.jpg");
        product.setDescription("product description");
        product.setStory("product story");
        product.setAllergyInfo("product allergy info");
        product.setDietaryCertifications("product dietary certifications");
        product.setSourcingValues(List.of(sourcingValue));
        product.setIngredients(List.of(ingredient));

        var product2 = new Product();
        product2.setId(2L);
        product2.setName("test product name 2");
        product2.setImageClosed("/files/images/closed/image2.jpg");
        product2.setImageOpen("/files/images/open/image2.jpg");
        product2.setDescription("product description 2");
        product2.setStory("product story 2");
        product2.setAllergyInfo("product allergy info 2");
        product2.setDietaryCertifications("product dietary certifications 2");
        product2.setSourcingValues(List.of(sourcingValue));
        product2.setIngredients(List.of(ingredient));

        EqualsVerifier.simple().forClass(SourcingValue.class)
            .suppress(Warning.SURROGATE_KEY)
            .withNonnullFields("id")
            .withPrefabValues(Product.class, product, product2)
            .verify();
    }
}
