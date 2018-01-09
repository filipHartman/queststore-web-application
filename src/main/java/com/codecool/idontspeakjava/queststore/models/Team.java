package com.codecool.idontspeakjava.queststore.models;

public class Team{

    private int id;
    private String name;

    public Team(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}