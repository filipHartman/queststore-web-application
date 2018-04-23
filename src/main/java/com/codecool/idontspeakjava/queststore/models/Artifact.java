package com.codecool.idontspeakjava.queststore.models;

public class Artifact{
    private int id;
    private String title;
    private ArtifactCategory category;
    private String description;
    private int price;

    public Artifact(String title, ArtifactCategory category, String description, int price) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.price = price;
    }

    private Artifact(Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setCategory(builder.category);
        setDescription(builder.description);
        setPrice(builder.price);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArtifactCategory getCategory() {
        return category;
    }

    public void setCategory(ArtifactCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return title;
    }


    public static final class Builder {
        private int id;
        private String title;
        private ArtifactCategory category;
        private String description;
        private int price;

        public Builder() {
        }

        public Builder setId(int val) {
            id = val;
            return this;
        }

        public Builder setTitle(String val) {
            title = val;
            return this;
        }

        public Builder setCategory(ArtifactCategory val) {
            category = val;
            return this;
        }

        public Builder setDescription(String val) {
            description = val;
            return this;
        }

        public Builder setPrice(int val) {
            price = val;
            return this;
        }

        public Artifact build() {
            return new Artifact(this);
        }
    }
}