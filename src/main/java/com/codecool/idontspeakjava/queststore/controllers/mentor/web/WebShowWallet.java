package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;


public class WebShowWallet extends AbstractHandler {


    @Override
    public void handle(HttpExchange httpExchange) {

        String method = httpExchange.getRequestMethod();
         if(method.equals("GET")){
             List<User> allStudents = new SQLiteUserDAO().getUsersByPermission(Permissions.Student);
             String form = HTMLGenerator.getRadioForm(allStudents, "Choose student", "name");
             sendTemplateResponseWithForm(httpExchange, "mentor_home", form);
         }

         if(method.equals("POST")){

         }

    }
}
