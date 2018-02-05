package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.TeamOrder;
import com.codecool.idontspeakjava.queststore.models.User;

import java.sql.SQLException;
import java.util.List;

public interface OrdersDAO {
    void createOrder(Order order);

    void createOrder(TeamOrder teamOrder);

    void updateOrder(Order order);

    void updateOrder(TeamOrder teamOrder);

    Order getOrder(int id);

    TeamOrder getTeamOrder(int id);

    void removeOrder(Order order);

    List<Order> getAllOrdersByUser(User user);

    List<TeamOrder> getAllOrdersByTeam(Team team);

    boolean checkIfOrderExists(int id) throws SQLException;

    boolean checkIfTeamOrderExists(int id) throws SQLException;
}
