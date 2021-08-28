package io.aldwindelgado.sourcingvalue.api.exchange;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aldwin Delgado
 */
public final class SourcingValueRequestDto implements Serializable {

    private static final long serialVersionUID = 2038063123792402717L;

    private String name;

    public SourcingValueRequestDto() {
        // no-op
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SourcingValueRequestDto that = (SourcingValueRequestDto) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "SourcingValueRequestDto{" +
            "name='" + name + '\'' +
            '}';
    }

}
