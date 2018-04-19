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

        List<User> allMentors = new SQLiteUserDAO().getUsersByPermission(Permissions.Mentor);
        List<CodecoolClass> allCodecoolClasses = new SQLiteCodecoolClassDAO().getAllCodecoolClasses();

        if(allCodecoolClasses.isEmpty() || allMentors.isEmpty()){
            redirectToLocation(httpExchange, "/alert/fail");
        }

        if (method.equals("GET")) {
            String form = HTMLGenerator.getFormToEditClass(allMentors, allCodecoolClasses);
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);
        }

        if(method.equals("POST")){
            if(assignMentorToClass(httpExchange, allMentors, allCodecoolClasses)){
                redirectToLocation(httpExchange, "/alert/success");
            }else {
                redirectToLocation(httpExchange, "/alert/fail");
            }
        }
    }

    private boolean assignMentorToClass(HttpExchange httpExchange, List<User> users, List<CodecoolClass> classes){
        boolean operationSucceeded = false;
        Map<String, String> data = readFormData(httpExchange);

        String userName = data.get("name");
        String className = data.get("className");

        User editedUser = getChosenUser(users, userName);
        CodecoolClass choosenClass = getChosenClass(classes, className);

        if((editedUser != null) && (choosenClass != null)){
            operationSucceeded = new SQLiteCodecoolClassDAO().addUserToCodecoolClass(editedUser, choosenClass);
        }
        return operationSucceeded;
    }
}
