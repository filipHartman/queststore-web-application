package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebRootController extends AbstractHandler {

    public WebRootController() {
        super();
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        redirectToLocation(httpExchange, "/login");
    }
}
