package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebAdminController extends AbstractHandler {

    @Override
    public void handle(HttpExchange httpExchange) {

        try {
            redirectToActionHandler(httpExchange, getAction(httpExchange));
        } catch (IndexOutOfBoundsException e) {
            sendTemplateResponse(httpExchange, "admin_home");
        }
    }

    private void redirectToActionHandler(HttpExchange httpExchange, String action){
            switch(action) {
                case "create-mentor":
                    new WebCreateMentor().handle(httpExchange);
                    break;
                case "remove-mentor":
                    break;
                case "create-codecoolclass":
                    break;
                case "assign_mentor":
                    new WebAssignMentor().handle(httpExchange);
                    break;
                case "edit-mentor":
                    break;
                case "show-mentor":
                    break;
                case "create-level":
                    break;
                default:
                    sendTemplateResponse(httpExchange, "admin_home");
                    break;
            }
    }
}

