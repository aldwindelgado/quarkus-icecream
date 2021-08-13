package io.aldwindelgado.sourcingvalue.service;

import io.aldwindelgado.product.service.Product;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueResponseDto;
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
public interface SourcingValueMapper {

    @Mapping(source = "products", target = "products", qualifiedByName = "productConverter")
    SourcingValueResponseDto toResponseDto(SourcingValue sourcingValue);

    @Named("productConverter")
    static List<String> extractProductName(Set<Product> products) {
        return products.stream().map(Product::getName).collect(Collectors.toList());
    }

    default List<SourcingValueResponseDto> toResponseDtos(List<SourcingValue> sourcingValues) {
        return sourcingValues.stream().map(this::toResponseDto).collect(Collectors.toList());
    }

}
