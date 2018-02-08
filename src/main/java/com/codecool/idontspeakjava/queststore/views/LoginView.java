package com.codecool.idontspeakjava.queststore.views;

import java.io.Console;

public class LoginView extends UserView {

    private static final Console console = System.console();

    public void showGreeting() {
        System.out.print(Colors.YELLOW);
        printTable(new String[][]{{"Welcome to Quest Store 1.0-SNAPSHOT"}});
        System.out.print(Colors.CYAN_BOLD);
        printTable(new String[][]{{"Enter 0 or hit enter to exit"}});
    }

    public void showBadCredentials() {
        clearScreen();
        System.out.print(Colors.RED);
        printTable(new String[][]{{"You have entered invalid credentials"}});
        super.getUserInput();
    }

    public String getUserLogin() {
        System.out.print(Colors.BLUE_BRIGHT);
        printTable(new String[][]{{"Enter your email"}});
        return getUserInput();
    }

    public String getUserPassword() {
        System.out.print(Colors.BLUE_BRIGHT);
        printTable(new String[][]{{"Enter your password"}});
        return String.valueOf(console.readPassword());
    }

    public String getNewUserPassword() {
        System.out.print(Colors.GREEN);
        printTable(new String[][]{{"Enter your new password. Requirements are :"}});
        System.out.print(Colors.BLUE_BRIGHT);
        printTable(new String[][]{{"Minimum 8 characters"}});
        System.out.print(Colors.PURPLE_BRIGHT);
        printTable(new String[][]{{"At least one : digit, upper and lower case letters and special character"}});
        return String.valueOf(console.readPassword());
    }

    public void showNewPasswordIsIncorrect() {
        clearScreen();
        System.out.print(Colors.RED);
        printTable(new String[][]{{"Your new password does not match requirements !"}});
        getUserInput();
    }
}
