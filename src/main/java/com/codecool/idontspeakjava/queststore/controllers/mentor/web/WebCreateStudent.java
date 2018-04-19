package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.services.PasswordService;
import com.sun.net.httpserver.HttpExchange;


import java.sql.SQLException;
import java.util.Map;

public class WebCreateStudent extends AbstractHandler{

    public void handle(HttpExchange httpExchange) {

        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            System.out.println(HTMLGenerator.generateFormToCreateUser("dupa"));
            sendTemplateResponseWithForm(httpExchange, "mentor_home", HTMLGenerator.generateFormToCreateUser("Create student"));

        }

        if (method.equals("POST")) {
            Map<String, String> data = null;

            data = readFormData(httpExchange);
            String name = data.get("name");
            String lastname = data.get("lastname");
            String email = data.get("email");
            String password = new PasswordService().hashPassword(data.get("password"));
            User user = new User(name, lastname, password, email, Permissions.Student);
            SQLiteUserDAO dao = new SQLiteUserDAO();
            
                dao.createUser(user);


            redirectToLocation(httpExchange, "/alert/success");
        }
    }


}

