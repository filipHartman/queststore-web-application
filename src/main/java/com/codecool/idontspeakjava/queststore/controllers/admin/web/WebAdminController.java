package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebAdminController extends AbstractHandler {

    public WebAdminController() {
        super();
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            sendTemplateResponse(httpExchange, "admin_home");
        }
    }
}
