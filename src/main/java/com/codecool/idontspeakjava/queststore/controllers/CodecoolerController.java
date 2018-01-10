package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.views.CodecoolerView;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.database.WalletDAO;
import com.codecool.idontspeakjava.queststore.database.ArtifactDAO;
import com.codecool.idontspeakjava.queststore.database.ExperienceLevelDAO;

import java.util.List;

public class CodecoolerController{

    private CodecoolerView view;
    private ArtifactDAO artifactDAO;
    private WalletDAO walletDAO;
    private ExperienceLevelDAO experienceLevelDAO;
    private User user;
    private Wallet wallet;

    public CodecoolerController(User user){
        this.artifactDAO = new ArtifactDAO();
        this.experienceLevelDAO = new ExperienceLevelDAO();
        this.walletDAO = new WalletDAO();
        this.view = new CodecoolerView();
        this.user = user;
        this.wallet = walletDAO.getWalletByUserID(user.getID);
    }

    private boolean buyArtifact(){
        Wallet userWallet = walletDAO.getWalletByUserID(user.getID);
        List<String> namesOfartifacts = new List<String>();
        for (Artifact artifact : artifactDAO.getAllArtifacts) {
            namesOfartifacts.add(artifact.getTitle());
        }
    }

    private void checkExperienceLevel(){
        long totalEarnings = wallet.getTotalEarnings();
        List<ExperienceLevel> levels = experienceLevelDAO.getAllLevels();
        ExpirienceLevel level = levels.get(0);
        for (int i; i < levels.size(); i++) {
            level = levels.get(i);
            if (level.getThreshold() > totalEarnings) {
                
            }
        }
        view.showMyLevel(experienceLevelDAO.get);
    }

    private void checkWallet(){

    }

    private void editProfile(){
        
    }

    private void manageMyTeam(){

    }

    private void manageMyTeam(Team team){
        
    }
}