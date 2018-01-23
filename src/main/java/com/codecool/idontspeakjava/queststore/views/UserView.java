package com.codecool.idontspeakjava.queststore.views;

import java.util.Scanner;

public abstract class UserView {

    static final String CLEAR_CONSOLE = "\033\143";

    private Scanner scanner;

    UserView() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    void continuePrompt() {
        System.out.println(String.format("%sHit enter to continue.\n", Colors.PURPLE_BOLD));
        getUserInput();
    }

    public void clearScreen() {
        System.out.print(CLEAR_CONSOLE);
    }
}