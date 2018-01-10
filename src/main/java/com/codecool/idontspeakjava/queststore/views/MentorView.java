package com.codecool.idontspeakjava.queststore.views;


public class MentorView extends UserView {

    public void showMainMenu() {
        System.out.println("Select what you want to do:\n" +
                "1 - Create new codecooler\n" +
                "2 - Add new quest\n" +
                "3 - Add new artifact\n" +
                "4 - Edit existing quest\n" +
                "5 - Edit existing artifact\n" +
                "6 - Mark quest for a codecooler\n" +
                "7 - Mark artifact for a codecooler\n" +
                "8 - Check the wallets of codecoolers\n" +
                "0 - \n");
    }
}
