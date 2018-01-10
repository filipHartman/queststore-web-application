package com.codecool.idontspeakjava.queststore.views;

import java.util.Scanner;

public abstract class UserView {

    private Scanner scanner;

    public UserView() {
        scanner = new Scanner(System.in);
    }

    public String getUserInput() {
        return scanner.nextLine();
    }

    private void showUser(String user) {

    }
}