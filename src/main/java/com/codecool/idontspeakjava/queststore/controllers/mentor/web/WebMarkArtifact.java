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
        List<User> students = new SQLiteUserDAO().getUsersByPermission(Student);

        if (method.equals("GET")) {

            if(getUserIDFromURI(exchange) == -1){
                String form = HTMLGenerator.getRadioForm(students, "Choose student", "student");
                sendTemplateResponseWithForm(exchange, "mentor_home", form);
            }

            else{
                int userID = getUserIDFromURI(exchange);
                User studentUser = new SQLiteUserDAO().getUserById(userID);
                List<Order> studentOrders = new SQLiteOrdersDAO().getAllOrdersByUser(studentUser);
                List<Artifact> studentArtifacts = new ArrayList<>();
                for (Order o : studentOrders) {
                    if(!o.isUsed()) {
                        Artifact artifact = new SQLiteArtifactsDAO().getArtifact(o.getArtifactID());
                        studentArtifacts.add(artifact);
                    }
                }
                String form = HTMLGenerator.getRadioForm(studentArtifacts, "Choose artifact", "artifact");
                sendTemplateResponseWithForm(exchange, "mentor_home", form);
            }


        } else if (method.equals("POST")) {

            if(getUserIDFromURI(exchange) == -1){
                Map<String, String> data = readFormData(exchange);
                String student = data.get("student");
                User studentUser = getChosenUser(students, student);
                redirectToLocation(exchange, "/mentor/mark-artifact/" + studentUser.getId());
            }else {
                Map<String, String> data = readFormData(exchange);
                String artifact = data.get("artifact");
                List <Artifact> artifacts = new SQLiteArtifactsDAO().getAllArtifacts();
                Artifact artifact1 = getArtifactByName(artifact, artifacts);
                int artifactID = artifact1.getId();

                int userID = getUserIDFromURI(exchange);
                User studentUser = new SQLiteUserDAO().getUserById(userID);
                List<Order> studentOrders = new SQLiteOrdersDAO().getAllOrdersByUser(studentUser);
                Order orderToUpdate = null;
                for(Order o : studentOrders){
                    if(o.getArtifactID() == artifactID){
                        orderToUpdate = o;
                    }
                }

                orderToUpdate.setUsed(true);

                new SQLiteOrdersDAO().updateOrder(orderToUpdate);
                redirectToLocation(exchange, "/alert/success");

            }

        }


    }

}


