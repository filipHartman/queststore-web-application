package com.codecool.idontspeakjava.queststore.controllers.codecooler.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.WalletsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteOrdersDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteTeamsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteWalletsDAO;
import com.codecool.idontspeakjava.queststore.models.*;
import com.sun.net.httpserver.HttpExchange;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class WebBuyTeamArtifact extends AbstractHandler {

    @Override
    public void handle(HttpExchange exchange) {
        String method = exchange.getRequestMethod();
        User user = getUserBySession(exchange);
        List<Artifact> allArtifacts = new SQLiteArtifactsDAO().getArtifacts(ArtifactCategory.Magic);

        Team team;
        try {
            if (new SQLiteTeamsDAO().checkIfUserIsInTeam(user)) {
                team = new SQLiteTeamsDAO().getUserTeam(user);

            } else {
                redirectToLocation(exchange, "/alert/fail");
                return;
            }

        } catch (SQLException e) {
            redirectToLocation(exchange, "/alert/fail");
            e.printStackTrace();
            return;
        }

        if (method.equals("GET")) {
            String form = HTMLGenerator.generateFormToBuyArtifactWithTeam(allArtifacts, "Buy artifact for team", "Enter contribution:");
            sendTemplateResponseWithForm(exchange, "student_home", form);

        } else if (method.equals("POST")) {
            Map<String, String> data = readFormData(exchange);
            System.out.println(data);
            int contribution = Integer.parseInt(data.get("Enter+contribution%3A"));
            String artifactName = data.get("artifact");
            Artifact artifact = getArtifactByName(artifactName, allArtifacts);
            WalletsDAO walletsDao = new SQLiteWalletsDAO();
            Wallet wallet = walletsDao.getWalletByUserID(getUserBySession(exchange).getId());

            if (contribution > wallet.getCurrentState()) {
                redirectToLocation(exchange, "/alert/fail");
                return;
            } else {

                List<TeamOrder> teamOrders = new SQLiteOrdersDAO().getAllOrdersByTeam(team);
                TeamOrder existingOrder = null;
                for (TeamOrder teamOrder : teamOrders) {
                    if (teamOrder.getArtifactID() == artifact.getId() && !teamOrder.isUsed()) {
                        existingOrder = teamOrder;
                        break;
                    }
                }
                if (existingOrder != null) {
                    int remainingContribution = artifact.getPrice() - existingOrder.getCollectedMoney();
                    if (contribution > remainingContribution) {
                        contribution -= contribution - remainingContribution;
                    }
                    existingOrder.setCollectedMoney(existingOrder.getCollectedMoney() + contribution);
                    new SQLiteOrdersDAO().updateOrder(existingOrder);
                } else {
                    if (contribution > artifact.getPrice()) {
                        contribution = artifact.getPrice();
                    }
                    TeamOrder newOrder = new TeamOrder(artifact.getId(), team.getId(), false, contribution);
                    new SQLiteOrdersDAO().createOrder(newOrder);
                }

                wallet.setCurrentState(wallet.getCurrentState() - contribution);
                walletsDao.updateWallet(wallet);
                redirectToLocation(exchange, "/alert/success");
            }
        }
    }
}
