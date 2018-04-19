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

public class WebEditMentorClass extends AbstractHandler {

    public void handle(HttpExchange httpExchange) {

        String method = httpExchange.getRequestMethod();

        List<User> userCollection = new SQLiteUserDAO().getUsersByPermission(Permissions.Mentor);
        List <CodecoolClass> classCollection = new SQLiteCodecoolClassDAO().getAllCodecoolClasses();
        System.out.println(classCollection.get(0));

        String form = HTMLGenerator.getRadioForm(userCollection,
                classCollection,
                "Edit mentor's class",
                "Choose mentor",
                "Choose class",
                "name",
                "className");

        if (method.equals("GET")) {
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);

        }

        if (method.equals("POST")) {
            Map<String, String> data = readFormData(httpExchange);
            String name = data.get("name");
            String className = data.get("className");
            User editedUser = getChosenUser(userCollection, name);
            CodecoolClass choosenClass = getChosenClass(classCollection, className);
            new SQLiteCodecoolClassDAO().removeUserFromCodecoolClass(editedUser);
            new SQLiteCodecoolClassDAO().addUserToCodecoolClass(editedUser, choosenClass);
            redirectToLocation(httpExchange, "admin_home");

        }

    }
}