package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Wallet;

import java.sql.SQLException;

public interface WalletsDAO {
    Wallet getWalletByUserID(int id);

    void updateWallet(Wallet wallet);

    boolean checkIfWalletExists(int userID) throws SQLException;
}
