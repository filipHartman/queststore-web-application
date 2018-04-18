package com.codecool.idontspeakjava.queststore.controllers.login;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.sun.net.httpserver.HttpExchange;

public class WebLogoutController extends AbstractHandler {

    public WebLogoutController() {
        super();
    }

    @Override
    public void handle(HttpExchange httpExchange) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        String sid = getSidFromCookieStr(cookieStr);

        getSessionIdContainer().remove(sid);
        httpExchange.getResponseHeaders().add("Set-Cookie", cookieStr + ";Max-Age=0");

        redirectToLocation(httpExchange,"/login");
    }


}
