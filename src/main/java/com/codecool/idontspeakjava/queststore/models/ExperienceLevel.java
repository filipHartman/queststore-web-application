package com.codecool.idontspeakjava.queststore.models;

public class ExperienceLevel{

    private int id;
    private String name;
    private long threshold;

    public ExperienceLevel(String name, long threshold) {
        this.name = name;
        this.threshold = threshold;
    }

    public ExperienceLevel(int id, String name, long threshold) {
        this.id = id;
        this.name = name;
        this.threshold = threshold;
    }

    private ExperienceLevel(Builder builder) {
        setId(builder.id);
        setName(builder.name);
        setThreshold(builder.threshold);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getThreshold() {
        return threshold;
    }

    public void setThreshold(long threshold) {

        this.threshold = threshold;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static final class Builder {
        private int id;
        private String name;
        private long threshold;

        public Builder() {
        }

        public Builder setId(int val) {
            id = val;
            return this;
        }

        public Builder setName(String val) {
            name = val;
            return this;
        }

        public Builder setThreshold(long val) {
            threshold = val;
            return this;
        }

        public ExperienceLevel build() {
            return new ExperienceLevel(this);
        }
    }
}