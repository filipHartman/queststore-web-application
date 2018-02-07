package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteExperienceLevelDAO;

import java.sql.SQLException;

class InputValidation{

    private RootView view;

    InputValidation(RootView view){
        this.view = view;
    }

    public boolean checkName(String input) {
        boolean correctName = false;
        if (input.matches("[a-zA-Z]+")) {
            correctName = true;
        } else {
            view.showWrongNameInput();
        }
        return correctName;
    }

    public boolean checkLastName(String input) {
        boolean correctLastName = false;
        if (input.matches("[a-zA-Z]+")) {
            correctLastName = true;
        } else {
            view.showWrongNameInput();
        }
        return correctLastName;
    }

    public boolean checkEmail(String input) {
        boolean correctEmail = false;
        if (input.matches("[a-zA-Z0-9@.]+")) {
            try {
                if (new SQLiteUserDAO().checkIfUsersExists(input)) {
                    view.showExistingValueWarning();
                } else {
                    correctEmail = true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                view.showDatabaseError();
            }
        } else {
            view.showWrongEmailInput();
        }
        return correctEmail;
    }

    public boolean checkClassName(String input){
        boolean correctClassName = false;
        if (input.matches("[a-zA-Z0-9,. ]+")) {
            try{
                if (new SQLiteCodecoolClassDAO().checkIfClassExists(input)) {
                    view.showExistingValueWarning();
                }else{
                    correctClassName = true;
                }
            }catch (SQLException e) {
                e.printStackTrace();
                view.showDatabaseError();
            }
        }else{
            view.showWrongClassNameInput();
        }
        return correctClassName;
    }

    public boolean checkExpLvlName(String input){
        boolean correctExpLvlName = false;
        if (input.matches("[a-zA-Z0-9,. ]+")){
            try{
                if (!new SQLiteExperienceLevelDAO().checkIfExperienceLevelExists(input)) {
                    correctExpLvlName = true;
                }else{
                    view.showExistingValueWarning();
                }
            }catch(SQLException e){
                e.printStackTrace();
                view.showDatabaseError();
            }
        }else{
            view.showWrongExpLvlInput();
        }
        return correctExpLvlName;
    }

    public boolean setThreshold(String input){
        boolean correctThreshold = false;
        if (input.matches("[0-9]+")){
            correctThreshold = true;
        }else{
            view.WrongThresholdInput();
        }
        return correctThreshold;
    }

}