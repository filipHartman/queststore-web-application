package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.sun.net.httpserver.HttpExchange;

public class WebCodecoolerController extends AbstractHandler{

    @Override
    public void handle(HttpExchange httpExchange) {
        if (checkPermission(httpExchange, Permissions.Student)) {
            try {
                redirectToActionHandler(httpExchange, getAction(httpExchange));
            } catch (IndexOutOfBoundsException e) {
                sendTemplateResponse(httpExchange, "student_home");
            }
        } else {
            redirectToLocation(httpExchange, "/");
        }
    }

    private void redirectToActionHandler(HttpExchange httpExchange, String action){
        switch(action) {
            case "buy-artifact":
                new WebBuyArtifact().handle(httpExchange);
                break;
            case "get-quest":
                new WebGetQuest().handle(httpExchange);
                break;
            case "manage-team":
                new WebManageTeam().handle(httpExchange);
                break;
            case "see-wallet":
                new WebSeeWallet().handle(httpExchange);
                break;
            default:
                sendTemplateResponse(httpExchange, "student_home");
                break;
        }
    }

}
