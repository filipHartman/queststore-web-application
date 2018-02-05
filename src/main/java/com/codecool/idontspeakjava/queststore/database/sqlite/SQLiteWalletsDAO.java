package com.codecool.idontspeakjava.queststore.database.sqlite;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SQLiteWalletsDAO extends AbstractDAO implements com.codecool.idontspeakjava.queststore.database.WalletsDAO {

    public static final Logger log = LoggerFactory.getLogger(SQLiteWalletsDAO.class);

    @Override
    public Wallet getWalletByUserID(int id) {
        String query = "SELECT * FROM wallets WHERE user_id = ?";
        Wallet wallet = null;
        try {
            if (checkIfWalletExists(id)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();

                log.info(preparedStatement.toString());

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

    @Override
    public void updateWallet(Wallet wallet) {
        int userID = wallet.getUserID();
        long currentState = wallet.getCurrentState();
        long totalEarnings = wallet.getTotalEarnings();
        String query = "UPDATE wallets SET current_state = ?, total_earnings = ? WHERE user_id = ?";

        try {
            if (checkIfWalletExists(userID)) {

                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setLong(1, currentState);
                preparedStatement.setLong(2, totalEarnings);
                preparedStatement.setInt(3, userID);

                log.info(preparedStatement.toString());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkIfWalletExists(int userID) throws SQLException {

        String query = "SELECT * FROM wallets WHERE user_id = ?";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, userID);
        log.info(preparedStatement.toString());
        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

}
