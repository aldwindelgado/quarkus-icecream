package io.aldwindelgado.product.service;

import io.aldwindelgado.product.api.ProductResponseDto;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 * @author Aldwin Delgado
 */
@ApplicationScoped
public class ProductService implements PanacheRepositoryBase<Product, Long> {

    private final ProductMapper mapper;

    public ProductService(ProductMapper mapper) {
        this.mapper = mapper;
    }

    public List<ProductResponseDto> getAll() {
        final var products = findAll().list();
        if (products.isEmpty()) {
            throw new NotFoundException("No products exist");
        }

        return mapper.toResponseDtos(products);
    }

    public ProductResponseDto getByName(String name) {
        if (name == null) {
            throw new BadRequestException("Product's name is required");
        }

        final var product = find("lower(name)", name.toLowerCase()).singleResultOptional();
        if (product.isEmpty()) {
            throw new NotFoundException("Product with name '" + name + "' does not exist");
        }

        return mapper.toResponseDto(product.get());
    }
}
