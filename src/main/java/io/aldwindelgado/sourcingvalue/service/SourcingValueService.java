package io.aldwindelgado.sourcingvalue.service;

import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueRequestDto;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueResponseDto;
import io.aldwindelgado.sourcingvalue.service.datasource.SourcingValueRepository;
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
public class SourcingValueService {
    private static final Logger log = Logger.getLogger(SourcingValueService.class);

    // this is the constraint name declared on the database
    private static final String UNIQUE_SOURCING_VALUE_NAME = "unq_sourcingvalue_name";

    private final SourcingValueRepository repository;
    private final SourcingValueMapper mapper;

    public SourcingValueService(SourcingValueRepository repository, SourcingValueMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public List<SourcingValueResponseDto> getAll() {
        final var sourcingValues = repository.getAll();
        if (sourcingValues.isEmpty()) {
            throw new NotFoundException("No sourcing value exists");
        }

        return mapper.toResponseDtos(sourcingValues);
    }

    public SourcingValueResponseDto getByName(String name) {
        if (name == null) {
            throw new BadRequestException("Sourcing value's name is required");
        }

        final var sourcingValue = repository.getByName(name);
        if (sourcingValue.isEmpty()) {
            throw new NotFoundException("Sourcing value with name '" + name + "' does not exist");
        }

        return mapper.toResponseDto(sourcingValue.get());
    }

    public void create(SourcingValueRequestDto requestDto) {
        if (requestDto == null) {
            throw new BadRequestException("Request body is required");
        }

        if (requestDto.getName() == null) {
            throw new BadRequestException("Sourcing value name is required");
        }

        final var sourcingValue = mapper.toEntity(requestDto);

        try {
            repository.save(sourcingValue);
        } catch (PersistenceException pEx) {
            log.error("Product creation error: ", pEx);
            if (pEx.getCause() instanceof ConstraintViolationException cvEx &&
                UNIQUE_SOURCING_VALUE_NAME.equals(cvEx.getConstraintName())) {
                throw new BadRequestException("Duplicate sourcing value name");
            }

            // default
            throw new BadRequestException("Invalid request");
        }

    }
}
