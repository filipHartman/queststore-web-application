package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.sun.net.httpserver.HttpExchange;

public class WebAdminController extends AbstractHandler {

    @Override
    public void handle(HttpExchange httpExchange) {
        if (checkPermission(httpExchange, Permissions.Root)) {
            try {
                redirectToActionHandler(httpExchange, getAction(httpExchange));
            } catch (IndexOutOfBoundsException e) {
                sendTemplateResponse(httpExchange, "admin_home");
            }
        } else {
            redirectToLocation(httpExchange, "/");
        }
    }

    private void redirectToActionHandler(HttpExchange httpExchange, String action){
        switch(action) {
            case "create-mentor":
                new WebCreateMentor().handle(httpExchange);
                break;
            case "remove-mentor":
                new WebRemoveMentor().handle(httpExchange);
                break;
            case "create-codecoolclass":
                new WebCreateClass().handle(httpExchange);
                break;
            case "assign-mentor":
                new WebAssignMentor().handle(httpExchange);
                break;
            case "edit-mentor-class":
                new WebEditMentorClass().handle(httpExchange);
                break;
            case "edit-mentor-email":
                new WebEditMentorEmail().handle(httpExchange);
                break;
            case "show-mentor":
                new WebShowMentor().handle(httpExchange);
                break;
            case "create-level":
                new WebCreateLevel().handle(httpExchange);
                break;
            default:
                sendTemplateResponse(httpExchange, "admin_home");
                break;
        }
    }

}

