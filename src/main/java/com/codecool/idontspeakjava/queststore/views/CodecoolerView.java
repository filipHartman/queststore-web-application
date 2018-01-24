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

    public void showBuyArtifactMenu(ArrayList<String> namesOfArtifacts, ArrayList<Integer> prices, long balance){
        System.out.println(String.format(
                    "%s%sBASIC ARTIFACTS SHOP         %sYOUR MONEY: %s%scc\n%s",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLACK_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT,
                balance, Colors.RESET));
        for (String artifact : namesOfArtifacts) {
            int index = namesOfArtifacts.indexOf(artifact);
            System.out.println(String.format(
                        "%s. %s%s   %s%scc%s",
                    index + 1, Colors.WHITE_BOLD, artifact, Colors.YELLOW_BOLD_BRIGHT, prices.get(index), Colors.RESET));
        }
        System.out.println("\n\n0 - Back");
    }

    public void showBuyTeamArtifactMenu(ArrayList<String> namesOfArtifacts, ArrayList<Long> collected, ArrayList<Long> prices, long balance){
        System.out.println(String.format(
                    "%s%sMAGIC ARTIFACTS SHOP         %sYOUR MONEY: %s%scc\n%s",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLACK_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT,
                balance, Colors.RESET));
        for (String artifact : namesOfArtifacts) {
            int index = namesOfArtifacts.indexOf(artifact);
            System.out.println(String.format(
                        "%s. %s%s   %s%scc%s/%s%scc%s",
                    index + 1, Colors.WHITE_BOLD, artifact, Colors.YELLOW_BOLD_BRIGHT, prices.get(index), Colors.RESET,
                    Colors.YELLOW_BOLD_BRIGHT, collected.get(index), Colors.RESET));
        }
        System.out.println("\n\n0 - Back");
    }

    public void showQuests(ArrayList<String> quests){
        System.out.println(CLEAR_CONSOLE + Colors.GREEN_BOLD_BRIGHT + "\nTAVERN\n" + Colors.RESET);
        String[] questInfo;
        for (String quest : quests){
            questInfo = quest.split("@");
            String title = questInfo[0], reward = questInfo[1], description = questInfo[2];
            System.out.println(String.format(
                        "%s%s     %sReward: %s%scc\n%s%s\n",
                    Colors.WHITE_BOLD, title, Colors.BLACK_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT, reward,
                    Colors.RESET, description));
        }
        System.out.println("\nPress enter to continue...");
        getUserInput();
    }

    public void notEnoughCoolcoins(){
        System.out.println(CLEAR_CONSOLE + "Not enough coolcoins\nPress enter to continue...");
        getUserInput();
    }

    public int askForContribution(){
        System.out.println(CLEAR_CONSOLE + "How many do you want to pay?");
        String input = getUserInput();
        if (input.matches("\\d+")) {
            return Integer.parseInt(input);
        }
        return 0;
    }

    public void showWrongInput() {
        System.out.println("Your input is wrong.\n");
    }
}