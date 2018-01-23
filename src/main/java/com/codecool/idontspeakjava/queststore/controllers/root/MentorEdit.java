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
    private CodecoolClassDAO classDAO;

    MentorEdit(RootView view) {
        this.view = view;
        CodecoolClassDAO classDAO = new CodecoolClassDAO();
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
            updateMentorEmail();
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
        boolean promptNotSet;
        switch(prompt){
            case SELECT_MENTOR:
                promptNotSet = selectMentor(input);
                break;
            case SELECT_EMAIL:
                promptNotSet = editEmail(input);
                break;
            case SELECT_CLASS:
                promptNotSet = editClass(input);
            default:
                promptNotSet = false;
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
        while(input.matches("[a-zA-Z1-9,.! ]+")){
            if(input.toUpperCase() == "Y"){
                view.askForMentorNewEmail();
                String mentorEmailInput = view.getUserInput();
                setEmail(mentorEmailInput);
                emailNotChanged = false;
                break;
            }else if (input.toUpperCase() == "N"){
                emailNotChanged = false;
                break;
            }else{
                view.showWrongAnswer();
                input = view.getUserInput();
            }
        }
        return emailNotChanged;
    }


    private void setEmail(String input) {
        if (input.matches("[a-zA-Z@.]+")) {
            try {
                if (new UserDAO().checkIfUsersExists(input)) {
                    view.showExistingValueWarning();
                } else {
                    selectedMentor.setEmail(input);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                view.showDatabaseError();
            }
        } else {
            view.showWrongEmailInput();
        }
    }

    private boolean editClass(String input){
        boolean classNotChanged = true;
        while(input.matches("[a-zA-Z1-9,.! ]+")){
            if(input.toUpperCase() == "Y"){
                view.showAllClasses();
                view.askForMentorNewClass();
                String mentorClassInput = view.getUserInput();
                setClass(mentorClassInput);
                classNotChanged = false;
                break;
            }else if (input.toUpperCase() == "N"){
                classNotChanged = false;
                break;
            }else{
                view.showWrongAnswer();
                input = view.getUserInput();
            }
        }
        return classNotChanged;
    }

    private void setClass(String input){
        while((input.matches("[a-zA-Z1-9,. ]+"))){
            try{
                if(classDAO.checkIfClassExists(input)){
                    if(!classDAO.checkIfUserIsInClass(selectedMentor)){
                        selectedClass = classDAO.getCodecoolClass(input); 
                        classDAO.addUserToCodecoolClass(selectedMentor, selectedClass);
                        break;
                    }else{
                        view.showMentorInClassInfo();
                        break;
                    }
                }else{
                    view.showWrongClassName();
                    view.showAllClasses();
                    view.askForMentorNewClass();
                    input = view.getUserInput();
                }
            }catch (SQLException e) {
                e.printStackTrace();
                view.showDatabaseError();
            }
        }
    }

    private void updateMentorEmail(){
        new UserDAO().updateUser(selectedMentor);
    }
}