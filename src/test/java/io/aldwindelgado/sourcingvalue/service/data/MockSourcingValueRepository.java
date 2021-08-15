package io.aldwindelgado.sourcingvalue.service.data;

import io.aldwindelgado.product.service.Product;
import io.quarkus.test.Mock;
import java.util.List;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

/**
 * @author Aldwin Delgado
 */
//@Mock
//@ApplicationScoped
public class MockSourcingValueRepository extends SourcingValueRepository {

    @Override
    public List<SourcingValue> getAll() {
        return List.of(mockedEntity());
    }

    @Override
    public Optional<SourcingValue> getByName(String name) {
        return Optional.of(mockedEntity());
    }

    @Override
    public void save(SourcingValue entity) {
        // do-nothing
    }

    private SourcingValue mockedEntity() {
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
}
