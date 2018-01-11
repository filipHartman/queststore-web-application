package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    private String hash = "";

    CodecoolerCreator(MentorView view) {
        this.view = view;
        codecoolClasses = new CodecoolClassDAO().getAllCodecoolClasses();
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
        boolean attributeNotSet;
        switch (promptNumber) {
            case STUDENT_NAME:
                attributeNotSet = setName(input);
                break;
            case STUDENT_SECOND_NAME:
                attributeNotSet = setSecondName(input);
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
        if (input.matches("[a-zA-Z@.]+")) {
            email = input;
            emailNotSet = false;
        } else {
            view.showWrongEmailInput();
        }
        return emailNotSet;
    }

    private boolean setName(String input) {
        boolean nameNotSet = true;
        if (input.matches("[a-zA-Z]+")) {
            name = capitalizeString(input);
            nameNotSet = false;
        } else {
            view.showWrongNameInput();
        }
        return nameNotSet;
    }

    private boolean setSecondName(String input) {
        boolean nameNotSet = true;
        if (input.matches("[a-zA-Z]+")) {
            secondName = capitalizeString(input);
            nameNotSet = false;
        } else {
            view.showWrongNameInput();
        }
        return nameNotSet;
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

    private ArrayList<String> getClassesTitles() {
        ArrayList<String> titles = new ArrayList<>();
        Iterator<CodecoolClass> iterator = codecoolClasses.iterator();
        for (; iterator.hasNext(); ) {
            titles.add(iterator.next().getName());
        }
        return titles;
    }

    private void addCodecoolerToDatabase() {
        User newCodecooler = new User(name, secondName, hash, email, Permissions.Student);
        try {
            new UserDAO().createUser(newCodecooler);
            new CodecoolClassDAO().addUserToCodecoolClass(newCodecooler, selectedCodecoolClass);
            view.showCodecoolerCreated();
        } catch (SQLException e) {
            e.printStackTrace();
            view.showCodecoolerCreationFailed();
        }
    }

    private String capitalizeString(String stringToCapitalize) {
        char[] splittedString = stringToCapitalize.toLowerCase().toCharArray();
        splittedString[0] = Character.toUpperCase(splittedString[0]);
        return new String(splittedString);
    }
}
