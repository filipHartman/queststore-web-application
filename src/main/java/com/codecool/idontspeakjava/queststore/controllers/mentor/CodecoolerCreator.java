package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

class CodecoolerCreator {
    private static final int STUDENT_NAME = 0;
    private static final int STUDENT_SECOND_NAME = 1;
    private static final int STUDENT_EMAIL = 2;
    private static final int STUDENT_CODECOOL_CLASS = 3;

    private static final String EXIT = "0";
    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
            "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(" +
            "?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]" +
            "?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[" +
            "\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

    private MentorView view;
    private List<CodecoolClass> codecoolClasses;
    private String name;
    private String secondName;
    private String email;
    private CodecoolClass selectedCodecoolClass;

    private Validator validator;

    CodecoolerCreator(MentorView view) {
        this.view = view;
        validator = new Validator();
        codecoolClasses = new SQLiteCodecoolClassDAO().getAllCodecoolClasses();
    }

    void createCodecooler() {
        int inputsReceived = 0;
        final int PROMPTS = 4;
        boolean continueLoop = !codecoolClasses.isEmpty();

        if (!continueLoop) {
            view.showClassesDontExist();
        }
        for (int i = 0; i < PROMPTS && continueLoop; i++) {
            boolean continueIteration = true;
            while (continueIteration) {
                selectPromptForCreateCodecooler(i);
                String input = view.getUserInput();

                if (input.equals(EXIT)) {
                    continueLoop = false;
                    continueIteration = false;
                } else {
                    continueIteration = setAttribute(i, input);
                }
            }
            if (continueLoop) {
                inputsReceived++;
            }
        }
        if (inputsReceived == PROMPTS) {
            addCodecoolerToDatabase();
        } else {
            view.showOperationCancelled();
        }
    }

    private boolean setAttribute(int promptNumber, String input) {
        boolean attributeNotSet = true;
        switch (promptNumber) {
            case STUDENT_NAME:
                if (validator.checkIfNameIsValid(input)) {
                    name = Utilities.capitalizeString(input);
                    attributeNotSet = false;
                } else {
                    view.showWrongInput();
                }
                break;
            case STUDENT_SECOND_NAME:
                if (validator.checkIfNameIsValid(input)) {
                    secondName = Utilities.capitalizeString(input);
                    attributeNotSet = false;
                } else {
                    view.showWrongInput();
                }
                break;
            case STUDENT_EMAIL:
                attributeNotSet = setEmail(input);
                break;
            case STUDENT_CODECOOL_CLASS:
                attributeNotSet = setCodecoolClass(input);
                break;
            default:
                attributeNotSet = false;
        }
        return attributeNotSet;
    }

    private boolean setCodecoolClass(String input) {
        boolean codecoolClassNotSet = true;
        if (input.matches("\\d+")) {
            int indexFromInput = Integer.valueOf(input);
            if (indexFromInput <= codecoolClasses.size()) {
                selectedCodecoolClass = codecoolClasses.get(indexFromInput - 1);
                codecoolClassNotSet = false;
            } else {
                view.showWrongInput();
            }
        } else {
            view.showWrongDigitInput();
        }
        return codecoolClassNotSet;
    }

    private boolean setEmail(String input) {
        boolean emailNotSet = true;
        if (input.matches(EMAIL_REGEX)) {
            try {
                if (new SQLiteUserDAO().checkIfUsersExists(input)) {
                    view.showDuplicateWarning();
                } else {
                    email = input;
                    emailNotSet = false;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                view.showDatabaseError();
            }
        } else {
            view.showWrongEmailInput();
        }
        return emailNotSet;
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
            case STUDENT_CODECOOL_CLASS:
                view.askForCodecoolClass(getClassesTitles());
                break;
        }
    }

    private List<String> getClassesTitles() {
        return codecoolClasses.stream().map(CodecoolClass::getName).collect(Collectors.toList());
    }

    private void addCodecoolerToDatabase() {
        String hash = "";
        User newCodecooler = new User(name, secondName, hash, email, Permissions.Student);
        try {
            new SQLiteUserDAO().createUser(newCodecooler);
            new SQLiteCodecoolClassDAO().addUserToCodecoolClass(newCodecooler, selectedCodecoolClass);
            view.showCodecoolerCreated();
        } catch (SQLException e) {
            e.printStackTrace();
            view.showCodecoolerCreationFailed();
        }
    }
}
