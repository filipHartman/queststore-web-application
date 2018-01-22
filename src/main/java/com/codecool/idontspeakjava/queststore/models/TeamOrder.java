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

    private TeamOrder(Builder builder) {
        setId(builder.id);
        setArtifactID(builder.artifactID);
        setWalletID(builder.walletID);
        collectedMoney = builder.collectedMoney;
    }


    public static final class Builder {
        private int id;
        private int artifactID;
        private int walletID;
        private int collectedMoney;

        public Builder() {
        }

        public Builder setId(int id) {
            this.id = id;
            return this;
        }

        public Builder setArtifactID(int artifactID) {
            this.artifactID = artifactID;
            return this;
        }

        public Builder setWalletID(int walletID) {
            this.walletID = walletID;
            return this;
        }

        public Builder setCollectedMoney(int collectedMoney) {
            this.collectedMoney = collectedMoney;
            return this;
        }

        public TeamOrder build() {
            return new TeamOrder(this);
        }
    }
}
