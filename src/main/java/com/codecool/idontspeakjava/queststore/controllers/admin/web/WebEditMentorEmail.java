package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class WebEditMentorEmail extends AbstractHandler {

    public void handle(HttpExchange httpExchange) throws IOException {

        String method = httpExchange.getRequestMethod();

        List<User> userCollection = new SQLiteUserDAO().getUsersByPermission(Permissions.Mentor);

        String form = HTMLGenerator.getFormToEditMail(userCollection);
        if (method.equals("GET")) {
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);

        }

        if (method.equals("POST")) {
            Map<String, String> data = readFormData(httpExchange);
            String name = data.get("name");
            String email = data.get("email");
            User editedUser = null;
            for(User user : userCollection){
                if(user.toString().equals(name)){
                    editedUser = user;
                }
            }

            editedUser.setEmail(email);

            new SQLiteUserDAO().updateUser(editedUser);
            redirectToLocation(httpExchange, "admin_home");

        }

    }
}



