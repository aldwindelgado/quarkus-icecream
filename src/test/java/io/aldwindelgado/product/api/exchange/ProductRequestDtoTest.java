package io.aldwindelgado.product.api.exchange;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
class ProductRequestDtoTest {

    @Test
    void testToStringMethod() {
        ToStringVerifier
            .forClass(ProductRequestDto.class)
            .verify();
    }

    @Test
    void testEqualsAndHashCodeMethod() {
        EqualsVerifier
            .simple()
            .forClass(ProductRequestDto.class)
            .withIgnoredFields("ingredients", "sourcingValues")
            .verify();
    }
}
