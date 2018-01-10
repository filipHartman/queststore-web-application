package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.sql.SQLException;
import java.util.ArrayList;

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

    private static final int STUDENT_NAME = 0;
    private static final int STUDENT_SECOND_NAME = 1;
    private static final int STUDENT_EMAIL = 2;

    public MentorController() {
        view = new MentorView();
        mentor = new User("Henryk", "Pryk", "ahbgd", "h.pryk@email.com", Permissions.Mentor);
    }

    public void run() {
        boolean runProgram = true;

        while (runProgram) {
            view.showMainMenu(mentor.getFirstName());
            runProgram = selectAction(view.getUserInput());
        }
    }

    private boolean selectAction(String input) {
        boolean continueRunning = true;
        switch (input) {
            case CREATE_CODECOOLER:
                createCodecooler();
                break;
            case ADD_QUEST:
                addQuest();
                break;
            case ADD_ARTIFACT:
                addArtifact();
                break;
            case EDIT_QUEST:
                editQuest();
                break;
            case EDIT_ARTIFACT:
                editArtifact();
                break;
            case MARK_QUEST:
                markQuest();
                break;
            case MARK_ARTIFACT:
                markArtifact();
                break;
            case CHECK_WALLETS:
                checkWallets();
                break;
            case EXIT:
                continueRunning = false;
                break;
            default:
                view.showWrongInput();
        }
        return continueRunning;
    }

    private void createCodecooler() {
        int inputsReceived = 0;
        boolean continueLoop = true;

        final int PROMPTS = 3;

        ArrayList<String> userInputs = new ArrayList<>();

        for (int i = 0; i < PROMPTS && continueLoop; i++) {
            selectPromptForCreateCodecooler(i);
            String input = view.getUserInput();

            if (input.equals(EXIT)) {
                continueLoop = false;
            } else {
                userInputs.add(input);
                inputsReceived++;
            }
        }

        if (inputsReceived == PROMPTS) {
            addCodecoolerToDatabase(userInputs);
        } else {
            view.showOperationCancelled();
        }
    }

    private void selectPromptForCreateCodecooler(int promptNumber) {
        switch (promptNumber) {
            case STUDENT_NAME:
                view.askForCodecoolerName();
                break;
            case STUDENT_SECOND_NAME:
                view.askForSecondName();
                break;
            case STUDENT_EMAIL:
                view.askForEmail();
                break;
        }
    }

    private void addCodecoolerToDatabase(ArrayList<String> userData) {
        User newCodecooler = new User(
                userData.get(STUDENT_NAME),
                userData.get(STUDENT_SECOND_NAME),
                "HASHTOPASS",
                userData.get(STUDENT_EMAIL),
                Permissions.Student);

        try {
            new UserDAO().createUser(newCodecooler);
            view.showCodecoolerCreated();
        } catch (SQLException e) {
            e.printStackTrace();
            view.showCodecoolerCreationFailed();
        }
    }

    private void addQuest() {

    }

    private void addArtifact() {

    }

    private void editQuest() {

    }

    private void editArtifact() {

    }

    private void markQuest() {

    }

    private void markArtifact() {

    }

    private void checkWallets() {

    }
}
