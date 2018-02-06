package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.RootView;

import java.sql.SQLException;

class MentorCreator{
    private static final int MENTOR_NAME = 0;
    private static final int MENTOR_LAST_NAME = 1;
    private static final int MENTOR_EMAIL = 2;
    private static final String EXIT = "0";

    private RootView view;
    private InputValidation validation;
    private String name;
    private String lastName;
    private String email;
    private String hash = "";

    MentorCreator(RootView view){
        this.view = view;
        validation = new InputValidation(view);
    }

    void createMentor(){
        boolean continueLoop = true;
        int userInputs = 0;
        final int prompts = 3;

        for (int i = 0; i < prompts && continueLoop; i++){
            boolean mentorNotDone = true;
            while (mentorNotDone){
                selectMentorFeature(i);
                String input = view.getUserInput();

                if (input.equals(EXIT)){
                    continueLoop = false;
                    mentorNotDone = false;
                }else{
                    mentorNotDone = setAttribute(i, input);
                }
            }
            if(continueLoop){
                userInputs++;
            }
        }
        if (userInputs == prompts) {
            addMentorToDatabase();
        }else{
            view.showOperationCancelled();
        }
    }

    private boolean setAttribute(int promptNumber, String input) {
        boolean attributeNotSet;
        switch (promptNumber) {
            case MENTOR_NAME:
                attributeNotSet = setName(input);
                break;
            case MENTOR_LAST_NAME:
                attributeNotSet = setLastName(input);
                break;
            case MENTOR_EMAIL:
                attributeNotSet = setEmail(input);
                break;
            default:
                attributeNotSet = false;
        }
        return attributeNotSet;
    }

    private boolean setName(String input) {
        boolean nameNotSet = true;
        if(validation.checkName(input)){
            name = capitalizeFirstLetter(input);
            nameNotSet = false;
        }
        return nameNotSet;
    }

    private boolean setLastName(String input) {
        boolean lastNameNotSet = true;
        if (validation.checkEmail(input)) {
            lastNameNotSet = false;
        }
        return lastNameNotSet;
    }

    private boolean setEmail(String input) {
        boolean emailNotSet = true;
        if(validation.checkEmail(input)) {
            email = input;
            emailNotSet = false;
        }
        return emailNotSet;
    }

    private void addMentorToDatabase() {
        User newMentor = new User(name, lastName, hash, email, Permissions.Mentor);
        try {
            new SQLiteUserDAO().createUser(newMentor);
            view.showMentorCreated();
        } catch (SQLException e) {
            e.printStackTrace();
            view.showMentorCreationFailed();
        }
    }

    private void selectMentorFeature(int mentorFeature) {
        switch (mentorFeature) {
            case MENTOR_NAME:
                view.askForMentorName();
                break;
            case MENTOR_LAST_NAME:
                view.askForMentorLastName();
                break;
            case MENTOR_EMAIL:
                view.askForMentorEmail();
                break;
        }
    }

    private String capitalizeFirstLetter(String stringToCapitalize) {
        char[] splittedString = stringToCapitalize.toLowerCase().toCharArray();
        splittedString[0] = Character.toUpperCase(splittedString[0]);
        return new String(splittedString);
    }
}