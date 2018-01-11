package com.codecool.idontspeakjava.queststore.views;

import java.util.ArrayList;

public class CodecoolerView extends UserView{

    public void showMainMenu(String userName) {
    System.out.println(String.format(
            "Hello %s ! Select what you want to do:\n" +
                    "1 - See your wallet\n" +
                    "2 - Buy an artifact\n" +
                    "3 - Buy an artifact for team\n" +
                    "4 - See your level\n" +
                    "5 - See available quests\n" +
                    "0 - Exit the program\n", userName));
    }

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

    public void showWrongInput() {
        System.out.println("Your input is wrong.\n");
    }
}