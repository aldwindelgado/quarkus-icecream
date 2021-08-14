package io.aldwindelgado.sourcingvalue.service;

import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueRequestDto;
import io.aldwindelgado.sourcingvalue.api.exchange.SourcingValueResponseDto;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
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
public class SourcingValueService implements PanacheRepositoryBase<SourcingValue, Long> {

    // this is the constraint name declared on the database
    private static final String UNIQUE_SOURCING_VALUE_NAME = "unq_sourcingvalue_name";

    private final SourcingValueMapper mapper;

    public SourcingValueService(SourcingValueMapper mapper) {
        this.mapper = mapper;
    }

    public List<SourcingValueResponseDto> getAll() {
        final var sourcingValues = findAll().list();
        if (sourcingValues.isEmpty()) {
            throw new NotFoundException("No sourcing value exists");
        }

        return mapper.toResponseDtos(sourcingValues);
    }

    public SourcingValueResponseDto getByName(String name) {
        if (name == null) {
            throw new BadRequestException("Sourcing value's name is required");
        }

        final var sourcingValue = find("lower(name)", name.toLowerCase()).singleResultOptional();
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
            persistAndFlush(sourcingValue);
        } catch (PersistenceException pEx) {
            if (pEx.getCause() instanceof ConstraintViolationException) {
                ConstraintViolationException cvEx = (ConstraintViolationException) pEx.getCause();
                if (cvEx.getConstraintName().equals(UNIQUE_SOURCING_VALUE_NAME)) {
                    throw new BadRequestException("Duplicate sourcing value name");
                }
            }

            // default
            throw new BadRequestException("Invalid request");
        }

    }
}
