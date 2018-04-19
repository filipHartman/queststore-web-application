package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

public class WebAssignMentor extends AbstractHandler {

    @Override
    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();

        List<User> userCollection = new SQLiteUserDAO().getUsersByPermission(Permissions.Mentor);
        List<CodecoolClass> classCollection = new SQLiteCodecoolClassDAO().getAllCodecoolClasses();

        if (method.equals("GET")) {
            String form = HTMLGenerator.getRadioForm(userCollection,
                    classCollection,
                    "Assign mentor to class",
                    "Choose mentor",
                    "Choose class",
                    "name",
                    "className");

            sendTemplateResponseWithForm(httpExchange, "admin_home", form);
        }

        if(method.equals("POST")){
            assignMentorToClass(httpExchange, userCollection, classCollection);
            redirectToLocation(httpExchange, "admin_home");
        }
    }

    private void assignMentorToClass(HttpExchange httpExchange, List<User> users, List<CodecoolClass> classes){
        Map<String, String> data = null;

        data = readFormData(httpExchange);

        String userName = data.get("name");
        String className = data.get("className");

        User editedUser = getChosenUser(users, userName);
        CodecoolClass choosenClass = getChosenClass(classes, className);


        new SQLiteCodecoolClassDAO().addUserToCodecoolClass(editedUser, choosenClass);

    }

}
