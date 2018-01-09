package com.codecool.idontspeakjava.queststore.models;

public class Wallet{

    private int id;
    private int userID;
    private long currentState;
    private long totalEarnings;

    public Wallet(int userID, long currentState, long totalEarnings) {
        this.userID = userID;
        this.currentState = currentState;
        this.totalEarnings = totalEarnings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public long getCurrentState() {
        return currentState;
    }

    public void setCurrentState(long currentState) {
        this.currentState = currentState;
    }

    public long getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(long totalEarnings) {
        this.totalEarnings = totalEarnings;
    }
}