package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.sql.SQLException;
import java.util.ArrayList;

class CodecoolerCreator {

    private MentorView view;

    CodecoolerCreator(MentorView view) {
        this.view = view;
    }

    private static final int STUDENT_NAME = 0;
    private static final int STUDENT_SECOND_NAME = 1;
    private static final int STUDENT_EMAIL = 2;
    private static final String EXIT = "0";

    void createCodecooler() {
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
                "",
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
}
