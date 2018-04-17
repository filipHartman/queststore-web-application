package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebMentorController extends AbstractHandler {

    public WebMentorController() {
        super();
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();

        if (method.equals("GET")) {
            sendTemplateResponse(httpExchange, "mentor_home");
        }
    }
}
