package io.aldwindelgado.sourcingvalue.api.exchange;

import com.jparams.verifier.tostring.ToStringVerifier;
import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
class SourcingValueRequestDtoTest {

    @Test
    void testToStringMethod() {
        ToStringVerifier
            .forClass(SourcingValueRequestDto.class)
            .verify();
    }

    @Test
    void testEqualsAndHashCodeMethod() {
        EqualsVerifier
            .simple()
            .forClass(SourcingValueRequestDto.class)
            .verify();
    }
}
