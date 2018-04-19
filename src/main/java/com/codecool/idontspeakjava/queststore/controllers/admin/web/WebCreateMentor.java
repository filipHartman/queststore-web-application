package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.services.PasswordService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

public class WebCreateMentor extends AbstractHandler {

    @Override
    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            sendTemplateResponseWithForm(httpExchange, "admin_home", HTMLGenerator.generateFormToCreateUser("Create mentor") );

        }

        if(method.equals("POST")){
            Map<String, String> data = null;

            data = readFormData(httpExchange);
            String name = data.get("name");
            String lastname = data.get("lastname");
            String email = data.get("email");
            String password = new PasswordService().hashPassword(data.get("password"));
            User user = new User(name, lastname, password, email, Permissions.Mentor);
            SQLiteUserDAO dao = new SQLiteUserDAO();
            try {
                dao.createUser(user);
            }catch (SQLException e){
                e.printStackTrace();
            }

            redirectToLocation(httpExchange, "/alert/mentor-create-success");
        }


    }
}
