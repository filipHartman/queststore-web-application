package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

public class WebRemoveMentor extends AbstractHandler {

    public void handle(HttpExchange httpExchange){

        String method = httpExchange.getRequestMethod();
        List<User> mentors = new SQLiteUserDAO().getUsersByPermission(Permissions.Mentor);

        if(mentors.size() == 0){
            redirectToLocation(httpExchange, "/alert/fail");
        }

        if (method.equals("GET")) {
            String form = HTMLGenerator.getRadioForm(mentors);
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);
        }

        if(method.equals("POST")){
            if(removeMentor(httpExchange, mentors)){
                redirectToLocation(httpExchange, "/alert/success");
            }else {
                redirectToLocation(httpExchange, "/alert/fail");

            }
        }
    }

    private boolean removeMentor(HttpExchange httpExchange, List<User> mentors){
        boolean operationSucceeded = false;
        Map<String, String> data = readFormData(httpExchange);
        String name = data.get("name");
        User userToDelete = getChosenUser(mentors, name);

        if(userToDelete != null){
            operationSucceeded = new SQLiteUserDAO(). (userToDelete);
        }
        return operationSucceeded;
    }


}
