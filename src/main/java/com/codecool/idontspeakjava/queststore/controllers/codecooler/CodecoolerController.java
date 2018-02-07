package com.codecool.idontspeakjava.queststore.controllers.codecooler;

import com.codecool.idontspeakjava.queststore.database.TeamsDAO;
import com.codecool.idontspeakjava.queststore.database.WalletsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.*;
import com.codecool.idontspeakjava.queststore.models.*;
import com.codecool.idontspeakjava.queststore.views.CodecoolerView;
import com.codecool.idontspeakjava.queststore.views.Colors;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CodecoolerController {
    private CodecoolerView view;
    private User codecooler;
    private SQLiteArtifactsDAO artifactDAO;
    private WalletsDAO walletDAO;
    private SQLiteExperienceLevelDAO experienceLevelDAO;
    private SQLiteOrdersDAO orderDAO;
    private TeamsDAO teamsDAO;
    private SQLiteQuestsDAO questsDAO;
    private Wallet wallet;

    private static final String SEE_WALLET = "1";
    private static final String BUY_ARTIFACT = "2";
    private static final String BUY_ARTIFACT_FOR_TEAM = "3";
    private static final String SEE_QUESTS = "4";
    private static final String MANAGE_TEAMS = "5";
    private static final String EXIT = "0";


    public CodecoolerController(User user) {
        this.view = new CodecoolerView();
        this.codecooler = user;
        this.artifactDAO = new SQLiteArtifactsDAO();
        this.experienceLevelDAO = new SQLiteExperienceLevelDAO();
        this.walletDAO = new SQLiteWalletsDAO();
        this.orderDAO = new SQLiteOrdersDAO();
        this.teamsDAO = new SQLiteTeamsDAO();
        this.questsDAO = new SQLiteQuestsDAO();
        this.wallet = walletDAO.getWalletByUserID(codecooler.getId());
    }

    public void run() {
        boolean runProgram = true;

        try {
            while (runProgram) {
                view.showMainMenu(codecooler.getFirstName(), checkExperienceLevel());
                runProgram = selectAction(view.getUserInput());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean selectAction(String input) throws SQLException {
        boolean continueRunning = true;

        switch (input) {
            case SEE_WALLET:
                checkWallet();
                break;
            case BUY_ARTIFACT:
                buyArtifact();
                break;
            case BUY_ARTIFACT_FOR_TEAM:
                addContributionToArtifact();
                break;
            case SEE_QUESTS:
                seeQuests();
                break;
            case MANAGE_TEAMS:
                new ManageTeamsController(codecooler, teamsDAO, view).start();
                break;
            case EXIT:
                continueRunning = false;
                break;
            default:
                view.showWrongInput();
        }

        return continueRunning;
    }

    private void checkWallet() throws SQLException {
        ArrayList<String> usedArtifacts = new ArrayList<>();
        ArrayList<String> namesOfArtifacts = new ArrayList<>();
        if (teamsDAO.checkIfUserIsInTeam(codecooler)) {
            for (TeamOrder order : orderDAO.getAllOrdersByTeam(teamsDAO.getUserTeam(codecooler))) {
                if (artifactDAO.getArtifact(order.getArtifactID()).getPrice() == order.getCollectedMoney()) {
                    Artifact artifact = artifactDAO.getArtifact(order.getArtifactID());
                    if (order.isUsed()) {
                        usedArtifacts.add(Colors.PURPLE_BOLD_BRIGHT + artifact.getTitle());
                    } else {
                        namesOfArtifacts.add(Colors.PURPLE_BOLD_BRIGHT + artifact.getTitle());
                    }
                }
            }
        }
        for (Order order : orderDAO.getAllOrdersByUser(codecooler)) {
            Artifact artifact = artifactDAO.getArtifact(order.getArtifactID());
            if (order.isUsed()) {
                usedArtifacts.add(artifact.getTitle());
            } else {
                namesOfArtifacts.add(artifact.getTitle());
            }
        }
        view.showWallet(wallet.getCurrentState(), wallet.getTotalEarnings(), namesOfArtifacts, usedArtifacts);
    }

    private void buyArtifact() {
        Artifact artifact = chooseArtifact(ArtifactCategory.Basic);
        if (artifact != null) {
            long currentState = wallet.getCurrentState();
            if (currentState >= artifact.getPrice()) {
                orderDAO.createOrder(new Order(artifact.getId(), wallet.getId(), false));
                wallet.setCurrentState(currentState - artifact.getPrice());
                walletDAO.updateWallet(wallet);
            } else {
                view.notEnoughCoolcoins();
            }
        }
    }

    private void addContributionToArtifact() throws SQLException {
        Artifact artifact;
        long currentState = wallet.getCurrentState();
        if (teamsDAO.checkIfUserIsInTeam(codecooler)) {
            Team team = teamsDAO.getUserTeam(codecooler);
            artifact = chooseArtifact(ArtifactCategory.Magic);
            if (artifact != null) {
                int contribution = view.askForContribution();
                if (contribution > wallet.getCurrentState()) {
                    view.notEnoughCoolcoins();
                } else {
                    List<TeamOrder> teamOrders = orderDAO.getAllOrdersByTeam(team);
                    TeamOrder existingOrder = null;
                    for (TeamOrder teamOrder : teamOrders) {
                        if (teamOrder.getArtifactID() == artifact.getId() && !teamOrder.isUsed()) {
                            existingOrder = teamOrder;
                        }
                    }
                    if (existingOrder != null) {
                        int remainingContribution = artifact.getPrice() - existingOrder.getCollectedMoney();
                        if (contribution > remainingContribution) {
                            contribution -= contribution - remainingContribution;
                        }
                        existingOrder.setCollectedMoney(existingOrder.getCollectedMoney() + contribution);
                        orderDAO.updateOrder(existingOrder);
                    } else {
                        TeamOrder newOrder = new TeamOrder(artifact.getId(), team.getId(), false, contribution);
                        orderDAO.createOrder(newOrder);
                    }
                    wallet.setCurrentState(currentState - contribution);
                    walletDAO.updateWallet(wallet);
                }
            }
        } else {
            view.showThatUserIsNotInTeam();
        }
    }

    private Artifact chooseArtifact(ArtifactCategory category) {
        Artifact chosenArtifact = null;
        ArrayList<Artifact> artifacts = new ArrayList<>();
        ArrayList<String> artifactsInfo = new ArrayList<>();
        for (Artifact artifact : artifactDAO.getAllArtifacts()) {
            if (artifact.getCategory() == category) {
                artifacts.add(artifact);
                String string = artifact.getTitle() + "@" + artifact.getPrice();
                string += category.name().equals(ArtifactCategory.Magic.name()) ?
                        String.valueOf("@" + getCollectedMoney(artifact)) : "";
                if (category.name().equals(ArtifactCategory.Magic.name()) && artifact.getPrice() == getCollectedMoney(artifact)) {
                    string = artifact.getTitle() + "@" + artifact.getPrice() + "@" + "IN WALLET";
                }
                artifactsInfo.add(string);
            }
        }
        boolean optionIsChosen = false;
        while (!optionIsChosen) {
            view.showBuyArtifactMenu(artifactsInfo, wallet.getCurrentState());
            String input = view.getUserInput();
            if (input.matches("\\d+")) {
                if (input.equals("0")) {
                    optionIsChosen = true;
                } else {
                    int chosenPosition = Integer.parseInt(input);
                    if (chosenPosition <= artifacts.size()) {
                        chosenArtifact = artifacts.get(chosenPosition - 1);
                        optionIsChosen = true;
                    }
                }
            }
        }
        return chosenArtifact;
    }

    private int getCollectedMoney(Artifact artifact) {
        List<TeamOrder> orders = orderDAO.getAllOrdersByTeam(teamsDAO.getUserTeam(codecooler));
        for (TeamOrder order : orders) {
            if (order.getArtifactID() == artifact.getId() && !order.isUsed()) {
                    return order.getCollectedMoney();
            }
        }
        return 0;
    }

    private String checkExperienceLevel() {
        long totalEarnings = wallet.getTotalEarnings();
        List<ExperienceLevel> levels = experienceLevelDAO.getAllExperienceLevels();
        ExperienceLevel level = levels.get(0);
        for (ExperienceLevel currentLevel : levels) {
            if (totalEarnings > currentLevel.getThreshold()) {
                level = currentLevel;
            }
        }
        return level.getName();
    }

    private void seeQuests() {
        List<Quest> quests = questsDAO.getAllQuests();
        ArrayList<String> questsStrings = new ArrayList<>();
        for (Quest quest : quests) {
            String questInfo = quest.getTitle() + "@" + quest.getReward() + "@" + quest.getDescription();
            questsStrings.add(questInfo);
        }
        view.showQuests(questsStrings);
    }
}