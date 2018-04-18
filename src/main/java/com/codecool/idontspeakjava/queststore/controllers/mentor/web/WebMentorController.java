package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.admin.web.WebAssignMentor;
import com.codecool.idontspeakjava.queststore.controllers.admin.web.WebCreateMentor;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class WebMentorController extends AbstractHandler {

    public WebMentorController() {
        super();
    }

    @Override
    public void handle(HttpExchange httpExchange) {

        try {
            redirectToActionHandler(httpExchange, getAction(httpExchange));
        } catch (IndexOutOfBoundsException e) {
            sendTemplateResponse(httpExchange, "admin_home");
        }
    }

    private void redirectToActionHandler(HttpExchange httpExchange, String action){
        try {
            switch(action){
                case "create-codecooler": break;
                case "add-quest": break;
                case "add-artifact": break;
                case "choose-quest": break;
                case "choose-artifact": break;
                case "choose-student-to-mark-quest": break;
                case "choose-student-to-mark-artifact": break;
                case "check-wallet": break;
                default: sendTemplateResponse(httpExchange, "mentor_home");
                    break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
