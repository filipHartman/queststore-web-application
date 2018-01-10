package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class WalletsDAO extends AbstractDAO {

    public static final Logger log = LoggerFactory.getLogger(WalletsDAO.class);

    public Wallet getWalletByUserID(int id) {
        String query = String.format("SELECT * FROM wallets WHERE id = %d", id);
        Wallet wallet = null;
        try {
            if (checkIfWalletExists(id)) {
                log.info(query);
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                wallet = new Wallet.Builder()
                        .setId(resultSet.getInt("id"))
                        .setCurrentState(resultSet.getLong("current_state"))
                        .setTotalEarnings(resultSet.getLong("total_earnings"))
                        .setUserID(resultSet.getInt("user_id"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return wallet;
    }

    public void updateWallet(Wallet wallet) {
        int userID = wallet.getUserID();
        long currentState = wallet.getCurrentState();
        long totalEarnings = wallet.getTotalEarnings();
        String query = String.format("UPDATE wallets SET current_state = %d, total_earnings = %d WHERE user_id = %d",
                currentState, totalEarnings, userID);

        try {
            if (checkIfWalletExists(userID)) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfWalletExists(int userID) throws SQLException {

        String query = String.format("SELECT * FROM wallets WHERE user_id = %d", userID);
        log.info(query);
        ResultSet resultSet = getConnection().createStatement().executeQuery(query);
        return resultSet.next();
    }

}
