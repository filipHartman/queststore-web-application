package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.sun.net.httpserver.HttpExchange;

public class AlertController extends AbstractHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String action = getAction(exchange);
        String method = exchange.getRequestMethod();

        if (method.equals("GET") && action.equals("success")) {
            String message = "Operation successful!";
            String form = HTMLGenerator.getAlertForm(message);
            sendTemplateResponseWithForm(exchange, "admin_home", form);

        } else if (method.equals("GET") && action.equals("fail")) {
            String message = "Operation failed!";
            String form = HTMLGenerator.getAlertForm(message);
            sendTemplateResponseWithForm(exchange, "admin_home", form);

        } else if (method.equals("POST")) {
            redirectToLocation(exchange, "/");
        }
    }
}
