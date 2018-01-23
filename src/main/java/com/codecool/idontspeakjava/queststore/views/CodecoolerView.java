package com.codecool.idontspeakjava.queststore.views;

import java.util.ArrayList;

public class CodecoolerView extends UserView{

    public void showMainMenu(String userName, String rank) {
        System.out.println(String.format(
                    "%s%sQUESTSTORE\n\n%sNAME: %s              RANK: %s%s\n\n" +
                    "1 - See your wallet\n" +
                    "2 - Buy an artifact\n" +
                    "3 - Buy an artifact for team\n" +
                    "4 - See available quests\n\n\n" +
                    "0 - Exit the program\n",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLACK_BOLD_BRIGHT, userName, rank, Colors.RESET));
    }

    public void showWallet(long totalMoney, long totalEarnings, ArrayList<String> namesOfArtifacts){
        System.out.println(String.format(
                    "%s%sWALLET         %sCurrent state: %s%scc         %sTotal earnings: %s%scc\n%s",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLACK_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT,
                totalMoney, Colors.BLACK_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT,
                totalEarnings, Colors.RESET));
        if (namesOfArtifacts.size() > 0) {
            System.out.println(String.format("%sYour artifacts: %s", Colors.BLACK_BOLD_BRIGHT, Colors.RESET));
            for (String artifact : namesOfArtifacts) {
                System.out.println(String.format("- %s%s%s", Colors.WHITE_BOLD, artifact, Colors.RESET));
            }
        } else {
            System.out.println("No artifacts");
        }
        System.out.println("\n\nPress enter to continue...");
        getUserInput();
    }

    public void showBuyArtifactMenu(ArrayList<String> namesOfArtifacts, ArrayList<Long> prices, long balance){
        System.out.println(String.format(
                    "%s%sBASIC ARTIFACTS SHOP         %sYOUR MONEY: %s%scc\n%s",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLACK_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT,
                balance, Colors.RESET));
        for (String artifact : namesOfArtifacts) {
            int index = namesOfArtifacts.indexOf(artifact);
            System.out.println(String.format(
                        "%s. %s%s   %s%scc%s",
                    index, Colors.WHITE_BOLD, artifact, Colors.YELLOW_BOLD_BRIGHT, prices.get(index), Colors.RESET));
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
        System.out.println(CLEAR_CONSOLE + "Not enough coolcoins\nPress enter to continue...");
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