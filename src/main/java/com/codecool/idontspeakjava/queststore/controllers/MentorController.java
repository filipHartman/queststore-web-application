package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.MentorView;

public class MentorController {
    private MentorView view;
    private User mentor;

    private static final String CREATE_CODECOOLER = "1";
    private static final String ADD_QUEST = "2";
    private static final String ADD_ARTIFACT = "3";
    private static final String EDIT_QUEST = "4";
    private static final String EDIT_ARTIFACT = "5";
    private static final String MARK_QUEST = "6";
    private static final String MARK_ARTIFACT = "7";
    private static final String CHECK_WALLETS = "8";
    private static final String EXIT = "0";

    public MentorController() {
        view = new MentorView();
        mentor = new User("Henryk", "Pryk", "ahbgd", "h.pryk@email.com", Permissions.Mentor);
    }

    public void run() {
        boolean runProgram = true;

        while (runProgram) {
            view.showMainMenu();
            runProgram = selectAction(view.getUserInput());
        }
    }

    private boolean selectAction(String input) {
        boolean continueRunning = true;
        return continueRunning;
    }
}
