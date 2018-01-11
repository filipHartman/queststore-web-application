package com.codecool.idontspeakjava.queststore.views;

public class LoginView extends UserView {

    public void showGreeting() {
        System.out.println(String.format("%sWelcome to %sQuest Store 1.0-SNAPSHOT", Colors.GREEN, Colors.YELLOW));
    }

    public void showBadCredentials() {
        clearScreen();
        System.out.println(String.format("%sYou've typed bad credentials !", Colors.RED));
        super.getUserInput();
    }

    public String getUserLogin() {
        System.out.print(String.format("%sYour email: %s", Colors.BLUE, Colors.RESET));
        return getUserInput();
    }
}
