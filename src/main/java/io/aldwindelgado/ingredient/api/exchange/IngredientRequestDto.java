package io.aldwindelgado.ingredient.api.exchange;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aldwin Delgado
 */
public class IngredientRequestDto implements Serializable {

    private static final long serialVersionUID = -881854355875614830L;

    private final String name;


    public IngredientRequestDto(String name) {
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
