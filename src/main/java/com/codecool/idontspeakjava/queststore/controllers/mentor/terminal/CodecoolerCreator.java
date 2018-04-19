package com.codecool.idontspeakjava.queststore.controllers.mentor.terminal;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

class CodecoolerCreator {
    private static final int STUDENT_NAME = 0;
    private static final int STUDENT_SECOND_NAME = 1;
    private static final int STUDENT_EMAIL = 2;
    private static final int STUDENT_CODECOOL_CLASS = 3;

    private static final String EXIT = "0";

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
                if (validator.checkIfEmailIsValid(input, getUsersEmails())) {
                    email = input;
                    attributeNotSet = false;
                } else {
                    view.showWrongEmailInput();
                }
                break;
            case STUDENT_CODECOOL_CLASS:
                if (!validator.isSelectFromListInvalid(codecoolClasses, input)) {
                    selectedCodecoolClass = codecoolClasses.get(Integer.parseInt(input) - 1);
                    attributeNotSet = false;
                } else {
                    view.showWrongInput();
                }
                break;
            default:
                attributeNotSet = false;
        }
        return attributeNotSet;
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

    private List<String> getUsersEmails() {
        return new SQLiteUserDAO().getAllUsers().stream().map(User::getEmail).collect(Collectors.toList());
    }

    private void addCodecoolerToDatabase() {
        String hash = "";
        User newCodecooler = new User(name, secondName, hash, email, Permissions.Student);
        
        if(new SQLiteUserDAO().createUser(newCodecooler)) {
            new SQLiteCodecoolClassDAO().addUserToCodecoolClass(newCodecooler, selectedCodecoolClass);
            view.showCodecoolerCreated();
        }else {
            view.showCodecoolerCreationFailed();
        }

    }
}
