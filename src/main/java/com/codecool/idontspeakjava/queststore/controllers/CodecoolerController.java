package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.views.CodecoolerView;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;
import com.codecool.idontspeakjava.queststore.database.WalletDAO;
import com.codecool.idontspeakjava.queststore.database.ArtifactDAO;
import com.codecool.idontspeakjava.queststore.database.OrderDAO;
import com.codecool.idontspeakjava.queststore.database.ExperienceLevelDAO;

import java.util.List;

public class CodecoolerController{

    private CodecoolerView view;
    private ArtifactDAO artifactDAO;
    private WalletDAO walletDAO;
    private ExperienceLevelDAO experienceLevelDAO;
    private OrderDAO orderDAO;
    private User user;
    private Wallet wallet;

    public CodecoolerController(User user){
        this.artifactDAO = new ArtifactDAO();
        this.experienceLevelDAO = new ExperienceLevelDAO();
        this.walletDAO = new WalletDAO();
        this.orderDAO = new OrderDAO();
        this.view = new CodecoolerView();
        this.user = user;
        this.wallet = walletDAO.getWalletByUserID(user.getID);
    }

    private boolean buyArtifact(){
        Wallet userWallet = walletDAO.getWalletByUserID(user.getID);
        List<String> namesOfArtifacts = new List<String>();
        for (Artifact artifact : artifactDAO.getAllArtifacts) {
            namesOfArtifacts.add(artifact.getTitle());
        }
    }

    private void checkExperienceLevel(){
        long totalEarnings = wallet.getTotalEarnings();
        List<ExperienceLevel> levels = experienceLevelDAO.getAllLevels();
        ExpirienceLevel level = levels.get(0);
        for (int i = 0; i < levels.size(); i++) {
            if (totalEarnings < levels.get(i).getThreshold()) {
                level = levels.get(i-1);
            }
        }
        view.showMyLevel(level.getTitle());
    }

    private void checkWallet(){
        List<Order> allOrders = orderDAO.getAllOrders();
        List<String> namesOfArtifacts = new List<String>();
        for (Order order : allOrders) {
            if (order.getID() == wallet.getID()){
                Artifact artifact = artifactDAO.getArtifact(order.getArtifactID());
                namesOfArtifacts.add(artifact.getTitle());
            }
        }
        view.showWallet(wallet.getCurrentState(), namesOfArtifacts)
    }

    private void editProfile(){
        
    }

    private void manageMyTeam(){

    }

    private void manageMyTeam(Team team){
        
    }
}