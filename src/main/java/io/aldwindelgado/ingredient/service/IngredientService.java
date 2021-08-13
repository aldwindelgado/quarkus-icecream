package io.aldwindelgado.ingredient.service;

import io.aldwindelgado.ingredient.api.exchange.IngredientRequestDto;
import io.aldwindelgado.ingredient.api.exchange.IngredientResponseDto;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 * @author Aldwin Delgado
 */
@ApplicationScoped
public class IngredientService implements PanacheRepositoryBase<Ingredient, Long> {

    private final IngredientMapper mapper;

    public IngredientService(IngredientMapper mapper) {
        this.mapper = mapper;
    }

    public List<IngredientResponseDto> getAll() {
        final var ingredients = findAll().list();
        if (ingredients.isEmpty()) {
            throw new NotFoundException("No ingredients exist");
        }

        return mapper.toResponseDtos(ingredients);
    }

    public IngredientResponseDto getByName(final String name) {
        if (name == null) {
            throw new BadRequestException("Ingredient's name is required");
        }

        final var ingredient = find("lower(name)", name.toLowerCase()).singleResultOptional();
        if (ingredient.isEmpty()) {
            throw new BadRequestException("Ingredient with name '" + name + "' does not exist");
        }

        return mapper.toResponseDto(ingredient.get());
    }


    public void createIngredient(final IngredientRequestDto requestDto) {
        if (requestDto == null) {
            throw new BadRequestException("Request body is required");
        }

        if (requestDto.getName() == null) {
            throw new BadRequestException("Ingredient name is required");
        }

        persist(mapper.toEntity(requestDto));
    }
}
