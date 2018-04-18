package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WebAssignMentor extends AbstractHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        List <User> userCollection = new SQLiteUserDAO().getUsersByPermission(Permissions.Mentor);

        List<String> collection = new ArrayList<>();

        for(User user: userCollection){
            collection.add(user.toString());
        }

        String form = HTMLGenerator.getRadioForm(collection);


        if (method.equals("GET")) {
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);

        }

        if(method.equals("POST")){
            Map<String, String> data = readFormData(httpExchange);
            String name = data.get("name");
            System.out.println(name);

            }
        }

}