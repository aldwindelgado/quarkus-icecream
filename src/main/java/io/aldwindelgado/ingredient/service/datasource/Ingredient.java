package io.aldwindelgado.ingredient.service.datasource;

import io.aldwindelgado.product.service.datasource.Product;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Aldwin Delgado
 */
@Table(name = "ingredient")
@Entity
@Cacheable
public class Ingredient {

    @Id
    @SequenceGenerator(name = "ingredientIdSequencer", sequenceName = "ingredient_seq",
        initialValue = 150, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ingredientIdSequencer")
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private List<Product> products = new ArrayList<>();

    public Ingredient() {
        // no-op
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Ingredient that = (Ingredient) o;

        return Objects.equals(getId(), that.getId());
    }

    @Override
    public int hashCode() {
        if (getId() == null) {
            return 0;
        }

        return getId().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
            "id = " + id + ", " +
            "name = " + name + ")";
    }
}
