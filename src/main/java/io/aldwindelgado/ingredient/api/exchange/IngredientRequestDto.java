package io.aldwindelgado.ingredient.api.exchange;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Aldwin Delgado
 */
public final class IngredientRequestDto implements Serializable {

    private static final long serialVersionUID = -881854355875614830L;

    private String name;

    public IngredientRequestDto() {
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

        IngredientRequestDto that = (IngredientRequestDto) o;

        return Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "IngredientRequestDto{" +
            "name='" + name + '\'' +
            '}';
    }
}
