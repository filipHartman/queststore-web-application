package com.codecool.idontspeakjava.queststore.controllers.mentor.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.WalletsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.*;
import com.codecool.idontspeakjava.queststore.models.*;
import com.sun.net.httpserver.HttpExchange;

import java.util.List;
import java.util.Map;

import static com.codecool.idontspeakjava.queststore.models.Permissions.Student;

public class WebMarkQuest extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        List<User> students = new SQLiteUserDAO().getUsersByPermission(Student);
        List<Quest> quests = new SQLiteQuestsDAO().getAllQuests();

        if (method.equals("GET")) {

            if(getUserIDFromURI(exchange) == -1){
                String form = HTMLGenerator.getRadioForm(students, "Choose student", "student");
                sendTemplateResponseWithForm(exchange, "mentor_home", form);
            }

            else{
                String form = HTMLGenerator.getRadioForm(quests, "Choose quest", "quest");
                sendTemplateResponseWithForm(exchange, "mentor_home", form);
            }


        } else if (method.equals("POST")) {

            if(getUserIDFromURI(exchange) == -1){
                Map<String, String> data = readFormData(exchange);
                String student = data.get("student");
                User chosenUser = getChosenUser(students, student);
                redirectToLocation(exchange, "/mentor/mark-quest/" + chosenUser.getId());
            }else {
                Map<String, String> data = readFormData(exchange);
                String quest = data.get("quest");
                int userID = getUserIDFromURI(exchange);
                Quest chosenQuest = getQuestByName(quest, new SQLiteQuestsDAO().getAllQuests());
                User chosenUser = new SQLiteUserDAO().getUserById(userID);
                addCoinsFromQuest(chosenQuest, chosenUser );
                redirectToLocation(exchange, "/alert/success");

            }

        }

    }

    private void addCoinsFromQuest(Quest chosenQuest, User chosenUser) {
        int reward = chosenQuest.getReward();
        WalletsDAO walletsDAO = new SQLiteWalletsDAO();
        Wallet wallet = walletsDAO.getWalletByUserID(chosenUser.getId());

        long currentCoins = wallet.getCurrentState();
        long totalEarnings = wallet.getTotalEarnings();
        wallet.setCurrentState(currentCoins + reward);
        wallet.setTotalEarnings(totalEarnings + reward);

        walletsDAO.updateWallet(wallet);
    }
}
