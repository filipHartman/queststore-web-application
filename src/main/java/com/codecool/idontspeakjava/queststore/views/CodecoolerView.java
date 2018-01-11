package com.codecool.idontspeakjava.queststore.views;

import java.util.ArrayList;

public class CodecoolerView extends UserView{

    public void showMainMenu(String userName) {
    System.out.println(String.format(
            "Hello %s ! Select what you want to do:\n" +
                    "1 - Create new codecooler\n" +
                    "2 - Add new quest\n" +
                    "3 - Add new artifact\n" +
                    "4 - Edit existing quest\n" +
                    "5 - Edit existing artifact\n" +
                    "6 - Mark quest for a codecooler\n" +
                    "7 - Mark artifact for a codecooler\n" +
                    "8 - Check the wallets of codecoolers\n" +
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