package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class WebManageTeam extends AbstractHandler{

    @Override
    public void handle(HttpExchange httpExchange){
        String method = httpExchange.getRequestMethod();
        if(method.equals("GET")) {
            sendTemplateResponse(httpExchange, "team_home");
        }
    }
}
