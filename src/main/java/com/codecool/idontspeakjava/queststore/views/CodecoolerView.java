package com.codecool.idontspeakjava.queststore.views;

import java.util.ArrayList;

import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;

public class CodecoolerView extends UserView{

    public void showMainMenu(String userName, String rank) {
    System.out.println(CLEAR_CONSOLE);
    System.out.println(String.format(
            "QUESTSTORE\n\nNAME: %s              RANK: %s\n\n" +
                    "1 - See your wallet\n" +
                    "2 - Buy an artifact\n" +
                    "3 - Buy an artifact for team\n" +
                    "4 - See available quests\n\n\n" +
                    "0 - Exit the program\n", userName, rank));
    }

    public void showWallet(long totalMoney, ArrayList<String> namesOfArtifacts){
        System.out.println(CLEAR_CONSOLE);
        System.out.println("WALLET\n");
        System.out.println("Your coolcoins: " + totalMoney);
        System.out.println("\nYour artifacts: ");
        for (String artifact : namesOfArtifacts) {
            System.out.println("- " + artifact);
        }
        System.out.println("\n\nPress enter to continue...");
        getUserInput();
    }

    public void showBuyArtifactMenu(ArrayList<String> namesOfArtifacts, ArrayList<Long> prices, long balance, ArtifactCategory category){
        System.out.println(CLEAR_CONSOLE);
        if (category == ArtifactCategory.Basic){
            System.out.print("BASIC ARTIFACTS SHOP");
        } else {
            System.out.print("MAGIC ARTIFACTS SHOP");
        }
        System.out.println("         YOUR MONEY: " + balance + "cc\n");
        for (String artifact : namesOfArtifacts) {
            int index = namesOfArtifacts.indexOf(artifact);
            System.out.print((index + 1) + ". " + artifact);
            System.out.println("   " + prices.get(index) + "cc");
        }
        System.out.println("\n\n0 - Back");
    }

    public void notEnoughCoolcoins(){
        System.out.println(CLEAR_CONSOLE);
        System.out.println("Not enough coolcoins\nPress enter to continue...");
        getUserInput();
    }

    public void showWrongInput() {
        System.out.println("Your input is wrong.\n");
    }

    public void showQuests(ArrayList<String> quests){
        System.out.println(CLEAR_CONSOLE);
        String[] questInfo = new String[3];
        for (String quest : quests){
            questInfo = quest.split("@");
            String title = questInfo[0];
            String reward = questInfo[1];
            String description = questInfo[2];
            System.out.println(title + "     Reward: " + reward + " coolcoins");
            System.out.println(description + "\n");
        }
        System.out.println("\nPress enter to continue...");
        getUserInput();
    }
}