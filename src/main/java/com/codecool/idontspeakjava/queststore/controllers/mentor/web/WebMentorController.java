package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class WebMentorController extends AbstractHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String response = "mentor menu";
        sendResponse(httpExchange, response);
    }
}
