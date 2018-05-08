package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteTeamsDAO;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

public class WebJoinTeam extends AbstractHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();

        List<Team> allTeams = new SQLiteTeamsDAO().getAllTeams();
        if(allTeams.isEmpty()) {
            redirectToLocation(httpExchange, "/alert/fail");
        }

        if(method.equals("GET")) {
            String form = HTMLGenerator.getRadioForm(allTeams,"Join team","team");
            sendTemplateResponseWithForm(httpExchange,"team_home", form);
        }

        if(method.equals("POST")) {
            if(joinToTeam(httpExchange)){
                redirectToLocation(httpExchange, "/alert/success");
            } else {
                redirectToLocation(httpExchange, "/alert/fail");
            }
        }

    }

    private boolean joinToTeam(HttpExchange httpExchange) {
        boolean operationSucceeded = false;
        Map<String, String> data = readFormData(httpExchange);
        String choosenTeam = data.get("team");
        return operationSucceeded;
    }
}
