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

    private Quest(Builder builder) {
        setId(builder.id);
        setTitle(builder.title);
        setCategory(builder.category);
        setDescription(builder.description);
        setReward(builder.reward);
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
        private QuestCategory category;
        private String description;
        private int reward;

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

        public Builder setCategory(QuestCategory val) {
            category = val;
            return this;
        }

        public Builder setDescription(String val) {
            description = val;
            return this;
        }

        public Builder setReward(int val) {
            reward = val;
            return this;
        }

        public Quest build() {
            return new Quest(this);
        }
    }
}