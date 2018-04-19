package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public class WebArtifactCreator extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();

        String form = HTMLGenerator.generateFormToAddArtifact();

        if (method.equals("GET")) {
            sendTemplateResponseWithForm(exchange, "mentor_home", form);

        } else if (method.equals("POST")) {
            sendArtifactDataToDatabase(exchange);
            redirectToLocation(exchange, "/alert/success");
        }
    }

    private void sendArtifactDataToDatabase(HttpExchange exchange) {
        Map<String, String> data = readFormData(exchange);

        String title = data.get("title");
        String description = data.get("description");
        int price = Integer.parseInt(data.get("price"));
        ArtifactCategory category = data.get("category").equals(ArtifactCategory.Basic.toString()) ? ArtifactCategory.Basic : ArtifactCategory.Magic;

        Artifact artifact = new Artifact(title, category, description, price);
        new SQLiteArtifactsDAO().createArtifact(artifact);
    }
}
