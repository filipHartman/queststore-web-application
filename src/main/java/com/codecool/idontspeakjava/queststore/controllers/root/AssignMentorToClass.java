package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.User;


import java.sql.SQLException;

class AssignMentorToClass{
    private static final int SELECT_MENTOR = 0;
    private static final int SELECT_CLASS = 1;
    private static final String EXIT = "0";

    private User selectedMentor;
    private CodecoolClass selectedClass;

    AssignMentorToClass(RootView view) {
        this.view = view;
    }

    void assignMentor(){
        boolean loopContinuation = true;
        int userInputs = 0;
        final int prompts = 2;
        for (int i = 0; i < prompt && loopContinuation; i++){
            boolean mentorNotAssign = true;
            while(mentorNotAssign){
                selectInfoForPrompt(i);
                String input = view.getUserInput();
                view.clearScreen();

                if (input.equals(EXIT)) {
                    loopContinuation = false;
                    mentorNotAssign = false;
                }else{
                    mentorNotAssign = selectPrompt(i, input);
                }
            }if (loopContinuation){
                userInputs++;
            }
        }if(userInputs==prompts){
            addMentorToClass(selectedMentor, selectedClass);
            view.showMentorAssign();
        }else{
            view.showOperationCancelled();
        }
    }

    private void selectInfoForPrompt(int propmtInfo){
        switch(propmtInfo){
            case SELECT_MENTOR:
                allMentors();
                view.askForMentorEmailInput();
                break;
            case SELECT_CLASS:
                allClasses();
                view.askForClassNameInput();
                break;
        }
    }

    private boolean selectMentor(String input){
        boolean mentorNotSelected = true;
        try{
            if(new userDAO().checkIfUsersExists(input)) {
                selectedMentor = new userDAO().getUserByEmail(input);
                mentorNotSelected = false;
            }else{
                view.showWrongEmailInput();
            }
        }catch SQLException e) {
            e.printStackTrace();
            view.showDatabaseError();
        }
        return mentorNotSelected;
    }

    private boolean selectClass(String input){
        boolean classNotSelected = true;
        try{
            if(new CodecoolClassDAO().checkIfClassExists(input)){
                selectedClass = new CodecoolClassDAO().getCodecoolClass(input);
                classNotSelected = false;
            }else{
                view.showWrongClassName();
            }
        }catch SQLException e) {
            e.printStackTrace();
            view.showDatabaseError();
        }
        return classNotSelected;
    }

    private boolean selectPrompt(int prompt, String input) {
        boolean promptNotSet;
        switch (prompt) {
            case SELECT_MENTOR:
                promptNotSet = selectMentor(input);
                break;
            case SELECT_CLASS:
                promptNotSet = selectClass(input);
                break;
            default:
                promptNotSet = false;
        }
        return promptNotSet;
    }

    private void addMentorToClass(User mentor, CodecoolClass ccClass){
        try {
            new CodecoolClassDAO().addUserToCodecoolClass(mentor, ccClass);
        }catch SQLException e) {
            e.printStackTrace();
            view.showDatabaseError();
        }
    }


    private void allMentors(){
        for (User mentorUser : new userDAO().getUsersByPermission(Permissions.Mentor)) {
            System.out.println(mentorUser.getFirstName() + " " + mentorUser.getLastName() + " " + mentorUser.getEmail()+"\n");
        }
    }

    private void allClasses(){
        for (CodecoolClass codecoolClass : new codecoolClassDAO().getAllCodecoolClasses()) {
            System.out.println(codecoolClass.getName());
        }
    }
}
