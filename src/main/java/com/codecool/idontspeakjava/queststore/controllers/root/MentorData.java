package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.RootView;

import com.codecool.idontspeakjava.queststore.models.Permissions;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
                getMentorClass();
                List<User> mentorStudents = getMentorStudents();
                view.showMentorInfo(selectedMentor, mentorClass, mentorStudents);
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

    private void getMentorClass(){
        try{
            mentorClass = new SQLiteCodecoolClassDAO().getUserCodecoolClass(selectedMentor);
        }catch(NullPointerException e){
            e.printStackTrace();
            view.showMentorNotAssignToClass();
        }
    }

    private List<User> getMentorStudents(){
        CodecoolClass pupilClass;
        List<User> allStudents = new SQLiteUserDAO().getUsersByPermission(Permissions.Student);
        List<User> mentorStudents = new ArrayList<User>();

        try{
            for (User pupil : allStudents) {
                pupilClass = new SQLiteCodecoolClassDAO().getUserCodecoolClass(pupil);
                if(pupilClass == null) continue;
                else if (mentorClass.getId() == pupilClass.getId()){
                    mentorStudents.add(pupil);
                }
            }
        }catch(NullPointerException e){
            e.printStackTrace();
            view.showDatabaseError();
        }
        return mentorStudents;
    }
}