package com.codecool.idontspeakjava.queststore.views;

import java.util.ArrayList;
import java.util.List;

public class CodecoolerView extends UserView{

    public void showMainMenu(String userName, String rank) {
        System.out.println(String.format(
                    "%s%sQUESTSTORE\n\n%sNAME: %s              RANK: %s%s\n\n" +
                    "1 - See your wallet\n" +
                    "2 - Buy an artifact\n" +
                    "3 - Buy an artifact for team\n" +
                            "4 - See available quests\n" +
                            "5 - Manage your team\n\n\n" +
                    "0 - Exit the program\n",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLACK_BOLD_BRIGHT, userName, rank, Colors.RESET));
    }

    public void showManageTeamMenu(String userName, String team) {
        System.out.println(String.format(
                "%s%sQUESTSTORE\n\n%sNAME: %s              TEAM: %s%s\n\n" +
                        "1 - Create new team\n" +
                        "2 - Join to existing team\n" +
                        "3 - Leave your team\n\n\n" +
                        "0 - Back to main menu\n",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLACK_BOLD_BRIGHT, userName, team, Colors.RESET));
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

    public void showBuyArtifactMenu(ArrayList<String> artifactsInfo, long balance){
        String category = (artifactsInfo.get(0).split("@").length > 2) ? "MAGIC" : "BASIC";
        System.out.println(String.format(
                    "%s%s%s ARTIFACTS SHOP         %sYOUR MONEY: %s%scc\n%s",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, category, Colors.BLACK_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT,
                balance, Colors.RESET));
        for (String artifact : artifactsInfo) {
            String title = category.equals("MAGIC") ? Colors.PURPLE_BOLD_BRIGHT + artifact.split("@")[0] : artifact.split("@")[0];
            String price = artifact.split("@")[1];
            String collected = category.equals("MAGIC") ?
                    artifact.split("@")[2] + Colors.RESET + "/" + Colors.YELLOW_BOLD_BRIGHT : "";
            int index = artifactsInfo.indexOf(artifact);
            String row = "%s. %s%s   %s%s%scc%s";
            if (collected.equals("IN WALLET" + Colors.RESET + "/" + Colors.YELLOW_BOLD_BRIGHT)) {
                collected = "IN WALLET" + Colors.RESET;
                row = "%s. %s%s   " + Colors.GREEN_BOLD_BRIGHT + collected;
                price = "";
            }
            System.out.println(String.format(
                        row,
                    index + 1, Colors.WHITE_BOLD, title, Colors.YELLOW_BOLD_BRIGHT, collected, price, Colors.RESET));
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
        clearScreen();
        System.out.println("Your input is wrong.\n");
    }

    public void showThatUserIsAlreadyInTeam() {
        clearScreen();
        System.out.println(String.format("%sYou already in team !", Colors.RED_BOLD));
        continuePrompt();
    }

    public void showThatUserIsNotInTeam() {
        clearScreen();
        System.out.println(String.format("%sYou are not in any team !", Colors.RED_BOLD));
        continuePrompt();
    }

    public void showThatTeamAlreadyExists() {
        clearScreen();
        System.out.println(String.format("%sSuch team already exists !", Colors.RED_BOLD));
        continuePrompt();

    }

    public void showNewTeamHaveBeenCreatedSuccessfully(String teamName) {
        clearScreen();
        System.out.printf("%sNew team %s %s %s have been created successfully!\n",
                Colors.YELLOW, Colors.GREEN_BOLD, teamName, Colors.YELLOW);
        continuePrompt();
    }

    public void showTeamsList(List<String> teams) {
        teams.forEach(System.out::println);
    }

    public void showNoTeamsInDatabase() {
        clearScreen();
        System.out.println(String.format("%sThere is no team in database avaible !", Colors.RED_BOLD));
        continuePrompt();
    }

    public void showUserHavenBeenRemovedFromTeamSuccessfully(String teamName) {
        clearScreen();
        System.out.printf("%sYou've been removed from %s %s %s successfully!\n",
                Colors.YELLOW, Colors.GREEN_BOLD, teamName, Colors.YELLOW);
        continuePrompt();
    }

    public void showUserHavenAddedToTeamSuccessfully(String teamName) {
        clearScreen();
        System.out.printf("%sYou've been added to %s %s %s team successfully!\n",
                Colors.YELLOW, Colors.GREEN_BOLD, teamName, Colors.YELLOW);
        continuePrompt();
    }
}
