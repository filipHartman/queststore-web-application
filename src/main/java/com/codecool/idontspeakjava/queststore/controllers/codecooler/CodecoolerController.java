package com.codecool.idontspeakjava.queststore.controllers.codecooler;

import com.codecool.idontspeakjava.queststore.views.CodecoolerView;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.TeamOrder;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;
import com.codecool.idontspeakjava.queststore.database.WalletsDAO;
import com.codecool.idontspeakjava.queststore.database.ArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.OrdersDAO;
import com.codecool.idontspeakjava.queststore.database.ExperienceLevelDAO;
import com.codecool.idontspeakjava.queststore.database.QuestsDAO;
import com.codecool.idontspeakjava.queststore.database.TeamsDAO;

import java.util.ArrayList;
import java.util.List;

public class CodecoolerController {
    private CodecoolerView view;
    private User codecooler;
    private ArtifactsDAO artifactDAO;
    private WalletsDAO walletDAO;
    private ExperienceLevelDAO experienceLevelDAO;
    private OrdersDAO orderDAO;
    private TeamsDAO teamsDAO;
    private QuestsDAO questsDAO;
    private Wallet wallet;

    private static final String SEE_WALLET = "1";
    private static final String BUY_ARTIFACT = "2";
    private static final String BUY_ARTIFACT_FOR_TEAM = "3";
    private static final String SEE_QUESTS = "4";
    private static final String EXIT = "0";

    public CodecoolerController(User user){
        view = new CodecoolerView();
        this.codecooler = user;
        this.artifactDAO = new ArtifactsDAO();
        this.experienceLevelDAO = new ExperienceLevelDAO();
        this.walletDAO = new WalletsDAO();
        this.orderDAO = new OrdersDAO();
        this.teamsDAO = new TeamsDAO();
        this.questsDAO = new QuestsDAO();
        this.wallet = walletDAO.getWalletByUserID(codecooler.getId());
    }

    public void run() {
        boolean runProgram = true;

        while (runProgram) {
            view.showMainMenu(codecooler.getFirstName(), checkExperienceLevel());
            runProgram = selectAction(view.getUserInput());
        }
    }

    private boolean selectAction(String input) {
        boolean continueRunning = true;
        switch (input) {
            case SEE_WALLET:
                checkWallet();
                break;
            case BUY_ARTIFACT:
                buyArtifact(chooseArtifact());
                break;
            case BUY_ARTIFACT_FOR_TEAM:
                buyArtifact(chooseTeamArtifact());
                break;
            case SEE_QUESTS:
                seeQuests();
                break;
            case EXIT:
                continueRunning = false;
                break;
            default:
                view.showWrongInput();
        }
        return continueRunning;
    }

    private int chooseArtifact(){
        int artifactId = 0;
        ArrayList<String> namesOfArtifacts = new ArrayList<String>();
        ArrayList<Integer> IDs = new ArrayList<>();
        ArrayList<Long> prices = new ArrayList<>();

        for (Artifact artifact : artifactDAO.getAllArtifacts()) {
            if (artifact.getCategory() == ArtifactCategory.Basic) {
                namesOfArtifacts.add(artifact.getTitle());
                IDs.add(new Integer(artifact.getId()));
                prices.add(new Long(artifact.getPrice()));
            }
        }

        boolean optionIsChoosen = false;
        while (!optionIsChoosen){

            view.showBuyArtifactMenu(namesOfArtifacts, prices, wallet.getCurrentState(), category);
            String input = view.getUserInput();

            if (input.equals("0")) {
                optionIsChoosen = true;

            } else {

                if (input.matches("\\d+")){

                    int choosenPosition = Integer.parseInt(input);

                    if (choosenPosition <= namesOfArtifacts.size()){

                        artifactId = IDs.get(choosenPosition - 1).intValue();
                        if (!checkIfItemIsBought(artifactId)){
                            optionIsChoosen = true;
                        } else {
                            artifactId = 0;
                        }
                    }
                }  
            }
        }
        return artifactId;    
    }

    private int chooseTeamArtifact(){
        int artifactId = 0;
        List<TeamOrder> teamOrders = orderDAO.getAllOrdersByTeam(teamsDAO.getUserTeam(codecooler));
        ArrayList<String> namesOfArtifacts = new ArrayList<String>();
        ArrayList<Integer> IDs = new ArrayList<>();
        ArrayList<Long> prices = new ArrayList<>();
        ArrayList<Long> collected = new ArrayList<>();

        for (Artifact artifact : artifactDAO.getAllArtifacts()) {
            if (artifact.getCategory() == ArtifactCategory.Magic) {
                namesOfArtifacts.add(artifact.getTitle());
                IDs.add(new Integer(artifact.getId()));
                prices.add(new Long(artifact.getPrice()));
                for (TeamOrder teamOrder : teamOrders) {
                    if (teamOrder.getArtifactID() == artifact.getId()) {
                        collected.add(new Long(teamOrder.getCollectedMoney()));
                    } else {
                        collected.add(new Long(0));
                    }
                }
        }

        boolean optionIsChoosen = false;
        while (!optionIsChoosen){

            view.showBuyArtifactMenu(namesOfArtifacts, prices, wallet.getCurrentState(), category);
            String input = view.getUserInput();

            if (input.equals("0")) {
                optionIsChoosen = true;

            } else {

                if (input.matches("\\d+")){

                    int choosenPosition = Integer.parseInt(input);

                    if (choosenPosition <= namesOfArtifacts.size()){

                        artifactId = IDs.get(choosenPosition - 1).intValue();
                        if (!checkIfItemIsBought(artifactId)){
                            optionIsChoosen = true;
                        } else {
                            artifactId = 0;
                        }
                    }
                }
            }
        }
        return artifactId;
    }

    private boolean checkIfItemIsBought(int id){

        List<Order> orders = orderDAO.getAllOrdersByUser(codecooler);
                        
        for (Order order : orders) {
            if (order.getArtifactID() == id){
                return true;
            }
        }
        return false;
    }

    private void buyArtifact(int id){
        if (!(id == 0)){
            Artifact artifact = artifactDAO.getArtifact(id);
            long currentState = wallet.getCurrentState();
            if (artifact.getPrice() <= currentState){
                Order order = new Order(id, wallet.getId(), false);
                orderDAO.createOrder(order);
                wallet.setCurrentState(currentState - artifact.getPrice());
                walletDAO.updateWallet(wallet);
            } else {
                view.notEnoughCoolcoins();
            }
        }
    }

    private String checkExperienceLevel(){
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

    private void checkWallet(){
        List<Order> allUserOrders = orderDAO.getAllOrdersByUser(codecooler);
        ArrayList<String> namesOfArtifacts = new ArrayList<String>();
        for (Order order : allUserOrders) {
            String artifactName = artifactDAO.getArtifact(order.getArtifactID()).getTitle();
            namesOfArtifacts.add(artifactName);
        }
        view.showWallet(wallet.getCurrentState(), namesOfArtifacts);
    }

    private void seeQuests(){
        List<Quest> quests = questsDAO.getAllQuests();
        ArrayList<String> questsStrings = new ArrayList<String>();
        for (Quest quest : quests) {
            String questInfo = quest.getTitle() + "@" + quest.getReward() + "@" + quest.getDescription();
            questsStrings.add(questInfo);
        }
        view.showQuests(questsStrings);
    }
}