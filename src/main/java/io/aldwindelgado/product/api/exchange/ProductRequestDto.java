package io.aldwindelgado.product.api.exchange;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Aldwin Delgado
 */
public final class ProductRequestDto implements Serializable {

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
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ProductRequestDto that = (ProductRequestDto) o;

        if (!Objects.equals(name, that.name)) {
            return false;
        }
        if (!Objects.equals(imageClosed, that.imageClosed)) {
            return false;
        }
        if (!Objects.equals(imageOpen, that.imageOpen)) {
            return false;
        }
        if (!Objects.equals(description, that.description)) {
            return false;
        }
        if (!Objects.equals(story, that.story)) {
            return false;
        }
        if (!Objects.equals(allergyInfo, that.allergyInfo)) {
            return false;
        }
        return Objects.equals(dietaryCertifications, that.dietaryCertifications);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (imageClosed != null ? imageClosed.hashCode() : 0);
        result = 31 * result + (imageOpen != null ? imageOpen.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (story != null ? story.hashCode() : 0);
        result = 31 * result + (allergyInfo != null ? allergyInfo.hashCode() : 0);
        result = 31 * result + (dietaryCertifications != null ? dietaryCertifications.hashCode() : 0);
        return result;
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
