package com.codecool.idontspeakjava.queststore.database.sqlite;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.TeamOrder;
import com.codecool.idontspeakjava.queststore.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class SQLiteOrdersDAO extends AbstractDAO implements com.codecool.idontspeakjava.queststore.database.OrdersDAO {

    private static final Logger log = LoggerFactory.getLogger(SQLiteOrdersDAO.class);

    @Override
    public void createOrder(Order order) {
        int artifactID = order.getArtifactID();
        int walletID = order.getWalletID();
        int is_used = order.isUsed() ? 1 : 0;
        String query = String.format("INSERT INTO orders(artifact_id, wallet_id, is_used) " +
                "VALUES(%d, %d, %d)", artifactID, walletID, is_used);

        try {
            getConnection().createStatement().executeUpdate(query);
        } catch (Exception e) {
        }

    }

    @Override
    public void createOrder(TeamOrder teamOrder) {
        int artifactID = teamOrder.getArtifactID();
        int teamID = teamOrder.getTeamID();
        int is_used = teamOrder.isUsed() ? 1 : 0;
        int collected_money = teamOrder.getCollectedMoney();
        String query = String.format("INSERT INTO team_orders (artifact_id, team_id, is_used, collected_money) " +
                "VALUES(%d, %d, %d, %d)", artifactID, teamID, is_used, collected_money);

        try {
            getConnection().createStatement().executeUpdate(query);
        } catch (Exception e) {
        }

    }

    @Override
    public void updateOrder(Order order) {
        String query = String.format("UPDATE orders SET artifact_id = %d, wallet_id = %d, is_used = %d WHERE id = %d",
                order.getArtifactID(), order.getWalletID(), order.isUsed() ? 1 : 0, order.getId());
        executeUpdateQuery(query, order);
    }

    @Override
    public void updateOrder(TeamOrder teamOrder) {
        String query = String.format("UPDATE team_orders SET artifact_id = %d, team_id = %d, is_used = %d, collected_money = %d WHERE id = %d",
                teamOrder.getArtifactID(), teamOrder.getTeamID(), teamOrder.isUsed() ? 1 : 0, teamOrder.getCollectedMoney(), teamOrder.getId());
        executeUpdateQuery(query, teamOrder);
    }

    @Override
    public Order getOrder(int id) {
        String query = String.format("SELECT * FROM orders WHERE id = %d", id);
        Order order = null;
        try {
            if (checkIfOrderExists(id)) {
                log.info(query);
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                order = new Order.Builder()
                        .setId(resultSet.getInt("id"))
                        .setArtifactID(resultSet.getInt("artifact_id"))
                        .setWalletID(resultSet.getInt("wallet_id"))
                        .setIsUsed(resultSet.getBoolean("is_used"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public TeamOrder getTeamOrder(int id) {
        String query = String.format("SELECT * FROM team_orders WHERE id = %d", id);
        TeamOrder teamOrder = null;
        try {
            if (checkIfTeamOrderExists(id)) {
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                teamOrder = new TeamOrder();
                teamOrder.setId(resultSet.getInt("id"));
                teamOrder.setArtifactID(resultSet.getInt("artifact_id"));
                teamOrder.setTeamID(resultSet.getInt("team_id"));
                teamOrder.setCollectedMoney(resultSet.getInt("collected_money"));
                teamOrder.setUsed(resultSet.getBoolean("is_used"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teamOrder;
    }

    @Override
    public void removeOrder(Order order) {
        String query = String.format("DELETE FROM orders WHERE id = %d", order.getId());
        executeUpdateQuery(query, order);
    }

    @Override
    public List<Order> getAllOrdersByUser(User user) {
        List<Order> orders = new ArrayList<>();
        int walletID = new SQLiteWalletsDAO().getWalletByUserID(user.getId()).getId();
        String query = String.format("SELECT * FROM orders WHERE wallet_id = %d", walletID);

        try {
            log.info(query);
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                orders.add(getOrder(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }

    @Override
    public List<TeamOrder> getAllOrdersByTeam(Team team) {
        List<TeamOrder> orders = new ArrayList<>();
        String query = "SELECT * FROM team_orders WHERE team_id = (SELECT id FROM teams WHERE name = ?);";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, team.getName());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(getTeamOrder(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return orders;
    }


    @Override
    public boolean checkIfOrderExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM orders WHERE id=%d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

    @Override
    public boolean checkIfTeamOrderExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM team_orders WHERE id=%d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

    private void executeUpdateQuery(String query, Order order) {
        try {
            getConnection().createStatement().executeUpdate(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
