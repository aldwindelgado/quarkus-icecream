package io.aldwindelgado.ingredient.service;

import io.aldwindelgado.ingredient.api.exchange.IngredientRequestDto;
import io.aldwindelgado.ingredient.api.exchange.IngredientResponseDto;
import io.aldwindelgado.ingredient.service.datasource.IngredientRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.PersistenceException;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.NotFoundException;
import java.util.List;
import org.hibernate.exception.ConstraintViolationException;
import org.jboss.logging.Logger;

/**
 * @author Aldwin Delgado
 */
@ApplicationScoped
public class IngredientService {
    private static final Logger log = Logger.getLogger(IngredientService.class);

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
            log.error("Ingredient creation error: ", pEx);
            if (pEx.getCause() instanceof ConstraintViolationException cvEx &&
                UNIQUE_INGREDIENT_NAME.equals(cvEx.getConstraintName())) {
                throw new BadRequestException("Duplicate ingredient name");
            }

            // default
            throw new BadRequestException("Invalid request");
        }
    }
}
