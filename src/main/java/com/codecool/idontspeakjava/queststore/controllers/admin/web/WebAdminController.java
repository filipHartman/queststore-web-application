package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

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
        try {
            switch(action){
                case "create_mentor": new WebCreateMentor().handle(httpExchange);
                    break;
                case "remove_mentor": break;
                case "create_codecoolclass": break;
                case "assign_mentor": new WebAssignMentor().handle(httpExchange);
                    break;
                case "edit_mentor": break;
                case "show_mentor": break;
                case "create_level": break;
                case "logout": break;
                default: break;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

