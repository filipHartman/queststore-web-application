package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebCodecoolerController extends AbstractHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
        String response = "student menu";
        sendResponse(httpExchange, response);
    }
}
