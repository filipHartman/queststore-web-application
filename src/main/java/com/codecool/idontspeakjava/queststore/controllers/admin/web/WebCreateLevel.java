package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteExperienceLevelDAO;
import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

public class WebCreateLevel extends AbstractHandler {
    @Override
    public void handle(HttpExchange httpExchange){
        String method = httpExchange.getRequestMethod();

        String form = HTMLGenerator.generateFormToCreateLevel("Create experience level");

        if(method.equals("GET")){
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);
        }else if(method.equals("POST")){
            try {
                parseFormDataToLevelObject(httpExchange);
            } catch (IOException e) {
                e.printStackTrace();
            }
            redirectToLocation(httpExchange, "/");

        }
    }

    private void parseFormDataToLevelObject(HttpExchange httpExchange) throws IOException {
        Map<String, String> data = readFormData(httpExchange);

        String name = data.get("name");
        Long threshold = Long.parseLong(data.get("threshold"));

        new SQLiteExperienceLevelDAO().createExperienceLevel(new ExperienceLevel(name, threshold));
    }
}