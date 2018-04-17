package com.codecool.idontspeakjava.queststore.controllers.root.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class WebAdminController extends AbstractHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "admin menu";
        sendResponse(httpExchange, response);
    }
}
