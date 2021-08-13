package io.aldwindelgado.product.service;

import io.aldwindelgado.ingredient.service.Ingredient;
import io.aldwindelgado.product.api.exchange.ProductResponseDto;
import io.aldwindelgado.sourcingvalue.service.SourcingValue;
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
public interface ProductMapper {

    @Mapping(source = "ingredients", target = "ingredients", qualifiedByName = "ingredientConverter")
    @Mapping(source = "sourcingValues", target = "sourcingValues", qualifiedByName = "sourcingValueConverter")
    ProductResponseDto toResponseDto(Product product);

    @Named("ingredientConverter")
    static List<String> extractIngredientName(Set<Ingredient> ingredients) {
        return ingredients.stream().map(Ingredient::getName).collect(Collectors.toList());
    }

    @Named("sourcingValueConverter")
    static List<String> extractSourcingValueName(Set<SourcingValue> sourcingValues) {
        return sourcingValues.stream().map(SourcingValue::getName).collect(Collectors.toList());
    }

    default List<ProductResponseDto> toResponseDtos(List<Product> products) {
        return products.stream().map(this::toResponseDto).collect(Collectors.toList());
    }

}
