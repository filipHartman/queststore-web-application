package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.views.CodecoolerView;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.database.ArtifactDAO;

import java.util.List;

public class CodecoolerController{

    private CodecoolerView view = new CodecoolerView();
    private ArtifactDAO artifactDAO;;

    public CodecoolerController(){
        this.artifactDAO = new ArtifactDAO();
    }

    private boolean buyArtifact(){
        List<String> namesOfartifacts = new List<String>();
        for (Artifact artifact : artifactDAO.getAllArtifacts) {
            namesOfartifacts.add(artifact.title);
        }
    }

    private void checkExperienceLevel(){

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