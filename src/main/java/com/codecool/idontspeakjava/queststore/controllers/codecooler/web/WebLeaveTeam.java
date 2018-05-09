package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteTeamsDAO;
import com.codecool.idontspeakjava.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;

public class WebLeaveTeam extends AbstractHandler{
    @Override
    public void handle(HttpExchange httpExchange) {
        User user = getUserBySession(httpExchange);
        if (new SQLiteTeamsDAO().removeUserFromTeam(user)) {
            redirectToLocation(httpExchange, "/alert/success");
        } else {
            redirectToLocation(httpExchange, "/alert/fail");
        }

    }
}
