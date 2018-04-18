package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class WebAdminController extends AbstractHandler {

    public WebAdminController() {
        super();
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();

        String uri = httpExchange.getRequestURI().toString();
        String[] uriParts = uri.split("/");
        if (uriParts.length == 2) {
            sendTemplateResponse(httpExchange, "admin_home");
        } else {
            String action = uriParts[2];
            if (action.equals("create-mentor")) {
                try {
                    new WebCreateMentor().handle(httpExchange);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (action.equals("assign-mentor")) {
                try {
                    new WebAssignMentor().handle(httpExchange);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
