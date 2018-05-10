package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteTeamsDAO;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public class WebCreateTeam extends AbstractHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();

        if(method.equals("GET")) {
            String form = HTMLGenerator.generateFromWithOneField("Create team", "name" );
            sendTemplateResponseWithForm(httpExchange, "team_home", form);
        }

        if(method.equals("POST")) {
            if(createTeam(httpExchange)){
                redirectToLocation(httpExchange, "/alert/success");
            }else {
                redirectToLocation(httpExchange, "/alert/fail");
            }
        }
    }

    private boolean createTeam(HttpExchange httpExchange){
        Map<String, String> data = readFormData(httpExchange);
        String name = data.get("name");
        return new SQLiteTeamsDAO().createTeam(new Team(name));
    }
}
