package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteOrdersDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteWalletsDAO;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.TeamOrder;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WebSeeWallet extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange){
        String method = exchange.getRequestMethod();

        if(method.equals("GET")){
            User codecooler = getUserBySession(exchange);

            Wallet chosenUserWallet = new SQLiteWalletsDAO().getWalletByUserID(codecooler.getId());
            List<String> walletData = new ArrayList<>(Arrays.asList(
                    "Total earning: " + chosenUserWallet.getTotalEarnings(),
                    "Current State: "+ chosenUserWallet.getCurrentState(),
                    "\n",
                    "Artifacts:"
            ));

            List<String> orders = getUserOrders(codecooler);
            walletData.addAll(orders);

            String responseForm = HTMLGenerator.getList(walletData, codecooler.getFullName(),"list-style-type: none");
            sendTemplateResponseWithForm(exchange, "student_home", responseForm);

        }
    }

    private List<String> getUserOrders(User user){
        List<Order> orders = new SQLiteOrdersDAO().getAllOrdersByUser(user);
        return createInfoAboutOrders(orders);

    }

    private List<String> createInfoAboutOrders(List<Order> orders) {
        SQLiteArtifactsDAO dao = new SQLiteArtifactsDAO();
        List<String> ordersToPrint = new ArrayList<>();

        for (Order o : orders) {
            String orderInfo = dao.getArtifact(o.getArtifactID()).getTitle() + ", ";
            orderInfo += o.isUsed() ? "Used" : "Not used";
            orderInfo += ", ";
            orderInfo += o instanceof TeamOrder ? "Magic" : "Basic";
            ordersToPrint.add(orderInfo);
        }
        return ordersToPrint;
    }
}
