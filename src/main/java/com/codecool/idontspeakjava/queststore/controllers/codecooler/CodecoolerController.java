package com.codecool.idontspeakjava.queststore.controllers.codecooler;

import com.codecool.idontspeakjava.queststore.views.CodecoolerView;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Permissions;
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
    private Wallet wallet;
    private QuestsDAO questsDAO;

    private static final String SEE_WALLET = "1";
    private static final String BUY_ARTIFACT = "2";
    private static final String BUY_ARTIFACT_FOR_TEAM = "3";
    private static final String SEE_MY_LEVEL = "4";
    private static final String SEE_QUESTS = "5";
    private static final String EXIT = "0";

    public CodecoolerController(){
        view = new CodecoolerView();
        codecooler = new User("Przemek", "Nachel", "haslo", "cygan@nic.pl", Permissions.Student);
        codecooler.setId(7);
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
            case BUY_ARTIFACT:
                buyArtifact(chooseArtifact());
                break;
            case BUY_ARTIFACT_FOR_TEAM:
                buyArtifact(chooseArtifact());
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

    private int chooseArtifact(){
        int artifactId = 0;
        ArrayList<String> namesOfArtifacts = new ArrayList<String>();
        ArrayList<Integer> IDs = new ArrayList<>();
        for (Artifact artifact : artifactDAO.getAllArtifacts()) {
            namesOfArtifacts.add(artifact.getTitle());
            IDs.add(new Integer(artifact.getId()));
        }
        view.showBuyArtifactMenu(namesOfArtifacts);
        String input = view.getUserInput();
        if (input.matches("\\d+") && !input.equals("0")){
            int choosenPosition = Integer.parseInt(input);
            if (choosenPosition <= namesOfArtifacts.size()){
                artifactId = IDs.get(choosenPosition - 1).intValue();
            }
        }
        return artifactId;    
    }

    private void buyArtifact(int id){
        Artifact artifact = artifactDAO.getArtifact(id);
        if (artifact.getPrice() <= wallet.getCurrentState()){
            Order order = new Order(id, wallet.getId(), false);
            orderDAO.createOrder(order);
        } else {
            view.notEnoughCoolcoins();
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

    private void editProfile(){
        
    }

    private void manageMyTeam(){

    }

    private void manageMyTeam(Team team){
        
    }
}