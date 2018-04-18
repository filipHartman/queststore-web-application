package com.codecool.idontspeakjava.queststore.controllers;

import com.sun.net.httpserver.HttpExchange;

public class AlertController extends AbstractHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String action = getAction(exchange);
        String method = exchange.getRequestMethod();

        if (method.equals("GET") && action.equals("mentor-create-success")) {
            String message = "Mentor creation was successful!";
            String form = "<fieldset> \n"+
                    "<form method = \"post\">" +
                    "<label>" + message + "</label>";
            form += "<input type = \"submit\" value = \"Continue\">" +
                    "</form> </fieldset>";
            sendTemplateResponseWithForm(exchange, "admin_home", form);

        } else if (method.equals("POST")) {
            redirectToLocation(exchange, "/");
        }
    }
}
