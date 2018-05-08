package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteQuestsDAO;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.List;

public class WebGetQuest extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange){
        String method = exchange.getRequestMethod();

        if(method.equals("GET")){

            List<String> quests = getQuests();

            String responseForm = HTMLGenerator.getList(quests, "Quests","list-style-type: none");
            sendTemplateResponseWithForm(exchange, "student_home", responseForm);

        }
    }

    private ArrayList<String> getQuests() {
        List<Quest> quests = new SQLiteQuestsDAO().getAllQuests();
        ArrayList<String> questsStrings = new ArrayList<>();

        for (Quest quest : quests) {
            String questInfo = quest.getTitle() + ", " + quest.getReward() + ", " + quest.getDescription() + ", " + quest.getCategory();
            questsStrings.add(questInfo);
        }
       return questsStrings;
    }
}
