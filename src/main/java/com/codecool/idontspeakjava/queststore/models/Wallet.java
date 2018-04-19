package com.codecool.idontspeakjava.queststore.models;

public class Wallet{

    private int id;
    private int userID;
    private long currentState;
    private long totalEarnings;

    public Wallet(int id, int userID, long currentState, long totalEarnings) {
        this.id = id;
        this.userID = userID;
        this.currentState = currentState;
        this.totalEarnings = totalEarnings;
    }

    private Wallet(Builder builder) {
        setId(builder.id);
        setUserID(builder.userID);
        setCurrentState(builder.currentState);
        setTotalEarnings(builder.totalEarnings);
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


    public static final class Builder {
        private int id;
        private int userID;
        private long currentState;
        private long totalEarnings;

        public Builder() {
        }

        public Builder setId(int val) {
            id = val;
            return this;
        }

        public Builder setUserID(int val) {
            userID = val;
            return this;
        }

        public Builder setCurrentState(long val) {
            currentState = val;
            return this;
        }

        public Builder setTotalEarnings(long val) {
            totalEarnings = val;
            return this;
        }

        public Wallet build() {
            return new Wallet(this);
        }


    }

    @Override
    public String toString() {
        return "Total earing: " + totalEarnings + "\n" +
                "Current State: " + currentState;
    }
}