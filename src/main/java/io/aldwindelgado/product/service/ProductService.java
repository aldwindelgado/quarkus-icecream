package io.aldwindelgado.product.service;

import io.aldwindelgado.product.api.exchange.ProductRequestDto;
import io.aldwindelgado.product.api.exchange.ProductResponseDto;
import io.aldwindelgado.product.service.datasource.ProductRepository;
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
public class ProductService {

    private static final Logger log = Logger.getLogger(ProductService.class);

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
            log.error("Product creation error: ", pEx);
            if (pEx.getCause() instanceof ConstraintViolationException cvEx &&
                UNIQUE_PRODUCT_NAME.equals(cvEx.getConstraintName())) {
                throw new BadRequestException("Duplicate product name");
            }

            // default
            throw new BadRequestException("Invalid request");
        }
    }
}
