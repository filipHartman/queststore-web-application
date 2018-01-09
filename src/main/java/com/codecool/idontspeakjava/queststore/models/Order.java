package com.codecool.idontspeakjava.queststore.models;

public class Order{

    private int id;
    private int artifactID;
    private int walletID;
    private boolean isUsed;

    public Order(int artifactID, int walletID, boolean isUsed) {
        this.artifactID = artifactID;
        this.walletID = walletID;
        this.isUsed = isUsed;
    }

    public int getArtifactID() {
        return artifactID;
    }

    public void setArtifactID(int artifactID) {
        this.artifactID = artifactID;
    }

    public int getWalletID() {
        return walletID;
    }

    public void setWalletID(int walletID) {
        this.walletID = walletID;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean used) {
        isUsed = used;
    }
}