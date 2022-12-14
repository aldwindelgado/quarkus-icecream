package io.aldwindelgado.product.service;

import io.aldwindelgado.product.api.exchange.ProductRequestDto;
import io.aldwindelgado.product.api.exchange.ProductResponseDto;
import io.aldwindelgado.product.service.datasource.ProductRepository;
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
public class ProductService {

    // this is the constraint name declared on the database
    private static final String UNIQUE_PRODUCT_NAME = "unq_product_name";

    private final ProductRepository repository;
    private final ProductMapper mapper;

    public ProductService(ProductRepository repository, ProductMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<ProductResponseDto> getAll() {
        final var products = repository.getAll();
        if (products.isEmpty()) {
            throw new NotFoundException("No products exist");
        }

        return mapper.toResponseDtos(products);
    }

    public ProductResponseDto getByName(String name) {
        if (name == null) {
            throw new BadRequestException("Product's name is required");
        }

        final var product = repository.getByName(name);
        if (product.isEmpty()) {
            throw new NotFoundException("Product with name '" + name + "' does not exist");
        }

        return mapper.toResponseDto(product.get());
    }

    public void create(ProductRequestDto requestDto) {
        if (requestDto == null) {
            throw new BadRequestException("Request body is required");
        }

        if (requestDto.getName() == null) {
            throw new BadRequestException("Product name is required");
        }

        final var product = mapper.toEntity(requestDto);

        try {
            repository.save(product);
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cvEx = (ConstraintViolationException) pEx.getCause();
                if (cvEx.getConstraintName().equals(UNIQUE_PRODUCT_NAME)) {
                    throw new BadRequestException("Duplicate product name");
                }
            }

            // default
            throw new BadRequestException("Invalid request");
        }
    }
}
