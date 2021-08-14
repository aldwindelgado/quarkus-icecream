package io.aldwindelgado.sourcingvalue.api.exchange;

import java.io.Serializable;

/**
 * @author Aldwin Delgado
 */
public class SourcingValueRequestDto implements Serializable {

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
    public String toString() {
        return "SourcingValueRequestDto{" +
            "name='" + name + '\'' +
            '}';
    }

}
