package io.aldwindelgado.sourcingvalue.api.exchange;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
class SourcingValueResponseDtoTest {

    @Test
    void testToStringMethod() {
        ToStringVerifier
            .forClass(SourcingValueResponseDto.class)
            .verify();
    }

    @Test
    void testEqualsAndHashCodeMethod() {
        EqualsVerifier
            .forClass(SourcingValueResponseDto.class)
            .withNonnullFields("name")
            .verify();
    }
}
