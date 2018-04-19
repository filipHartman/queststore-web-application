package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WebRemoveMentor extends AbstractHandler {

    public void handle(HttpExchange httpExchange){

    String method = httpExchange.getRequestMethod();

    List<User> userCollection = new SQLiteUserDAO().getUsersByPermission(Permissions.Mentor);



    String form = HTMLGenerator.getRadioForm(userCollection,"choose mentor to remove", "name" );


        if (method.equals("GET")) {
        sendTemplateResponseWithForm(httpExchange, "admin_home", form);

    }

        if(method.equals("POST")){
        Map<String, String> data = readFormData(httpExchange);
        String name = data.get("name");
        User userToDelete = getChosenUser(userCollection, name);
        new SQLiteUserDAO().deleteUser(userToDelete);

        redirectToLocation(httpExchange, "admin_home");

    }
}
}