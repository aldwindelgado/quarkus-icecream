package io.aldwindelgado.product.api.exchange;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
class ProductResponseDtoTest {

    @Test
    void testToStringMethod() {
        ToStringVerifier
            .forClass(ProductResponseDto.class)
            .verify();
    }

    @Test
    void testEqualsAndHashCodeMethod() {
        EqualsVerifier
            .forClass(ProductResponseDto.class)
            .withNonnullFields("name")
            .withIgnoredFields("ingredients", "sourcingValues")
            .verify();
    }
}
