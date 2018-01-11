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


    public Order(int id, int artifactID, int walletID, boolean isUsed) {
        this.id = id;
        this.artifactID = artifactID;
        this.walletID = walletID;
        this.isUsed = isUsed;
    }

    private Order(Builder builder) {
        setId(builder.id);
        setArtifactID(builder.artifactID);
        setWalletID(builder.walletID);
        setUsed(builder.isUsed);
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public static final class Builder {
        private int id;
        private int artifactID;
        private int walletID;
        private boolean isUsed;

        public Builder() {
        }

        public Builder setId(int val) {
            id = val;
            return this;
        }

        public Builder setArtifactID(int val) {
            artifactID = val;
            return this;
        }

        public Builder setWalletID(int val) {
            walletID = val;
            return this;
        }

        public Builder setIsUsed(boolean val) {
            isUsed = val;
            return this;
        }

        public Order build() {
            return new Order(this);
        }
    }
}