package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteQuestsDAO;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import com.sun.net.httpserver.HttpExchange;

import java.util.Map;

public class WebQuestCreator extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();

        String form = HTMLGenerator.generateFormToAddQuest();

        if (method.equals("GET")) {
            sendTemplateResponseWithForm(exchange, "mentor_home", form);

        } else if (method.equals("POST")) {
            sendQuestDataToDatabase(exchange);
            redirectToLocation(exchange, "/alert/success");
        }

    }

    public void sendQuestDataToDatabase(HttpExchange exchange) {
        Map<String, String> data = readFormData(exchange);

        String title = data.get("title");
        String description = data.get("description");
        int reward = Integer.parseInt(data.get("reward"));
        QuestCategory category = data.get("category").equals(QuestCategory.Basic.toString()) ? QuestCategory.Basic : QuestCategory.Extra;

        Quest quest = new Quest(title, category, description, reward);
        new SQLiteQuestsDAO().createQuest(quest);
    }
}
