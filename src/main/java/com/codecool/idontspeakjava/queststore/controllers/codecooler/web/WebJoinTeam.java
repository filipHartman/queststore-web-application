package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.TeamsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteTeamsDAO;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

public class WebJoinTeam extends AbstractHandler {
    private TeamsDAO teamsDAO = new SQLiteTeamsDAO();

    @Override
    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();
        User user = getUserBySession(httpExchange);


        List<Team> allTeams = teamsDAO.getAllTeams();
        if(allTeams.isEmpty()) {
            redirectToLocation(httpExchange, "/alert/fail");
        }

        if(method.equals("GET")) {

            if (teamsDAO.checkIfUserIsInTeam(user)) {
                redirectToLocation(httpExchange, "/alert/fail");
            } else {
                String form = HTMLGenerator.getRadioForm(allTeams,"Join team","team");
                sendTemplateResponseWithForm(httpExchange,"team_home", form);
            }
        }

        if(method.equals("POST")) {
            if(joinToTeam(httpExchange, allTeams, user)){
                redirectToLocation(httpExchange, "/alert/success");
            } else {
                redirectToLocation(httpExchange, "/alert/fail");
            }
        }

    }

    private boolean joinToTeam(HttpExchange httpExchange, List<Team> allTeams, User user) {
        Map<String, String> data = readFormData(httpExchange);
        String choosenTeamData = data.get("team");
        Team team = getChoosenTeam(allTeams, choosenTeamData);
        

        if(teamsDAO.addUserToTeam(user, team)) {
            return true;
        }
        return false;
    }

    private Team getChoosenTeam(List<Team> allTeams, String choosenTeamData) {
        Team choosenTeam = null;
        for (Team team : allTeams) {
            if(choosenTeamData.equals(team.toString())) {
                choosenTeam = team;
            }
        }
        return choosenTeam;
    }
}
