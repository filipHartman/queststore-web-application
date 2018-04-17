package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.admin.web.HTMLGenerator;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class WebCreateMentor extends AbstractHandler {

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();




        if (method.equals("GET")) {
            sendTemplateResponseWithForm(httpExchange, "admin_home", HTMLGenerator.generateFormToCreateUser() );

        }

        if(method.equals("POST")){
            Map<String, String> data = readFormData(httpExchange);
            String name = data.get("name");
            String lastname = data.get("lastname");
            String email = data.get("email");
            System.out.println(name);

            sendTemplateResponse(httpExchange, "admin_home");
        }


    }
}
