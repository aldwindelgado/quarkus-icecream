package io.aldwindelgado.product.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Aldwin Delgado
 */
public class ProductResponseDto implements Serializable {

    private static final long serialVersionUID = 7463713303601720841L;

    private final String name;

    private final String imageClosed;

    private final String imageOpen;

    private final String description;

    private final String story;

    private final String allergyInfo;

    private final String dietaryCertifications;

    private final List<String> ingredients;

    private final List<String> sourcingValues;

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

    private ProductResponseDto(String name, String imageClosed, String imageOpen,
        String description, String story, String allergyInfo, String dietaryCertifications,
        List<String> ingredients, List<String> sourcingValues) {
        this.name = name;
        this.imageClosed = imageClosed;
        this.imageOpen = imageOpen;
        this.description = description;
        this.story = story;
        this.allergyInfo = allergyInfo;
        this.dietaryCertifications = dietaryCertifications;
        this.ingredients = ingredients;
        this.sourcingValues = sourcingValues;
    }

    public static ProductResponseBuilder builder() {
        return new ProductResponseBuilder();
    }

    public static class ProductResponseBuilder {

        private String name;

        private String imageClosed;

        private String imageOpen;

        private String description;

        private String story;

        private String allergyInfo;

        private String dietaryCertifications;

        private List<String> ingredients = new ArrayList<>();

        private List<String> sourcingValues = new ArrayList<>();

        public ProductResponseBuilder() {
            // no-op
        }

        public ProductResponseBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductResponseBuilder imageClosed(String imageClosed) {
            this.imageClosed = imageClosed;
            return this;
        }

        public ProductResponseBuilder imageOpen(String imageOpen) {
            this.imageOpen = imageOpen;
            return this;
        }

        public ProductResponseBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductResponseBuilder story(String story) {
            this.story = story;
            return this;
        }

        public ProductResponseBuilder allergyInfo(String allergyInfo) {
            this.allergyInfo = allergyInfo;
            return this;
        }

        public ProductResponseBuilder dietaryCertifications(String dietaryCertifications) {
            this.dietaryCertifications = dietaryCertifications;
            return this;
        }

        public ProductResponseBuilder ingredients(List<String> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public ProductResponseBuilder sourcingValues(List<String> sourcingValues) {
            this.sourcingValues = sourcingValues;
            return this;
        }

        public ProductResponseDto build() {
            return new ProductResponseDto(name, imageClosed, imageOpen, description, story, allergyInfo,
                dietaryCertifications, ingredients, sourcingValues);
        }

    }

    @Override
    public String toString() {
        return "ProductResponseDto{" +
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
