package io.aldwindelgado.ingredient.service;

import io.aldwindelgado.ingredient.api.IngredientResponseDto;
import io.aldwindelgado.product.service.Product;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

/**
 * @author Aldwin Delgado
 */
@Mapper(componentModel = "cdi")
public interface IngredientMapper {

    @Mapping(source = "products", target = "products", qualifiedByName = "productConverter")
    IngredientResponseDto toResponseDto(Ingredient ingredient);

    @Named("productConverter")
    static List<String> extractProductName(Set<Product> products) {
        return products.stream().map(Product::getName).collect(Collectors.toList());
    }

    default List<IngredientResponseDto> toResponseDtos(List<Ingredient> ingredients) {
        return ingredients.stream().map(this::toResponseDto).collect(Collectors.toList());
    }

}
