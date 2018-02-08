package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteOrdersDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteTeamsDAO;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.TeamOrder;
import com.codecool.idontspeakjava.queststore.models.User;

import java.util.List;

public class Utilities {

    public static List<Order> createOrders(User user) {
        SQLiteOrdersDAO dao = new SQLiteOrdersDAO();
        List<Order> orders = dao.getAllOrdersByUser(user);

        Team team = new SQLiteTeamsDAO().getUserTeam(user);
        if (team != null) {
            List<TeamOrder> teamOrders = dao.getAllOrdersByTeam(team);
            for (TeamOrder order : teamOrders) {
                int collectedMoney = order.getCollectedMoney();
                int priceOfArtifact = new SQLiteArtifactsDAO().getArtifact(order.getArtifactID()).getPrice();
                if (collectedMoney >= priceOfArtifact) {
                    orders.add(order);
                }
            }
        }
        return orders;
    }

    public static String capitalizeString(String stringToCapitalize) {
        char[] splittedString = stringToCapitalize.toLowerCase().toCharArray();
        splittedString[0] = Character.toUpperCase(splittedString[0]);
        return new String(splittedString);
    }
}
