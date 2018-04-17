package com.codecool.idontspeakjava.queststore.controllers.root.terminal;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.RootView;

import java.sql.SQLException;

class AssignMentorToClass{
    private static final int SELECT_MENTOR = 0;
    private static final int SELECT_CLASS = 1;
    private static final String EXIT = "0";

    private User selectedMentor;
    private CodecoolClass selectedClass;
    private RootView view;

    AssignMentorToClass(RootView view) {
        this.view = view;
    }

    void assignMentor(){
        boolean loopContinuation = true;
        int userInputs = 0;
        final int prompts = 2;
        for (int i = 0; i < prompts && loopContinuation; i++){
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
            new SQLiteCodecoolClassDAO().removeUserFromCodecoolClass(selectedMentor);
            new SQLiteCodecoolClassDAO().addUserToCodecoolClass(selectedMentor, selectedClass);
            view.showMentorAssign();
        }else{
            view.showOperationCancelled();
        }
    }

    private void selectInfoForPrompt(int propmtInfo){
        switch(propmtInfo){
            case SELECT_MENTOR:
                view.showAllMentors();
                view.askForMentorEmailInput();
                break;
            case SELECT_CLASS:
                view.showAllClasses();
                view.askForClassNameInput();
                break;
        }
    }

    private boolean selectMentor(String input){
        boolean mentorNotSelected = true;
        try{
            if (new SQLiteUserDAO().checkIfUsersExists(input)) {
                selectedMentor = new SQLiteUserDAO().getUserByEmail(input);
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

    private boolean selectClass(String input){
        boolean classNotSelected = true;
        try{
            if (new SQLiteCodecoolClassDAO().checkIfClassExists(input)) {
                selectedClass = new SQLiteCodecoolClassDAO().getCodecoolClass(input);
                classNotSelected = false;
            }else{
                view.showWrongClassName();
            }
        }catch (SQLException e) {
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
}
