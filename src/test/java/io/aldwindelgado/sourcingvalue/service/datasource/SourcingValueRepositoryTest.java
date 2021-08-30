package io.aldwindelgado.sourcingvalue.service.datasource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
@QuarkusTest
class SourcingValueRepositoryTest {

    private static final int SOURCING_VALUE_COUNT_FROM_DB = 6;

    @Inject
    SourcingValueRepository repository;

    @Test
    void testGetAllQuery() {
        final var allSourcingValues = repository.getAll();

        assertEquals(SOURCING_VALUE_COUNT_FROM_DB, allSourcingValues.size());
    }

    @Test
    void testGetByNameQuery() {
        final var existingSourcingValue = repository.getByName("NON-gmo");

        assertTrue(existingSourcingValue.isPresent());
    }

    @TestTransaction
    @Test
    void testSaveQuery() {
        var newSourcingValue = new SourcingValue();
        newSourcingValue.setName("new sourcing value");

        repository.save(newSourcingValue);

        final var newPersistedSourcingValue = repository.getByName("new SOURCING value");
        assertTrue(newPersistedSourcingValue.isPresent());
    }
}
