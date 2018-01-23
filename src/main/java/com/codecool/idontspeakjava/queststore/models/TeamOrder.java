package com.codecool.idontspeakjava.queststore.models;

public class TeamOrder extends Order {

    private int collectedMoney;
    private int teamID;

    public TeamOrder(int artifactID, int teamID, boolean isUsed, int collectedMoney) {
        super(artifactID, teamID, isUsed);
        this.teamID = teamID;
        this.collectedMoney = collectedMoney;
    }

    public TeamOrder(int id, int artifactID, int teamID, boolean isUsed, int collectedMoney) {
        super(id, artifactID, teamID, isUsed);
        this.collectedMoney = collectedMoney;
        this.teamID = teamID;
    }


    public int getTeamID() {
        return teamID;
    }

    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }

    public int getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(int collectedMoney) {
        this.collectedMoney = collectedMoney;
    }


}
