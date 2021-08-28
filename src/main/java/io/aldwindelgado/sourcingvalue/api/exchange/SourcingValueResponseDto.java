package io.aldwindelgado.sourcingvalue.api.exchange;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aldwin Delgado
 */
public final class SourcingValueResponseDto implements Serializable {

    private static final long serialVersionUID = -7840700679320473933L;

    private final String name;

    private final List<String> products;

    public SourcingValueResponseDto(String name, List<String> products) {
        this.name = name;
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public List<String> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SourcingValueResponseDto that = (SourcingValueResponseDto) o;

        if (!getName().equals(that.getName())) {
            return false;
        }
        return getProducts() != null ? getProducts().equals(that.getProducts()) : that.getProducts() == null;
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + (getProducts() != null ? getProducts().hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SourcingValueResponseDto{" +
            "name='" + name + '\'' +
            ", products=" + products +
            '}';
    }
}
