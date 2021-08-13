package io.aldwindelgado.product.service;

import io.aldwindelgado.ingredient.service.Ingredient;
import io.aldwindelgado.sourcingvalue.service.SourcingValue;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import org.hibernate.Hibernate;

@Table(name = "product")
@Entity
@Cacheable
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "image_closed", columnDefinition = "CLOB")
    private String imageClosed;

    @Column(name = "image_open", columnDefinition = "CLOB")
    private String imageOpen;

    @Column(columnDefinition = "CLOB")
    private String description;

    @Column(columnDefinition = "CLOB")
    private String story;

    @Column(name = "allergy_info")
    private String allergyInfo;

    @Column(name = "dietary_certifications")
    private String dietaryCertifications;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "map_product_ingredient",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "ingredient_id")
    )
    private Set<Ingredient> ingredients = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "map_product_sourcingvalue",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "sourcing_value_id")
    )
    private Set<SourcingValue> sourcingValues = new HashSet<>();

    public Product() {
        // no-op
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
        ingredient.getProducts().add(this);
    }

    public void removeIngredient(Ingredient ingredient) {
        ingredients.remove(ingredient);
        ingredient.getProducts().remove(this);
    }

    public void addSourcingValue(SourcingValue sourcingValue) {
        sourcingValues.add(sourcingValue);
        sourcingValue.getProducts().add(this);
    }

    public void removeSourcingValue(SourcingValue sourcingValue) {
        sourcingValues.remove(sourcingValue);
        sourcingValue.getProducts().remove(this);
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

    public String getImageClosed() {
        return imageClosed;
    }

    public void setImageClosed(String imageClosed) {
        this.imageClosed = imageClosed;
    }

    public String getImageOpen() {
        return imageOpen;
    }

    public void setImageOpen(String imageOpen) {
        this.imageOpen = imageOpen;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public String getAllergyInfo() {
        return allergyInfo;
    }

    public void setAllergyInfo(String allergyInfo) {
        this.allergyInfo = allergyInfo;
    }

    public String getDietaryCertifications() {
        return dietaryCertifications;
    }

    public void setDietaryCertifications(String dietaryCertifications) {
        this.dietaryCertifications = dietaryCertifications;
    }

    public Set<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Set<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public Set<SourcingValue> getSourcingValues() {
        return sourcingValues;
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
        Product product = (Product) o;

        return Objects.equals(id, product.id);
    }

    @Override
    public int hashCode() {
        return 2042274511;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
            "id = " + id + ", " +
            "name = " + name + ", " +
            "imageClosed = " + imageClosed + ", " +
            "imageOpen = " + imageOpen + ", " +
            "description = " + description + ", " +
            "story = " + story + ", " +
            "allergyInfo = " + allergyInfo + ", " +
            "dietaryCertifications = " + dietaryCertifications + ")";
    }
}
