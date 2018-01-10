package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.views.CodecoolerView;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;
import com.codecool.idontspeakjava.queststore.database.WalletsDAO;
import com.codecool.idontspeakjava.queststore.database.ArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.OrdersDAO;
import com.codecool.idontspeakjava.queststore.database.ExperienceLevelDAO;

import java.util.ArrayList;
import java.util.List;

public class CodecoolerController{

    private CodecoolerView view;
    private ArtifactsDAO artifactDAO;
    private WalletsDAO walletDAO;
    private ExperienceLevelDAO experienceLevelDAO;
    private OrdersDAO orderDAO;
    private User user;
    private Wallet wallet;

    public CodecoolerController(User user){
        this.artifactDAO = new ArtifactsDAO();
        this.experienceLevelDAO = new ExperienceLevelDAO();
        this.walletDAO = new WalletsDAO();
        this.orderDAO = new OrdersDAO();
        this.view = new CodecoolerView();
        this.user = user;
        this.wallet = walletDAO.getWalletByUserID(user.getId());
    }

    public void start(){

    }

    private boolean buyArtifact(){
        Wallet userWallet = walletDAO.getWalletByUserID(user.getId());
        List<String> namesOfArtifacts = new ArrayList<String>();
        for (Artifact artifact : artifactDAO.getAllArtifacts()) {
            namesOfArtifacts.add(artifact.getTitle());
        }
    }

    private void checkExperienceLevel(){
        long totalEarnings = wallet.getTotalEarnings();
        List<ExperienceLevel> levels = experienceLevelDAO.getAllLevels();
        ExperienceLevel level = levels.get(0);
        for (int i = 0; i < levels.size(); i++) {
            if (totalEarnings < levels.get(i).getThreshold()) {
                level = levels.get(i-1);
            }
        }
        view.showMyLevel(level.getName());
    }

    private void checkWallet(){
        List<Order> allUserOrders = orderDAO.getAllOrdersByUserID(user.getId());
        ArrayList<String> namesOfArtifacts = new ArrayList<String>();
        for (Order order : allUserOrders) {
            String artifactName = artifactDAO.getArtifact(order.getArtifactID()).getTitle();
            namesOfArtifacts.add(artifactName);
        }
        view.showWallet(wallet.getCurrentState(), namesOfArtifacts);
    }

    private void editProfile(){
        
    }

    private void manageMyTeam(){

    }

    private void manageMyTeam(Team team){
        
    }
}