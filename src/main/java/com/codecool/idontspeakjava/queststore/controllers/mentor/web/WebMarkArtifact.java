package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteOrdersDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteWalletsDAO;
import com.codecool.idontspeakjava.queststore.models.*;
import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.codecool.idontspeakjava.queststore.models.Permissions.Student;

public class WebMarkArtifact extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        Map<User, Wallet> studentsWithArtifact = new HashMap<>();
        List <User> students = new SQLiteUserDAO().getUsersByPermission(Student);
        for(User student : students) {
            Wallet choosenUserWallet = new SQLiteWalletsDAO().getWalletByUserID(student.getId());
            studentsWithArtifact.put(student, choosenUserWallet);
        }



        if (method.equals("GET")) {
            String form = HTMLGenerator.getRadioForm(students, "Choose student", "student");
            sendTemplateResponseWithForm(exchange, "mentor_home", form);

        } else if (method.equals("POST")) {
            Map<String, String> data = readFormData(exchange);
            String student = data.get("student");
            User studentUser = getChosenUser(students, student);
            //Wallet studentWallet = studentsWithArtifact.get(studentUser);
            List <Order> studentOrders = new SQLiteOrdersDAO().getAllOrdersByUser(studentUser);
            List <Artifact> studentArtifacts = new ArrayList<>();
            for(Order o : studentOrders){
                Artifact artifact = new SQLiteArtifactsDAO().getArtifact(o.getArtifactID());
                studentArtifacts.add(artifact);
            }

            String form = HTMLGenerator.getRadioForm(studentArtifacts, "Choose artifact to mark", "artifact");

            sendTemplateResponseWithForm(exchange, "mentor_home", form);

            //redirectToLocation(exchange, "/alert/success");
        }
    }

}
