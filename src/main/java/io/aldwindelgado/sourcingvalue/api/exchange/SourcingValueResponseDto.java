package io.aldwindelgado.sourcingvalue.api.exchange;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @author Aldwin Delgado
 */
public class SourcingValueResponseDto implements Serializable {

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
    public String toString() {
        return "SourcingValueResponseDto{" +
            "name='" + name + '\'' +
            ", products=" + products +
            '}';
    }
}
