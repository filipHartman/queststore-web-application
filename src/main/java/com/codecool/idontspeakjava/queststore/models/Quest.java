package com.codecool.idontspeakjava.queststore.models;

public class Quest{

    private int id;
    private String title;
    private QuestCategory category;
    private String description;
    private int reward;

    public Quest(String title, QuestCategory category, String description, int reward) {
        this.title = title;
        this.category = category;
        this.description = description;
        this.reward = reward;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public QuestCategory getCategory() {
        return category;
    }

    public void setCategory(QuestCategory category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReward() {
        return reward;
    }

    public void setReward(int reward) {
        this.reward = reward;
    }
}