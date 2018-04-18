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


}
