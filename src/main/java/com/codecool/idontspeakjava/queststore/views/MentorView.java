package com.codecool.idontspeakjava.queststore.views;


public class MentorView extends UserView {

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

    public void showWrongInput() {
        System.out.println("Your input is wrong.\n");
    }

    public void showCodecoolerCreated() {
        System.out.println("You created a new codecooler.\n");
    }

    public void showCodecoolerCreationFailed() {
        System.out.println("An error occured. The new codecooler wasn't created.\n");
    }

    public void showOperationCancelled() {
        System.out.println("Operation cancelled.\n");
    }

    public void askForCodecoolerName() {
        System.out.println("Enter the name of codecooler's or 0 to cancel.\n");
    }

    public void askForSecondName() {
        System.out.println("Enter the second name of codecooler's or 0 to cancel.\n");
    }

    public void askForEmail() {
        System.out.println("Enter the codecooler's email or 0 to cancel.\n");
    }
}
