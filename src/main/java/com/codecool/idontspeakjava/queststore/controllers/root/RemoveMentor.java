package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;

import java.sql.SQLException;

class RemoveMentor{
    private static final String EXIT = "0";

    private User selectedMentor;
    private RootView view;

    RemoveMentor(RootView view){
        this.view = view;
    }

    void deleteMentor(){
        boolean loopContinuation = true;
        while(loopContinuation){
            view.showAllMentors();
            view.askForMentorEmailInput();
            String input = view.getUserInput();
            view.clearScreen();
            
            if (input.equals(EXIT)) {
                loopContinuation = false;
            } else {
                loopContinuation = selectMentor(input);
                new SQLiteUserDAO().deleteUser(selectedMentor);
            }
        }

    }

    private boolean selectMentor(String email){
        boolean mentorNotSelected = true;
        try{
            if (new SQLiteUserDAO().checkIfUsersExists(email)) {
                selectedMentor = new SQLiteUserDAO().getUserByEmail(email);
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
}