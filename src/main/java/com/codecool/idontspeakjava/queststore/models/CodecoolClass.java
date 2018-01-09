package com.codecool.idontspeakjava.queststore.models;

public class CodecoolClass{

    private int id;
    private String name;

    public CodecoolClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}