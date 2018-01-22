package com.codecool.idontspeakjava.queststore.models;

public class TeamOrder extends Order {

    private int collectedMoney;

    public TeamOrder(int artifactID, int walletID, boolean isUsed, int collectedMoney) {
        super(artifactID, walletID, isUsed);
        this.collectedMoney = collectedMoney;
    }

    public TeamOrder(int id, int artifactID, int walletID, boolean isUsed, int collectedMoney) {
        super(id, artifactID, walletID, isUsed);
        this.collectedMoney = collectedMoney;
    }


    public int getCollectedMoney() {
        return collectedMoney;
    }

    public void setCollectedMoney(int collectedMoney) {
        this.collectedMoney = collectedMoney;
    }


}
