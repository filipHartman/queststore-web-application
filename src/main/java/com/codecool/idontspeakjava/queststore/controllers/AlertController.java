package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.sun.net.httpserver.HttpExchange;

public class AlertController extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
        String sid;
        if (cookieStr == null) {
            redirectToLocation(exchange, "/");
            return;
        } else {
            sid = getSidFromCookieStr(cookieStr);
        }

        if (!isLoggedIn(sid)) {
            redirectToLocation(exchange, "/");
        } else {
            String action = getAction(exchange);
            String method = exchange.getRequestMethod();
            String templateName = getTemplateNameFromHomeLocation(getHomeLocationFromSid(sid));

            if (method.equals("GET") && action.equals("success")) {
                String message = "Operation successful!";
                String form = HTMLGenerator.getAlertForm(message);
                sendTemplateResponseWithForm(exchange, templateName, form);

            } else if (method.equals("GET") && action.equals("fail")) {
                String message = "Operation failed!";
                String form = HTMLGenerator.getAlertForm(message);
                sendTemplateResponseWithForm(exchange, templateName, form);

            } else if (method.equals("POST")) {
                redirectToLocation(exchange, "/");
            }
        }
    }

    private String getTemplateNameFromHomeLocation(String homeLocation) {
        return String.format("%s_home", homeLocation.substring(1));
    }
}
