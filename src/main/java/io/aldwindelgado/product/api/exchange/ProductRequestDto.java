package io.aldwindelgado.product.api.exchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aldwin Delgado
 */
public class ProductRequestDto implements Serializable {

    private static final long serialVersionUID = 172167201268071936L;

    private String name;

    private String imageClosed;

    private String imageOpen;

    private String description;

    private String story;

    private String allergyInfo;

    private String dietaryCertifications;

    private List<String> ingredients = new ArrayList<>();

    private List<String> sourcingValues = new ArrayList<>();

    public ProductRequestDto() {
        // no-op
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageClosed(String imageClosed) {
        this.imageClosed = imageClosed;
    }

    public void setImageOpen(String imageOpen) {
        this.imageOpen = imageOpen;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public void setAllergyInfo(String allergyInfo) {
        this.allergyInfo = allergyInfo;
    }

    public void setDietaryCertifications(String dietaryCertifications) {
        this.dietaryCertifications = dietaryCertifications;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public void setSourcingValues(List<String> sourcingValues) {
        this.sourcingValues = sourcingValues;
    }

    public String getName() {
        return name;
    }

    public String getImageClosed() {
        return imageClosed;
    }

    public String getImageOpen() {
        return imageOpen;
    }

    public String getDescription() {
        return description;
    }

    public String getStory() {
        return story;
    }

    public String getAllergyInfo() {
        return allergyInfo;
    }

    public String getDietaryCertifications() {
        return dietaryCertifications;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public List<String> getSourcingValues() {
        return sourcingValues;
    }

    @Override
    public String toString() {
        return "ProductRequestDto{" +
            "name='" + name + '\'' +
            ", imageClosed='" + imageClosed + '\'' +
            ", imageOpen='" + imageOpen + '\'' +
            ", description='" + description + '\'' +
            ", story='" + story + '\'' +
            ", allergyInfo='" + allergyInfo + '\'' +
            ", dietaryCertifications='" + dietaryCertifications + '\'' +
            ", ingredients=" + ingredients +
            ", sourcingValues=" + sourcingValues +
            '}';
    }
}
