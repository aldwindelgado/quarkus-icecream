package io.aldwindelgado.product.service;

import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.product.api.exchange.ProductRequestDto;
import io.aldwindelgado.product.api.exchange.ProductResponseDto;
import io.aldwindelgado.product.service.datasource.Product;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValue;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import org.apache.commons.text.StringEscapeUtils;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

/**
 * @author Aldwin Delgado
 */
@Mapper(componentModel = "cdi")
public abstract class ProductMapper {

    @Inject
    EntityManager entityManager;

    @Mapping(target = "ingredients", qualifiedByName = "ingredientConverter")
    @Mapping(target = "sourcingValues", qualifiedByName = "sourcingValueConverter")
    abstract ProductResponseDto toResponseDto(Product product);

    @Named("ingredientConverter")
    protected List<String> extractIngredientName(List<Ingredient> ingredients) {
        return ingredients.stream().map(ingredient -> StringEscapeUtils.unescapeJava(ingredient.getName()))
            .collect(Collectors.toList());
    }

    @Named("sourcingValueConverter")
    protected List<String> extractSourcingValueName(List<SourcingValue> sourcingValues) {
        return sourcingValues.stream().map(sourcingValue -> StringEscapeUtils.unescapeJava(sourcingValue.getName()))
            .collect(Collectors.toList());
    }

    List<ProductResponseDto> toResponseDtos(List<Product> products) {
        return products.stream().map(this::toResponseDto).collect(Collectors.toList());
    }

    @Mapping(target = "ingredients", ignore = true)
    @Mapping(target = "sourcingValues", ignore = true)
    abstract Product toEntity(ProductRequestDto requestDto);

    @AfterMapping
    protected void toEntityAfterMapping(@MappingTarget Product product, ProductRequestDto requestDto) {
        if (!requestDto.getIngredients().isEmpty()) {
            final var ingredients = retrieveIngredients(requestDto.getIngredients());
            product.addIngredients(ingredients);
        }

        if (!requestDto.getSourcingValues().isEmpty()) {
            final var sourcingValues = retrieveSourcingValues(requestDto.getSourcingValues());
            product.addSourcingValues(sourcingValues);
        }
    }

    /**
     * Retrieve sourcing values from database using names, case-insensitive
     *
     * @param sourcingValues the sourcing value names
     * @return the sourcing values from database
     */
    @SuppressWarnings("unchecked")
    private List<SourcingValue> retrieveSourcingValues(List<String> sourcingValues) {
        final var loweredCaseNames = sourcingValues.stream().map(String::toLowerCase).collect(Collectors.toList());

        return entityManager.createNativeQuery(
                "SELECT * FROM sourcing_value sov WHERE lower(sov.name) IN :names", SourcingValue.class)
            .setParameter("names", loweredCaseNames)
            .getResultList();
    }

    /**
     * Retrieve ingredients from database using names, case-insensitive
     *
     * @param ingredientNames the ingredient names
     * @return the ingredients from database
     */
    @SuppressWarnings("unchecked")
    private List<Ingredient> retrieveIngredients(List<String> ingredientNames) {
        final var loweredCaseNames = ingredientNames.stream().map(String::toLowerCase).collect(Collectors.toList());
        return entityManager.createNativeQuery(
                "SELECT * FROM ingredient ing WHERE lower(ing.name) IN :names", Ingredient.class)
            .setParameter("names", loweredCaseNames)
            .getResultList();
    }
}
