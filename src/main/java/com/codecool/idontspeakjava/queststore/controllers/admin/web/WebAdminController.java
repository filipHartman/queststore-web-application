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

        if(method.equals("POST")){

        }else if(method.equals("GET")){

        }


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

    private void handlePostRequest(HttpExchange httpExchange){
        String action = getAction(httpExchange);
        

    }

    private void handleGetRequest(HttpExchange httpExchange){

    }

    private String getAction(HttpExchange httpExchange){
        int actionIndex = 2;
        String uri = httpExchange.getRequestURI().toString();
        String[] uriParts = uri.split("/");
        return uriParts[actionIndex];
    }

}
