package io.aldwindelgado.product.service.datasource;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

/**
 * @author Aldwin Delgado
 */
@ApplicationScoped
public class ProductRepository implements PanacheRepository<Product> {

    public List<Product> getAll() {
        return listAll(Sort.by("name"));
    }

    public Optional<Product> getByName(String name) {
        return find("lower(name)", Sort.by("name"), name.toLowerCase(Locale.ENGLISH)).singleResultOptional();
    }

    public void save(Product entity) {
        persistAndFlush(entity);
    }

}
