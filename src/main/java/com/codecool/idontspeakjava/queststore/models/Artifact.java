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
}