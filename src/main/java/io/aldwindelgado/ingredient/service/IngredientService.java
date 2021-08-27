package io.aldwindelgado.ingredient.service;

import io.aldwindelgado.ingredient.api.exchange.IngredientRequestDto;
import io.aldwindelgado.ingredient.api.exchange.IngredientResponseDto;
import io.aldwindelgado.ingredient.service.datasource.IngredientRepository;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.PersistenceException;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;
import org.hibernate.exception.ConstraintViolationException;

/**
 * @author Aldwin Delgado
 */
@ApplicationScoped
public class IngredientService {

    // this is the constraint name declared on the database
    private static final String UNIQUE_INGREDIENT_NAME = "unq_ingredient_name";

    private final IngredientRepository repository;
    private final IngredientMapper mapper;

    public IngredientService(IngredientRepository repository, IngredientMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<IngredientResponseDto> getAll() {
        final var ingredients = repository.getAll();
        if (ingredients.isEmpty()) {
            throw new NotFoundException("No ingredients exist");
        }

        return mapper.toResponseDtos(ingredients);
    }

    public IngredientResponseDto getByName(final String name) {
        if (name == null) {
            throw new BadRequestException("Ingredient's name is required");
        }

        final var ingredient = repository.getByName(name);
        if (ingredient.isEmpty()) {
            throw new NotFoundException("Ingredient with name '" + name + "' does not exist");
        }

        return mapper.toResponseDto(ingredient.get());
    }

    public void create(final IngredientRequestDto requestDto) {
        if (requestDto == null) {
            throw new BadRequestException("Request body is required");
        }

        if (requestDto.getName() == null) {
            throw new BadRequestException("Ingredient name is required");
        }

        final var ingredient = mapper.toEntity(requestDto);

        try {
            repository.save(ingredient);
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cvEx = (ConstraintViolationException) pEx.getCause();
                if (cvEx.getConstraintName().equals(UNIQUE_INGREDIENT_NAME)) {
                    throw new BadRequestException("Duplicate ingredient name");
                }
            }

            // default
            throw new BadRequestException("Invalid request");
        }
    }
}
