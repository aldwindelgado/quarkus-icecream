package io.aldwindelgado.ingredient.api.exchange;

import java.io.Serializable;

/**
 * @author Aldwin Delgado
 */
public class IngredientRequestDto implements Serializable {

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
    public String toString() {
        return "IngredientRequestDto{" +
            "name='" + name + '\'' +
            '}';
    }
}
