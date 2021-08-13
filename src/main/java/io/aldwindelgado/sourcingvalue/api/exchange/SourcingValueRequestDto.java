package io.aldwindelgado.sourcingvalue.api.exchange;

import java.io.Serializable;

/**
 * @author Aldwin Delgado
 */
public class SourcingValueRequestDto implements Serializable {

    private final String name;

    public SourcingValueRequestDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "SourcingValueRequestDto{" +
            "name='" + name + '\'' +
            '}';
    }

}
