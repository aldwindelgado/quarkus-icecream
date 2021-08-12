package io.aldwindelgado.ingredient.api;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aldwin Delgado
 */
public class IngredientResponseDto implements Serializable {

    private static final long serialVersionUID = 5355916465663111534L;

    private final String name;

    private final List<String> products;

    public IngredientResponseDto(String name, List<String> products) {
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
        return "IngredientResponseDto{" +
            "name='" + name + '\'' +
            ", products=" + products +
            '}';
    }

}
