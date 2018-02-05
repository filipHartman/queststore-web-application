package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.RootView;

import java.sql.SQLException;

class MentorData{
    private static final String EXIT = "0";
    
    private User selectedMentor;
    private CodecoolClass mentorClass;
    private RootView view;

    MentorData(RootView view){
        this.view = view;
    }

    void showMentor(){
        boolean loopContinuation = true;
        while(loopContinuation){
            view.showAllMentors();
            view.askForMentorEmailInput();
            String input = view.getUserInput();
            view.clearScreen();
            if (input.equals(EXIT)) {
                view.showOperationCancelled();
                loopContinuation = false;
            }else if(!selectMentor(input)){
                getMentorClass(selectedMentor);
                view.showMentorInfo(selectedMentor, mentorClass);
                loopContinuation = false;
                }
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

    private void getMentorClass(User mentor){
        try{
            mentorClass = new SQLiteCodecoolClassDAO().getUserCodecoolClass(selectedMentor);
        }catch(NullPointerException e){
            e.printStackTrace();
            view.showMentorNotAssignToClass();
        }
    }
}