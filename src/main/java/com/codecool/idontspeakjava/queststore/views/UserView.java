package com.codecool.idontspeakjava.queststore.views;

import java.util.Scanner;

public abstract class UserView {

    static final String CLEAR_CONSOLE = "\033\143";

    private Scanner scanner;

    public UserView() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    private void showUser(String user) {

    }

    void continuePrompt() {
        System.out.println("Hit enter to continue.\n");
        getUserInput();
    }

    public void clearScreen() {
        System.out.println(CLEAR_CONSOLE);
    }

}