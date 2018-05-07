package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteQuestsDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

public class WebArtifactEditor extends AbstractHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        List <Artifact> allArtifacts = new SQLiteArtifactsDAO().getAllArtifacts();

        if (method.equals("GET")) {
            String form = HTMLGenerator.generateFormToEditQuestOrArtifact(allArtifacts, "Price", "Magic", "Edit Artifact");
            sendTemplateResponseWithForm(exchange, "mentor_home", form);

        } else if (method.equals("POST")) {
            Map<String, String> data = readFormData(exchange);
            String artifactName = data.get("quest");
            Artifact artifact = getArtifactByName(artifactName, allArtifacts);
            String title = data.get("title");
            String description = data.get("description");
            int price = Integer.parseInt(data.get("Price"));
            ArtifactCategory category = data.get("category").equals("Basic") ? ArtifactCategory.Basic : ArtifactCategory.Magic;

            artifact.setTitle(title);
            artifact.setDescription(description);
            artifact.setPrice(price);
            artifact.setCategory(category);

            editArtifactInDatabase(artifact);
            redirectToLocation(exchange, "/alert/success");
        }
    }

    public void editArtifactInDatabase( Artifact artifact) {
        new SQLiteArtifactsDAO().updateArtifact(artifact);
    }
}
