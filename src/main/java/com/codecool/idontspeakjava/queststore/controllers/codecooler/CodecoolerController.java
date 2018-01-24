package com.codecool.idontspeakjava.queststore.controllers.codecooler;

import com.codecool.idontspeakjava.queststore.database.*;
import com.codecool.idontspeakjava.queststore.models.*;
import com.codecool.idontspeakjava.queststore.views.CodecoolerView;

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
    private static final String MANAGE_TEAMS = "5";
    private static final String EXIT = "0";

    public CodecoolerController(User user) {
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
                buyArtifact();
                break;
            case BUY_ARTIFACT_FOR_TEAM:
                addContributionToArtifact(chooseTeamArtifact());
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

    private void checkWallet() {
        ArrayList<String> namesOfArtifacts = new ArrayList<>();
        for (Order order : orderDAO.getAllOrdersByUser(codecooler)) {
            namesOfArtifacts.add(artifactDAO
                    .getArtifact(order.getArtifactID())
                    .getTitle());
        }
        view.showWallet(wallet.getCurrentState(), wallet.getTotalEarnings(), namesOfArtifacts);
    }

    private void buyArtifact() {
        int id = chooseArtifact();
        long currentState = wallet.getCurrentState();
        if (!(id == 0)) {
            Artifact artifact = artifactDAO.getArtifact(id);
            if (currentState >= artifact.getPrice()) {
                Order order = new Order(id, wallet.getId(), false);
                orderDAO.createOrder(order);
                wallet.setCurrentState(currentState - artifact.getPrice());
                walletDAO.updateWallet(wallet);
            } else {
                view.notEnoughCoolcoins();
            }
        }
    }

    private int chooseArtifact(){
        int artifactId = 0;
        ArrayList<String> namesOfArtifacts = new ArrayList<>();
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

            view.showBuyArtifactMenu(namesOfArtifacts, prices, wallet.getCurrentState());
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

    private ArrayList<Integer> chooseTeamArtifact(){
        int artifactId = 0;
        Integer idOfCurrentTeamOrder = new Integer(0);
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
                boolean artifactInTeamOrders = false;
                TeamOrder currentTeamOrder = null;
                for (TeamOrder teamOrder : teamOrders) {
                    if (teamOrder.getArtifactID() == artifact.getId()) {
                        artifactInTeamOrders = true;
                        currentTeamOrder = teamOrder;
                        idOfCurrentTeamOrder = new Integer(currentTeamOrder.getId());
                    }
                }
                if (artifactInTeamOrders) {
                        collected.add(new Long(currentTeamOrder.getCollectedMoney()));
                } else {
                        collected.add(new Long(0));
                }
            }
        }

        boolean optionIsChoosen = false;
        while (!optionIsChoosen){

            view.showBuyTeamArtifactMenu(namesOfArtifacts, collected, prices, wallet.getCurrentState());
            String input = view.getUserInput();

            if (input.equals("0")) {
                optionIsChoosen = true;

            } else {

                if (input.matches("\\d+")){

                    int choosenPosition = Integer.parseInt(input);

                    if (choosenPosition <= namesOfArtifacts.size()){

                        artifactId = IDs.get(choosenPosition - 1).intValue();
                        if (!checkIfTeamItemIsBought(artifactId)){
                            optionIsChoosen = true;
                        } else {
                            artifactId = 0;
                        }
                    }
                }
            }
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(new Integer(artifactId));
        result.add(idOfCurrentTeamOrder);

        return result;
    }

    private void addContributionToArtifact(ArrayList<Integer> artifactAndIdOfOrder){
        int id = Integer.parseInt(artifactAndIdOfOrder.get(0).toString());
        int orderId = Integer.parseInt(artifactAndIdOfOrder.get(1).toString());

        if (!(id == 0)){
            Artifact artifact = artifactDAO.getArtifact(id);
            long currentState = wallet.getCurrentState();
            if (currentState > 0) {
                int contribution = view.askForContribution();
                if (orderId > 0) {
                    TeamOrder order = orderDAO.getTeamOrder(orderId);
                    order.setCollectedMoney(order.getCollectedMoney() + contribution);
                    orderDAO.updateOrder(order);
                } else {
                    TeamOrder order = new TeamOrder(id, teamsDAO.getUserTeam(codecooler).getId(),false, contribution);
                    orderDAO.createOrder(order);
                    wallet.setCurrentState(currentState - artifact.getPrice());
                    walletDAO.updateWallet(wallet);
                }
                wallet.setCurrentState(currentState - contribution);
                walletDAO.updateWallet(wallet);
            } else {
                view.notEnoughCoolcoins();
            }
        }
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

    private boolean checkIfTeamItemIsBought(int id){

        List<TeamOrder> orders = orderDAO.getAllOrdersByTeam(teamsDAO.getUserTeam(codecooler));

        for (TeamOrder order : orders) {
            int artifactId = order.getArtifactID();
            if (artifactId == id && order.getCollectedMoney() == artifactDAO.getArtifact(artifactId).getPrice()){
                return true;
            }
        }
        return false;
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