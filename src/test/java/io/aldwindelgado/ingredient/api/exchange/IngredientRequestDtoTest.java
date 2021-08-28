package io.aldwindelgado.ingredient.api.exchange;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
class IngredientRequestDtoTest {

    @Test
    void testToStringMethod() {
        ToStringVerifier
            .forClass(IngredientRequestDto.class)
            .verify();
    }

    @Test
    void testEqualsAndHashCodeMethod() {
        EqualsVerifier
            .simple()
            .forClass(IngredientRequestDto.class)
            .verify();
    }
}
