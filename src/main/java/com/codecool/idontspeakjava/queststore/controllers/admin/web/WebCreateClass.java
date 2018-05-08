package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public class WebCreateClass extends AbstractHandler {
    @Override
    public void handle(HttpExchange httpExchange) {
        String method = httpExchange.getRequestMethod();

        String form = HTMLGenerator.generateFromWithOneField("Create class", "name");

        if(method.equals("GET")) {
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);
        }

        if(method.equals("POST")) {
            if(createClass(httpExchange)){
                redirectToLocation(httpExchange, "/alert/success");
            }else {
                redirectToLocation(httpExchange, "/alert/fail");
            }
        }
    }

    private boolean createClass(HttpExchange httpExchange){
        Map<String, String> data = readFormData(httpExchange);
        String name = data.get("name");
        return new SQLiteCodecoolClassDAO().createCodecoolClass(new CodecoolClass(name));
    }
}
