package io.aldwindelgado.sourcingvalue.service;

import io.aldwindelgado.sourcingvalue.api.SourcingValueResponseDto;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.util.List;
import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.NotFoundException;

/**
 * @author Aldwin Delgado
 */
@ApplicationScoped
public class SourcingValueService implements PanacheRepositoryBase<SourcingValue, Long> {

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
}
