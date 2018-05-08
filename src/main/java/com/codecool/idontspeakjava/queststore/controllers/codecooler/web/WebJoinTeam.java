package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteTeamsDAO;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;

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

    }
}
