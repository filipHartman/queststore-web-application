package com.codecool.idontspeakjava.queststore.controllers.codecooler;

import com.codecool.idontspeakjava.queststore.views.CodecoolerView;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;
import com.codecool.idontspeakjava.queststore.database.WalletsDAO;
import com.codecool.idontspeakjava.queststore.database.ArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.OrdersDAO;
import com.codecool.idontspeakjava.queststore.database.ExperienceLevelDAO;
import com.codecool.idontspeakjava.queststore.database.QuestsDAO;

import java.util.ArrayList;
import java.util.List;

public class CodecoolerController {
    private CodecoolerView view;
    private User codecooler;
    private ArtifactsDAO artifactDAO;
    private WalletsDAO walletDAO;
    private ExperienceLevelDAO experienceLevelDAO;
    private OrdersDAO orderDAO;
    private QuestsDAO questsDAO;
    private Wallet wallet;

    private static final String SEE_WALLET = "1";
    private static final String BUY_ARTIFACT = "2";
    private static final String BUY_ARTIFACT_FOR_TEAM = "3";
    private static final String SEE_MY_LEVEL = "4";
    private static final String SEE_QUESTS = "5";
    private static final String EXIT = "0";

    public CodecoolerController(User user){
        view = new CodecoolerView();
        this.codecooler = user;
        this.artifactDAO = new ArtifactsDAO();
        this.experienceLevelDAO = new ExperienceLevelDAO();
        this.walletDAO = new WalletsDAO();
        this.orderDAO = new OrdersDAO();
        this.questsDAO = new QuestsDAO();
        this.wallet = walletDAO.getWalletByUserID(codecooler.getId());
    }

    public void run() {
        boolean runProgram = true;

        while (runProgram) {
            view.showMainMenu(codecooler.getFirstName());
            runProgram = selectAction(view.getUserInput());
        }
    }

    private boolean selectAction(String input) {
        boolean continueRunning = true;
        switch (input) {
            case SEE_WALLET:
                checkWallet();
                break;
            case BUY_ARTIFACT:;
                buyArtifact(chooseArtifact(0));
                break;
            case BUY_ARTIFACT_FOR_TEAM:
                buyArtifact(chooseArtifact(1));
                break;
            case SEE_MY_LEVEL:
                checkExperienceLevel();
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

    private int chooseArtifact(int mode){ // 0 for Basic, 1 or else for Magic
        final int BASIC = 0;
        ArtifactCategory category = (mode == BASIC) ? ArtifactCategory.Basic : ArtifactCategory.Magic;
        
        int artifactId = 0;
        ArrayList<String> namesOfArtifacts = new ArrayList<String>();
        ArrayList<Integer> IDs = new ArrayList<>();
        for (Artifact artifact : artifactDAO.getAllArtifacts()) {
            if (artifact.getCategory() == category) {
                namesOfArtifacts.add(artifact.getTitle());
                IDs.add(new Integer(artifact.getId()));
            }
        }
        boolean artifactIsChoosen = false;
        while (!artifactIsChoosen){
            view.showBuyArtifactMenu(namesOfArtifacts);
            String input = view.getUserInput();
            if (input.equals("0")) {
                artifactIsChoosen = true;
            } else {
                if (input.matches("\\d+") && !input.equals("0")){
                    int choosenPosition = Integer.parseInt(input);
                    if (choosenPosition <= namesOfArtifacts.size()){
                        artifactId = IDs.get(choosenPosition - 1).intValue();
                        artifactIsChoosen = true;
                        List<Order> orders = orderDAO.getAllOrdersByUser(codecooler);
                        for (Order order : orders) {
                            if (order.getArtifactID() == artifactId){
                                artifactIsChoosen = false;
                            }
                        }
                    }
                }  
            }
        }
        return artifactId;    
    }

    private void buyArtifact(int id){
        if (!(id == 0)){
            Artifact artifact = artifactDAO.getArtifact(id);
            if (artifact.getPrice() <= wallet.getCurrentState()){
                Order order = new Order(id, wallet.getId(), false);
                orderDAO.createOrder(order);
            } else {
                view.notEnoughCoolcoins();
            }
        }
    }

    private void checkExperienceLevel(){
        long totalEarnings = wallet.getTotalEarnings();
        List<ExperienceLevel> levels = experienceLevelDAO.getAllExperienceLevels();
        ExperienceLevel level = levels.get(0);
        for (ExperienceLevel currentLevel : levels) {
            if (totalEarnings > currentLevel.getThreshold()) {
                level = currentLevel;
            }
        }
        view.showMyLevel(level.getName());
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