package com.codecool.idontspeakjava.queststore.views;

import java.util.ArrayList;

import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;

public class CodecoolerView extends UserView{

    public void showMainMenu(String userName, String rank) {
    System.out.println(CLEAR_CONSOLE);
    System.out.println(String.format(
            "%sQUESTSTORE\n\n%sNAME: %s              RANK: %s\n\n" +
                    "%s1 - See your wallet\n" +
                    "2 - Buy an artifact\n" +
                    "3 - Buy an artifact for team\n" +
                    "4 - See available quests\n\n\n" +
                    "0 - Exit the program\n", Colors.GREEN_BOLD_BRIGHT, Colors.BLACK_BOLD_BRIGHT, userName, rank, Colors.RESET));
    }

    public void showWallet(long totalMoney, ArrayList<String> namesOfArtifacts){
        System.out.println(CLEAR_CONSOLE);
        System.out.print(Colors.GREEN_BOLD_BRIGHT + "WALLET         ");
        System.out.println(Colors.BLACK_BOLD_BRIGHT + "Your coolcoins: " + Colors.YELLOW_BOLD_BRIGHT + totalMoney + Colors.BLACK_BOLD_BRIGHT);
        System.out.println("\nYour artifacts: " + Colors.CYAN_BOLD_BRIGHT);
        for (String artifact : namesOfArtifacts) {
            System.out.println("- " + artifact);
        }
        System.out.println(Colors.RESET + "\n\nPress enter to continue...");
        getUserInput();
    }

    public void showBuyArtifactMenu(ArrayList<String> namesOfArtifacts, ArrayList<Long> prices, long balance){
        System.out.println(CLEAR_CONSOLE);
        System.out.print(Colors.GREEN_BOLD_BRIGHT + "BASIC ARTIFACTS SHOP" + Colors.BLACK_BOLD_BRIGHT);
        System.out.println("         YOUR MONEY: " + Colors.YELLOW_BOLD_BRIGHT + balance + "cc\n" + Colors.RESET);
        for (String artifact : namesOfArtifacts) {
            int index = namesOfArtifacts.indexOf(artifact);
            System.out.print((index + 1) + ". " + Colors.CYAN_BOLD_BRIGHT + artifact);
            System.out.println("   " + Colors.YELLOW_BOLD_BRIGHT + prices.get(index) + "cc" + Colors.RESET);
        }
        System.out.println("\n\n0 - Back");
    }

    public void showBuyTeamArtifactMenu(ArrayList<String> namesOfArtifacts, ArrayList<Long> collected, ArrayList<Long> prices, long balance){
        System.out.println(CLEAR_CONSOLE);
        System.out.print(Colors.GREEN_BOLD_BRIGHT + "MAGIC ARTIFACTS SHOP" + Colors.BLACK_BOLD_BRIGHT);
        System.out.println("         YOUR MONEY: " + Colors.YELLOW_BOLD_BRIGHT + balance + "cc\n" + Colors.RESET);
        for (String artifact : namesOfArtifacts) {
            int index = namesOfArtifacts.indexOf(artifact);
            System.out.print((index + 1) + ". " + Colors.CYAN_BOLD_BRIGHT + artifact);
            System.out.println("   " + Colors.YELLOW_BOLD_BRIGHT + collected.get(index) + Colors.RESET + "/" + Colors.YELLOW_BOLD_BRIGHT + prices.get(index) + "cc" + Colors.RESET);
        }
        System.out.println("\n\n0 - Back");
    }

    public void notEnoughCoolcoins(){
        System.out.println(CLEAR_CONSOLE);
        System.out.println("Not enough coolcoins\nPress enter to continue...");
        getUserInput();
    }

    public int askForContribution(){
        System.out.println(CLEAR_CONSOLE);
        System.out.println("How may you want to pay?");
        String input = getUserInput();
        if (input.matches("\\d+")) {
            return Integer.parseInt(input);
        }
        return 0;
    }

    public void showWrongInput() {
        System.out.println("Your input is wrong.\n");
    }

    public void showQuests(ArrayList<String> quests){
        System.out.println(CLEAR_CONSOLE + Colors.GREEN_BOLD_BRIGHT + "\nTAVERN\n" + Colors.RESET);
        String[] questInfo = new String[3];
        for (String quest : quests){
            questInfo = quest.split("@");
            String title = questInfo[0];
            String reward = questInfo[1];
            String description = questInfo[2];
            System.out.println(Colors.CYAN_BOLD_BRIGHT + title + Colors.RESET + "     Reward: " + Colors.YELLOW_BOLD_BRIGHT + reward + " coolcoins");
            System.out.println(Colors.RESET + description + "\n");
        }
        System.out.println("\nPress enter to continue...");
        getUserInput();
    }
}