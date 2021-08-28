package io.aldwindelgado.ingredient.api.exchange;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
class IngredientResponseDtoTest {

    @Test
    void testToStringMethod() {
        ToStringVerifier
            .forClass(IngredientResponseDto.class)
            .verify();
    }

    @Test
    void testEqualsAndHashCodeMethod() {
        EqualsVerifier
            .forClass(IngredientResponseDto.class)
            .withNonnullFields("name")
            .verify();
    }
}
