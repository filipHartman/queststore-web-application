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
        }
        return correctName;
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
        }
        return correctExpLvlName;
    }

    public boolean checkThresholdInput(String input){
        boolean correctThreshold = false;
        if (input.matches("\\d+")){
            try{
                if (Long.valueOf(input).longValue() <= Long.MAX_VALUE) {
                    correctThreshold = true;
                }
            }catch(NumberFormatException e){
                e.printStackTrace();
                view.WrongThresholdInput();
            }
        }else{
            view.WrongThresholdInput();
        }
        return correctThreshold;
    }

}