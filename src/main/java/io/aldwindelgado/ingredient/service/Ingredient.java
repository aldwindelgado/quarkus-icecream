package io.aldwindelgado.ingredient.service;

import io.aldwindelgado.product.service.Product;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.Hibernate;

/**
 * @author Aldwin Delgado
 */
@Table(name = "ingredient")
@Entity
@Cacheable
public class Ingredient {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "ingredients")
    private Set<Product> products = new HashSet<>();

    public Ingredient() {
        // no-op
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setProducts(Set<Product> products) {
        this.products = products;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Product> getProducts() {
        return products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(
            o)) {
            return false;
        }
        Ingredient that = (Ingredient) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 1847634289;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
            "id = " + id + ", " +
            "name = " + name + ")";
    }
}
