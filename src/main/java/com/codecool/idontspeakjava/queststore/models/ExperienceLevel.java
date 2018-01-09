package com.codecool.idontspeakjava.queststore.models;

public class ExperienceLevel{

    private int id;
    private String name;
    private long threshold;

    public ExperienceLevel(String name, long threshold) {
        this.name = name;
        this.threshold = threshold;
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
}