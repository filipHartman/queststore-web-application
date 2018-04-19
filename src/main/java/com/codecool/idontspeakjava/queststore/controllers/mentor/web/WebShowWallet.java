package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteWalletsDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.sun.net.httpserver.HttpExchange;

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
             List <String> walletData = asList( "Total earing: " + choosenUserWallet.getTotalEarnings(),
                     "Current State:"+ choosenUserWallet.getCurrentState());
             String responseForm = HTMLGenerator.getList(walletData, choosenUser.getFullName());
             sendTemplateResponseWithForm(httpExchange, "mentor_home", responseForm);


         }

    }
}
