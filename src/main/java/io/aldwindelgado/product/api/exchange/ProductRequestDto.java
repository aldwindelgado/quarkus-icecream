package io.aldwindelgado.product.api.exchange;

import java.io.Serializable;
import java.util.List;

/**
 * @author Aldwin Delgado
 */
public class ProductRequestDto implements Serializable {

    private static final long serialVersionUID = 172167201268071936L;

    private final String name;

    private final String imageClosed;

    private final String imageOpen;

    private final String description;

    private final String story;

    private final String allergyInfo;

    private final String dietaryCertifications;

    private final List<String> ingredients;

    private final List<String> sourcingValues;

    private ProductRequestDto(String name, String imageClosed, String imageOpen, String description,
        String story, String allergyInfo, String dietaryCertifications, List<String> ingredients,
        List<String> sourcingValues) {
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

    public ProductRequestBuilder builder() {
        return new ProductRequestBuilder();
    }

    public static class ProductRequestBuilder {

        private String name;

        private String imageClosed;

        private String imageOpen;

        private String description;

        private String story;

        private String allergyInfo;

        private String dietaryCertifications;

        private List<String> ingredients;

        private List<String> sourcingValues;

        public ProductRequestBuilder() {
            // no-op
        }

        public ProductRequestBuilder name(String name) {
            this.name = name;
            return this;
        }

        public ProductRequestBuilder imageClosed(String imageClosed) {
            this.imageClosed = imageClosed;
            return this;
        }

        public ProductRequestBuilder imageOpen(String imageOpen) {
            this.imageOpen = imageOpen;
            return this;
        }

        public ProductRequestBuilder description(String description) {
            this.description = description;
            return this;
        }

        public ProductRequestBuilder story(String story) {
            this.story = story;
            return this;
        }

        public ProductRequestBuilder allergyInfo(String allergyInfo) {
            this.allergyInfo = allergyInfo;
            return this;
        }

        public ProductRequestBuilder dietaryCertifications(String dietaryCertifications) {
            this.dietaryCertifications = dietaryCertifications;
            return this;
        }

        public ProductRequestBuilder ingredients(List<String> ingredients) {
            this.ingredients = ingredients;
            return this;
        }

        public ProductRequestBuilder sourcingValues(List<String> sourcingValues) {
            this.sourcingValues = sourcingValues;
            return this;
        }

        public ProductRequestDto build() {
            return new ProductRequestDto(name, imageClosed, imageOpen, description, story, allergyInfo,
                dietaryCertifications, ingredients, sourcingValues);
        }
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
