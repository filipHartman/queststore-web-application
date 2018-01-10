package com.codecool.idontspeakjava.queststore.views;

import java.util.ArrayList;

public class CodecoolerView extends UserView{

    public void showWallet(long totalMoney, ArrayList<String> namesOfArtifacts){
        System.out.println("WALLET\n");
        System.out.println("Your coolcoins: " + totalMoney);
        System.out.println("Your artifacts: ");
        for (String artifact : namesOfArtifacts) {
            System.out.println("- " + artifact);
        }
    }

    public void showBuyArtifactMenu(ArrayList<String> namesOfArtifacts){
        System.out.println("BASIC ARTIFACTS SHOP\n");
        for (String artifact : namesOfArtifacts) {
            System.out.println(namesOfArtifacts.indexOf(artifact) + ". " + artifact);
        }
    }

    public void showBuyArtifactForTeamMenu(ArrayList<String> namesOfArtifacts){
        System.out.println("MAGIC ARTIFACTS SHOP\n");
        for (String artifact : namesOfArtifacts) {
            System.out.println(namesOfArtifacts.indexOf(artifact) + ". " + artifact);
        }
    }

    public void showMyLevel(String level){
        System.out.println("Rank: " + level);
    }
}