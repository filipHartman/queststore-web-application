package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class OrdersDAO extends AbstractDAO {

    private static final Logger log = LoggerFactory.getLogger(OrdersDAO.class);

    public void createOrder(Order order) {
        int artifactID = order.getArtifactID();
        int walletID = order.getWalletID();
        int is_used = order.isUsed() ? 1 : 0;
        String query = String.format("INSERT INTO orders(artifact_id, wallet_id, is_used) " +
                "VALUES(%d, %d, %d)", artifactID, walletID, is_used);

        try {
            if (!checkIfOrderExists(order.getId())) {
                log.info(query);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

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

    public void removeOrder(Order order) {
        String query = String.format("DELETE FROM orders WHERE id = %d", order.getId());

        try {
            if (checkIfOrderExists(order.getId())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Order> getAllOrders() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";

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


    public boolean checkIfOrderExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM orders WHERE id=%d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }
}
