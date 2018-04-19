package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteOrdersDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteWalletsDAO;
import com.codecool.idontspeakjava.queststore.models.*;
import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;


public class WebShowWallet extends AbstractHandler {


    @Override
    public void handle(HttpExchange httpExchange) {

        List<User> allStudents = new SQLiteUserDAO().getUsersByPermission(Permissions.Student);

        String method = httpExchange.getRequestMethod();
         if(method.equals("GET")){

             String form = HTMLGenerator.getRadioForm(allStudents, "Choose student", "name");
             sendTemplateResponseWithForm(httpExchange, "mentor_home", form);
         }

         if(method.equals("POST")){

             Map<String, String> data = readFormData(httpExchange);
             String userName = data.get("name");
             User choosenUser = getChosenUser(allStudents, userName);
             int choosenUserId = choosenUser.getId();
             Wallet choosenUserWallet = new SQLiteWalletsDAO().getWalletByUserID(choosenUserId);
             List <String> walletData = new ArrayList<>(Arrays.asList(
                     "Total earing: " + choosenUserWallet.getTotalEarnings(),
                     "Current State:"+ choosenUserWallet.getCurrentState(),
                     "\n",
                     "Artifacts:"
             ));


             List<String> test = getUserOrders(choosenUser);
             walletData.addAll(test);

             String responseForm = HTMLGenerator.getList(walletData, choosenUser.getFullName());
             sendTemplateResponseWithForm(httpExchange, "mentor_home", responseForm);

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
            orderInfo += o.isUsed() ? "Used" : "Not used" + ", ";
            orderInfo += o instanceof TeamOrder ? "Magic" : "Basic";
            ordersToPrint.add(orderInfo);
        }
        return ordersToPrint;
    }

}
