package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteOrdersDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteQuestsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.codecool.idontspeakjava.queststore.models.Permissions.Student;

public class WebMarkQuest extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        List<User> students = new SQLiteUserDAO().getUsersByPermission(Student);
        List<Quest> quests = new SQLiteQuestsDAO().getAllQuests();

        if (method.equals("GET")) {

            if(getUserIDFromURI(exchange) == -1){
                String form = HTMLGenerator.getRadioForm(students, "Choose student", "student");
                sendTemplateResponseWithForm(exchange, "mentor_home", form);
            }

            else{
                int userID = getUserIDFromURI(exchange);
                String form = HTMLGenerator.getRadioForm(quests, "Choose quest", "quest");
                sendTemplateResponseWithForm(exchange, "mentor_home", form);
            }


        } else if (method.equals("POST")) {

            if(getUserIDFromURI(exchange) == -1){
                Map<String, String> data = readFormData(exchange);
                String student = data.get("student");
                User studentUser = getChosenUser(students, student);
                redirectToLocation(exchange, "/mentor/mark-quest/" + studentUser.getId());
            }else {
                Map<String, String> data = readFormData(exchange);
                String quest = data.get("quest");

                redirectToLocation(exchange, "/alert/success");

            }

        }


    }
}
