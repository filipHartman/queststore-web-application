package com.codecool.idontspeakjava.queststore.views;

import java.io.Console;

public class LoginView extends UserView {

    private static final Console console = System.console();

    public void showGreeting() {
        System.out.println(String.format("%sWelcome to %sQuest Store 1.0-SNAPSHOT" +
                "\n%sType 0 or nothing to exit.", Colors.GREEN, Colors.YELLOW, Colors.CYAN_BOLD));
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

    public String getUserPassword() {
        return String.valueOf(console.readPassword(String.format("%sYour password: %s", Colors.BLUE, Colors.RESET)));
    }

    public String getNewUserPassword() {
        System.out.println(String.format("%sPassword requirments:%s \n" +
                        "%s-%s Min. 8-char length\n" +
                        "%s-%s At least one of the following: diggit, upper case, lower case, special character"
                , Colors.GREEN, Colors.RESET, Colors.BLUE, Colors.PURPLE, Colors.BLUE, Colors.PURPLE));
        return String.valueOf(console.readPassword(String.valueOf("%sEnter new password:%s "), Colors.GREEN, Colors.RESET));
    }

    public void showNewPasswordIsIncorrect() {
        clearScreen();
        System.out.println(String.format("%sYour new password does not match requirements !%s", Colors.RED, Colors.RESET));
        getUserInput();
    }
}
