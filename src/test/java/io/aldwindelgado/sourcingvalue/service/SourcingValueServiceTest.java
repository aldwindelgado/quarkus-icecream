package io.aldwindelgado.sourcingvalue.service;

import io.aldwindelgado.product.service.Product;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueResponseDto;
import io.aldwindelgado.sourcingvalue.service.data.SourcingValue;
import io.aldwindelgado.sourcingvalue.service.data.SourcingValueRepository;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

/**
 * @author Aldwin Delgado
 */
@QuarkusTest
class SourcingValueServiceTest {

    @Inject
    SourcingValueService service;

    @InjectMock
    SourcingValueRepository repository;

    private static SourcingValue mockedEntity() {
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

        return mockedEntity;
    }

    @BeforeAll
    static void setup () {
        SourcingValueRepository mock = Mockito.mock(SourcingValueRepository.class);
        Mockito.when(mock.getByName(ArgumentMatchers.anyString())).thenReturn(Optional.of(mockedEntity()));
        Mockito.when(mock.getAll()).thenReturn(List.of(mockedEntity()));
        Mockito.doNothing().when(mock).save(ArgumentMatchers.any(SourcingValue.class));
    }

    @DisplayName("Test getAll methods")
    @Nested
    class GetAll {

        @Test
        void getAll_whenRecordIsFound_thenReturnResponse() {
            final var actual = service.getAll();

            final var expectedResponseDto = new SourcingValueResponseDto("existing sourcing value",
                List.of("existing product"));
            final var expected = List.of(expectedResponseDto);
            Assertions.assertEquals(expected, actual);
        }

        @Test
        void getAll_whenNoRecordFound_thenThrowNotFoundException() {
//            QuarkusMock.installMockForInstance(new MockEmptySourcingValueRepository(), repository);
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
            final var actual = service.getByName("non-gmo");

            final var expected = new SourcingValueResponseDto("non-gmo", List.of("existing product"));
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
            Assertions.assertThrows(
                NotFoundException.class,
                () -> service.getByName("non-gmo"),
                "Sourcing value with name 'non-gmo' does not exist"
            );
        }

    }

    public static class MockEmptySourcingValueRepository extends SourcingValueRepository {

        @Override
        public List<SourcingValue> getAll() {
            return Collections.emptyList();
        }

        @Override
        public Optional<SourcingValue> getByName(String name) {
            return Optional.empty();
        }
    }
}
