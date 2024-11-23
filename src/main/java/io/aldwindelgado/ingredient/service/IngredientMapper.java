package io.aldwindelgado.ingredient.service;

import io.aldwindelgado.ingredient.api.exchange.IngredientRequestDto;
import io.aldwindelgado.ingredient.api.exchange.IngredientResponseDto;
import io.aldwindelgado.ingredient.service.datasource.Ingredient;
import io.aldwindelgado.product.service.datasource.Product;
import java.util.List;
import org.apache.commons.text.StringEscapeUtils;
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
    static List<String> extractProductName(List<Product> products) {
        return products.stream().map(product -> StringEscapeUtils.unescapeJava(product.getName()))
            .toList();
    }

    default List<IngredientResponseDto> toResponseDtos(List<Ingredient> ingredients) {
        return ingredients.stream().map(this::toResponseDto).toList();
    }

    @Mapping(target = "products", ignore = true)
    Ingredient toEntity(IngredientRequestDto requestDto);

}
