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
                new WebQuestCreator().handle(httpExchange);
                break;
            case "add-artifact":
                new WebArtifactCreator().handle(httpExchange);
                break;
            case "edit-quest":
                new WebQuestEditor().handle(httpExchange);
                break;
            case "edit-artifact":
                new WebArtifactEditor().handle(httpExchange);
                break;
            case "mark-quest":
                break;
            case "mark-artifact":
                break;
            case "check-wallet":
                new WebShowWallet().handle(httpExchange);
                break;
            default:
                sendTemplateResponse(httpExchange, "mentor_home");
                break;
        }

    }
}
