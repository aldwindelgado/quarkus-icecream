package io.aldwindelgado.ingredient.api.exchange;

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IngredientResponseDto that = (IngredientResponseDto) o;

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
        return "IngredientResponseDto{" +
            "name='" + name + '\'' +
            ", products=" + products +
            '}';
    }

}
