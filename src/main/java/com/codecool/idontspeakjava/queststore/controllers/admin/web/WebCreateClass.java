package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class WebCreateClass extends AbstractHandler {
    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        String method = httpExchange.getRequestMethod();

        String form = HTMLGenerator.generateFromWith1Field("Create codecool class", "Codecool class name" );

        if(method.equals("GET")) {
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);
        }

        if(method.equals("POST")) {
            Map<String, String> data = readFormData(httpExchange);

            String name = data.get("name");
            new SQLiteCodecoolClassDAO().createCodecoolClass(new CodecoolClass(name));

            redirectToLocation(httpExchange, "/");
        }
    }
}
