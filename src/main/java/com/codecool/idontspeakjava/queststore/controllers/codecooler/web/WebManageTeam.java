package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.sun.net.httpserver.HttpExchange;

public class WebManageTeam extends AbstractHandler{

    @Override
    public void handle(HttpExchange httpExchange){
        if (checkPermission(httpExchange, Permissions.Team)) {
            try {
                redirectToActionHandler(httpExchange, getAction(httpExchange));

            } catch (IndexOutOfBoundsException e) {
                sendTemplateResponse(httpExchange, "team_home");
            }
        } else {
            redirectToLocation(httpExchange, "/");
        }
    }

    private void redirectToActionHandler(HttpExchange httpExchange, String action){
        switch(action) {
            case "create-team":
                new WebCreateTeam().handle(httpExchange);
                break;
            case "join-team":
                new WebJoinTeam().handle(httpExchange);
                break;
            case "leave-team":
                new WebLeaveTeam().handle(httpExchange);
                break;
            case "go-back":
                setUserPermission(httpExchange, Permissions.Student);
                new WebCodecoolerController().handle(httpExchange);
                break;
            default:
                sendTemplateResponse(httpExchange, "team_home");
                break;
        }
    }

}
