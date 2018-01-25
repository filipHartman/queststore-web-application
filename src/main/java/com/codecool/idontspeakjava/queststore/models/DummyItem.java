package com.codecool.idontspeakjava.queststore.models;

public class DummyItem {
    private String title;
    private String description;
    private String category;
    private String rewardOrPrice;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getRewardOrPrice() {
        return Integer.parseInt(rewardOrPrice);
    }

    public void setRewardOrPrice(String rewardOrPrice) {
        this.rewardOrPrice = rewardOrPrice;
    }
}
