package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;

import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

public class WebEditMentorEmail extends AbstractHandler {

    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();
        List<User> mentors = new SQLiteUserDAO().getUsersByPermission(Permissions.Mentor);

        if(mentors.size() == 0){
            redirectToLocation(httpExchange, "/alert/fail");
        }

        if (method.equals("GET")) {
            String form = HTMLGenerator.getFormToEditMail(mentors, "edit mentor's e-mail", "Choose mentor", "Enter new e-mail", "name");
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);

        }

        if (method.equals("POST")) {
            if(editMentor(httpExchange, mentors)){
                redirectToLocation(httpExchange, "/alert/success");
            }else {
                redirectToLocation(httpExchange, "/alert/fail");

            }

        }

    }

    private boolean editMentor(HttpExchange httpExchange, List<User> mentors) {
        boolean operationSucceeded = false;
        Map<String, String> data = readFormData(httpExchange);
        String name = data.get("name");
        String email = data.get("email");

        User user = getChosenUser(mentors, name);

        if (user != null) {
            user.setEmail(email);
            operationSucceeded = new SQLiteUserDAO().updateUser(user);

        }
        return operationSucceeded;
    }
}



