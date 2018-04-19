package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.QuestsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteQuestsDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

public class WebQuestEditor extends AbstractHandler {
    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        List<Quest> allQuests = new SQLiteQuestsDAO().getAllQuests();

        if (method.equals("GET")) {
            String form = HTMLGenerator.generateFormToEditQuest(allQuests);
            sendTemplateResponseWithForm(exchange, "mentor_home", form);

        } else if (method.equals("POST")) {
            Map<String, String> data = readFormData(exchange);
            String questName = data.get("quest");
            Quest quest = getQuestByName(questName, allQuests);

            String title = data.get("title");
            String description = data.get("description");
            int reward = Integer.parseInt(data.get("reward"));
            QuestCategory category = data.get("category").equals("Basic") ? QuestCategory.Basic : QuestCategory.Extra;

            quest.setTitle(title);
            quest.setDescription(description);
            quest.setReward(reward);
            quest.setCategory(category);

            editQuestInDatabase(exchange, quest);
            redirectToLocation(exchange, "/alert/success");
        }
    }

    public void editQuestInDatabase(HttpExchange exchange, Quest quest) {
        new SQLiteQuestsDAO().updateQuest(quest);
    }
}
