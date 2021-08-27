package io.aldwindelgado.sourcingvalue.service.datasource;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import io.quarkus.panache.common.Sort;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import javax.enterprise.context.ApplicationScoped;

/**
 * @author Aldwin Delgado
 */
@ApplicationScoped
public class SourcingValueRepository implements PanacheRepository<SourcingValue> {

    public List<SourcingValue> getAll() {
        return listAll(Sort.by("name"));
    }

    public Optional<SourcingValue> getByName(String name) {
        return find("lower(name)", Sort.by("name"), name.toLowerCase(Locale.ENGLISH)).singleResultOptional();
    }

    public void save(SourcingValue entity) {
        persistAndFlush(entity);
    }
}
