package io.aldwindelgado.sourcingvalue.service;

import io.aldwindelgado.product.service.datasource.Product;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueRequestDto;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueResponseDto;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValue;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValueRepository;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.persistence.PersistenceException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * @author Aldwin Delgado
 */
@QuarkusTest
class SourcingValueServiceTest {

    @Inject
    SourcingValueService service;

    @Inject
    SourcingValueRepository repository;

    public static class MockNotEmptySourcingValueRepository extends SourcingValueRepository {

        @Override
        public List<SourcingValue> getAll() {
            final var product = new Product();
            product.setName("existing product");
            product.setDescription("product description");
            product.setImageClosed("/files/images/closed/img.jpg");
            product.setImageOpen("/files/images/open/img.jpg");
            product.setAllergyInfo("product allergy info");
            product.setDietaryCertifications("product dietary certifications");

            final var mockedEntity = new SourcingValue();
            mockedEntity.setName("existing sourcing value");
            mockedEntity.setProducts(List.of(product));

            return Collections.singletonList(mockedEntity);
        }

        @Override
        public Optional<SourcingValue> getByName(String name) {
            final var product = new Product();
            product.setName("existing product");
            product.setDescription("product description");
            product.setImageClosed("/files/images/closed/img.jpg");
            product.setImageOpen("/files/images/open/img.jpg");
            product.setAllergyInfo("product allergy info");
            product.setDietaryCertifications("product dietary certifications");

            final var mockedEntity = new SourcingValue();
            mockedEntity.setName("existing sourcing value");
            mockedEntity.setProducts(List.of(product));
            return Optional.of(mockedEntity);
        }
    }

    public static class MockErrorSourcingValueRepository extends SourcingValueRepository {

        @Override
        public List<SourcingValue> getAll() {
            return Collections.emptyList();
        }

        @Override
        public Optional<SourcingValue> getByName(String name) {
            return Optional.empty();
        }

        @Override
        public void save(SourcingValue entity) {
            final var constraintViolateException = new ConstraintViolationException("Duplicate sourcing value name",
                new SQLException("sql.errorcode.mocked"), "unq_sourcingvalue_name");
            throw new PersistenceException("Duplicate sourcing value name", constraintViolateException);
        }
    }

    @DisplayName("Test getAll methods")
    @Nested
    class GetAll {

        @Test
        void getAll_whenRecordIsFound_thenReturnResponse() {
            QuarkusMock.installMockForInstance(new MockNotEmptySourcingValueRepository(), repository);

            final var actual = service.getAll();

            final var expected = List.of(
                new SourcingValueResponseDto("existing sourcing value", List.of("existing product"))
            );
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void getAll_whenNoRecordFound_thenThrowNotFoundException() {
            QuarkusMock.installMockForInstance(new MockErrorSourcingValueRepository(), repository);
            Assertions.assertThrows(
                NotFoundException.class,
                () -> service.getAll(),
                "No sourcing value exists"
            );
        }
    }

    @DisplayName("Test getByName methods")
    @Nested
    class GetByName {

        @Test
        void getByName_whenRecordIsFound_thenReturnResponse() {
            QuarkusMock.installMockForInstance(new MockNotEmptySourcingValueRepository(), repository);
            final var actual = service.getByName("existing sourcing value");

            final var expected = new SourcingValueResponseDto("existing sourcing value", List.of("existing product"));
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void getByName_whenNameIsNull_thenThrowBadRequestException() {
            Assertions.assertThrows(
                BadRequestException.class,
                () -> service.getByName(null),
                "Sourcing value's name is required"
            );
        }

        @Test
        void getByName_whenNameIsNotFound_thenThrowNotFoundException() {
            QuarkusMock.installMockForInstance(new MockErrorSourcingValueRepository(), repository);
            Assertions.assertThrows(
                NotFoundException.class,
                () -> service.getByName("existing sourcing value"),
                "Sourcing value with name 'existing sourcing value' does not exist"
            );
        }
    }

    @DisplayName("Test save method")
    @Nested
    class Save {

        @Test
        void save_whenUniqueConstraintIsTriggered_thenThrowBadRequestException() {
            QuarkusMock.installMockForInstance(new MockErrorSourcingValueRepository(), repository);

            final var request = new SourcingValueRequestDto();
            request.setName("new sourcing value");

            Assertions.assertThrows(
                BadRequestException.class,
                () -> service.create(request),
                "Duplicate sourcing value name"
            );
        }
    }
}
