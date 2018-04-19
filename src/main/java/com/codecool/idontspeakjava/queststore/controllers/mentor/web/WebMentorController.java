package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.admin.web.WebCreateMentor;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.sun.net.httpserver.HttpExchange;

public class WebMentorController extends AbstractHandler {

    public WebMentorController() {
        super();
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        if ((checkPermission(httpExchange, Permissions.Mentor))) {
            try {
                redirectToActionHandler(httpExchange, getAction(httpExchange));
            } catch (IndexOutOfBoundsException e) {
                sendTemplateResponse(httpExchange, "mentor_home");
            }
        } else {
            redirectToLocation(httpExchange, "/");
        }
    }

    private void redirectToActionHandler(HttpExchange httpExchange, String action){
        switch(action) {
            case "create-codecooler":
                new WebCreateStudent().handle(httpExchange);
                break;
            case "add-quest":
                break;
            case "add-artifact":
                break;
            case "choose-quest":
                break;
            case "choose-artifact":
                break;
            case "choose-student-to-mark-quest":
                break;
            case "choose-student-to-mark-artifact":
                break;
            case "check-wallet":
                break;
            default:
                sendTemplateResponse(httpExchange, "mentor_home");
                break;
        }

    }
}
