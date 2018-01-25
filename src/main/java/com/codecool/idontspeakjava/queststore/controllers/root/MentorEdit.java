package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.User;
import java.sql.SQLException;

class MentorEdit {
    private static final int SELECT_MENTOR = 0;
    private static final int SELECT_EMAIL = 1;
    private static final int SELECT_CLASS = 2;
    private static final String EXIT = "0";

    private User selectedMentor;
    private RootView view;
    private CodecoolClass selectedClass;

    MentorEdit(RootView view) {
        this.view = view;
    }

    void editMentor() {
        boolean loopContinuation = true;
        int userInputs = 0;
        final int prompts = 3;
        for (int i = 0; i < prompts && loopContinuation; i++) {
            boolean mentorNotAssign = true;
            while (mentorNotAssign) {
                selectInfoForPrompt(i);
                String input = view.getUserInput();
                view.clearScreen();

                if (input.equals(EXIT)) {
                    loopContinuation = false;
                    mentorNotAssign = false;
                } else {
                    mentorNotAssign = selectPrompt(i, input);
                }
            }
            if (loopContinuation) {
                userInputs++;
            }
        }if(userInputs==prompts){
            new UserDAO().updateUser(selectedMentor);
            new CodecoolClassDAO().addUserToCodecoolClass(selectedMentor, selectedClass);
            view.showMentorUpdate();
        }else{
            view.showOperationCancelled();
        }
    }

    private void selectInfoForPrompt(int promptInfo) {
        switch (promptInfo) {
            case SELECT_MENTOR:
                view.showAllMentors();
                view.askForMentorEmailInput();
                break;
            case SELECT_EMAIL:
                view.askForMentorEmailToEdit();
                break;
            case SELECT_CLASS:
                view.askForMentorClassToEdit();
                break;
        }
    }

    private boolean selectPrompt(int prompt, String input){
        boolean promptNotSet = true;
        switch(prompt){
            case SELECT_MENTOR:
                promptNotSet = selectMentor(input);
                break;
            case SELECT_EMAIL:
                promptNotSet = editEmail(input);
                break;
            case SELECT_CLASS:
                promptNotSet = editClass(input);
        }
        return promptNotSet;
    }


    private boolean selectMentor(String input){
        boolean mentorNotSelected = true;
        try{
            if(new UserDAO().checkIfUsersExists(input)) {
                selectedMentor = new UserDAO().getUserByEmail(input);
                mentorNotSelected = false;
            }else{
                view.showWrongEmailInput();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            view.showDatabaseError();
        }
        return mentorNotSelected;
    }

    private boolean editEmail(String input){
        boolean emailNotChanged = true;
        String upperInput = input.toUpperCase();
        if(upperInput.matches("Y")){
            view.askForMentorNewEmail();
            String mentorEmailInput = view.getUserInput();
            emailNotChanged = setEmail(mentorEmailInput);
        }else if (upperInput.matches("N")){
            emailNotChanged = false;
        }else{
            view.showWrongAnswer();
        }
        return emailNotChanged;
    }


    private boolean setEmail(String input) {
        boolean emailNotSet = true;
        if (input.matches("[a-zA-Z0-9@.]+")) {
            try {
                if (new UserDAO().checkIfUsersExists(input)) {
                    view.showExistingValueWarning();
                } else {
                    selectedMentor.setEmail(input);
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

    private boolean editClass(String input){
        boolean classNotChanged = true;
        String upperInput = input.toUpperCase();
        if(upperInput.matches("Y")){
            view.showAllClasses();
            view.askForMentorNewClass();
            String mentorClassInput = view.getUserInput();
            setClass(mentorClassInput);
            classNotChanged = false;
        }else if (upperInput.matches("N")){
            classNotChanged = false;
        }else{
            view.showWrongAnswer();
        }
        return classNotChanged;
    }

    private boolean setClass(String input){
        boolean classNotSet = true;
        try{
            if(new CodecoolClassDAO().checkIfClassExists(input)){
                    new CodecoolClassDAO().removeUserFromCodecoolClass(selectedMentor);
                    selectedClass = new CodecoolClassDAO().getCodecoolClass(input); 
                    classNotSet = false;
            }else{
                view.showClassNotExist();
            }
        }catch (SQLException e) {
            e.printStackTrace();
            view.showDatabaseError();
        }
        return classNotSet;
    }
}