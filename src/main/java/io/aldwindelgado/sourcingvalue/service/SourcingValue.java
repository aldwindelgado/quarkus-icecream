package io.aldwindelgado.sourcingvalue.service;

import io.aldwindelgado.product.service.Product;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.Hibernate;

@Table(name = "sourcing_value")
@Entity
@Cacheable
public class SourcingValue {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "sourcingValues")
    private List<Product> products = new ArrayList<>();

    public SourcingValue() {
        // no-op
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
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(
            o)) {
            return false;
        }
        SourcingValue that = (SourcingValue) o;

        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return 476740759;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
            "id = " + id + ", " +
            "name = " + name + ")";
    }
}
