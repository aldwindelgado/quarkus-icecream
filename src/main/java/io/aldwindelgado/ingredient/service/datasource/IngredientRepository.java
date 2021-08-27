package io.aldwindelgado.ingredient.service.datasource;

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
public class IngredientRepository implements PanacheRepository<Ingredient> {

    public List<Ingredient> getAll() {
        return listAll(Sort.by("name"));
    }

    public Optional<Ingredient> getByName(String name) {
        return find("lower(name)", name.toLowerCase(Locale.ENGLISH)).singleResultOptional();
    }

    public void save(Ingredient ingredient) {
        persistAndFlush(ingredient);
    }
}
