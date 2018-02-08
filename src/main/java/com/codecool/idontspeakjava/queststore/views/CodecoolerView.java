package com.codecool.idontspeakjava.queststore.views;

import java.lang.reflect.Array;
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
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLUE_BOLD_BRIGHT, userName, rank, Colors.RESET));
    }

    public void showManageTeamMenu(String userName, String team) {
        System.out.println(String.format(
                "%s%sQUESTSTORE\n\n%sNAME: %s              TEAM: %s%s\n\n" +
                        "1 - Create new team\n" +
                        "2 - Join to existing team\n" +
                        "3 - Leave your team\n\n\n" +
                        "0 - Back to main menu\n",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLUE_BOLD_BRIGHT, userName, team, Colors.RESET));
    }

    public void showWallet(long totalMoney, long totalEarnings, ArrayList<String> namesOfArtifacts, ArrayList<String> usedArtifacts){
        System.out.println(String.format(
                    "%s%sWALLET         %sCurrent state: %s%scc         %sTotal earnings: %s%scc\n%s",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, Colors.BLUE_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT,
                totalMoney, Colors.BLUE_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT,
                totalEarnings, Colors.RESET));
        if (namesOfArtifacts.size() > 0) {
            System.out.println(String.format("%sYour artifacts: %s", Colors.BLUE_BOLD_BRIGHT, Colors.RESET));
            for (String artifact : namesOfArtifacts) {
                System.out.println(String.format("- %s%s%s", Colors.WHITE_BOLD, artifact, Colors.RESET));
            }
        } else {
            System.out.println("No artifacts");
        }
        if (usedArtifacts.size() > 0) {
            System.out.println(String.format("\n%sUsed artifacts: %s", Colors.BLUE_BOLD_BRIGHT, Colors.RESET));
            for (String artifact : usedArtifacts) {
                System.out.print(String.format("| %s%s%s ", Colors.WHITE_BOLD, artifact, Colors.RESET));
            }
        }
        System.out.println("\n\nPress enter to continue...");
        getUserInput();
    }

    public void showBuyArtifactMenu(ArrayList<String> artifactsInfo, long balance){
        String category = (artifactsInfo.get(0).split("@").length > 3) ? "MAGIC" : "BASIC";
        System.out.println(String.format(
                    "%s%s%s ARTIFACTS SHOP         %sYOUR MONEY: %s%scc\n%s",
                CLEAR_CONSOLE, Colors.GREEN_BOLD_BRIGHT, category, Colors.BLUE_BOLD_BRIGHT, Colors.YELLOW_BOLD_BRIGHT,
                balance, Colors.RESET));
        ArrayList<Integer> maxLengths = getMaxStringsLength(artifactsInfo);
        for (String artifact : artifactsInfo) {
            String title = artifact.split("@")[0] + getSpaces(maxLengths.get(0) - artifact.split("@")[0].length());
            title = category.equals("MAGIC") ? Colors.RED_BOLD_BRIGHT + title : title;
            String description = Colors.RESET + artifact.split("@")[1] + getSpaces(maxLengths.get(1) - artifact.split("@")[1].length());
            String price = artifact.split("@")[2];
            String collected = category.equals("MAGIC") ?
                    artifact.split("@")[3] + Colors.RESET + "/" + Colors.YELLOW_BOLD_BRIGHT : "";
            int index = artifactsInfo.indexOf(artifact);
            String row = "%s. %s%s   %s    %s%s%scc%s";
            if (collected.equals("IN WALLET" + Colors.RESET + "/" + Colors.YELLOW_BOLD_BRIGHT)) {
                collected = "    IN WALLET" + Colors.RESET;
                row = "%s. %s%s   " + description + Colors.GREEN_BOLD_BRIGHT + collected;
                price = "";
            }
            System.out.println(String.format(
                        row,
                    index + 1, Colors.WHITE_BOLD, title, description, Colors.YELLOW_BOLD_BRIGHT, collected, price, Colors.RESET));
        }
        System.out.println("\n\n0 - Back");
    }

    private ArrayList<Integer> getMaxStringsLength(ArrayList<String> strings) {
        int maxFirst = 0;
        int maxSecond = 0;
        int maxThird = 0;
        for (String string : strings ) {
            if (string.split("@")[0].length() > maxFirst) maxFirst = string.split("@")[0].length();
            if (string.split("@")[1].length() > maxSecond) maxSecond = string.split("@")[1].length();
            if (string.split("@")[2].length() > maxThird) maxThird = string.split("@")[2].length();
        }
        ArrayList<Integer> result = new ArrayList<>();
        result.add(maxFirst);
        result.add(maxSecond);
        result.add(maxThird);
        return result;
    }

    private String getSpaces(int amount) {
        return new String(new char[amount]).replace("\0", " ");
    }

    public void showQuests(ArrayList<String> quests){
        System.out.println(CLEAR_CONSOLE + Colors.GREEN_BOLD_BRIGHT + "\nTAVERN\n" + Colors.RESET);
        String[] questInfo;
        ArrayList<Integer> maxLengths = getMaxStringsLength(quests);
        for (String quest : quests){
            questInfo = quest.split("@");
            String title = questInfo[0] + getSpaces(maxLengths.get(0) - questInfo[0].length());
            String reward = questInfo[1];
            String description = questInfo[2] + getSpaces(maxLengths.get(2) - questInfo[2].length());
            System.out.println(String.format(
                        "%s%s " + getSpaces(maxLengths.get(2) - title.length()) + " %sReward: %s%scc\n%s%s\n",
                    Colors.BLUE_BOLD_BRIGHT, title, Colors.BLUE_BRIGHT, Colors.YELLOW_BOLD_BRIGHT, reward,
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
