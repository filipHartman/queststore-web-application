package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;

import java.sql.SQLException;

class ClassCreator {
    private static final String EXIT = "0";

    private String className;
    private RootView view;

    ClassCreator(RootView view) {
        this.view = view;
    }

    void createClass() {
        int userInputs = 0;
        final int prompts = 1;
        boolean loopContinuation = true;
        while (loopContinuation) {
            view.askForClassName();
            String input = view.getUserInput();
            if (input.equals(EXIT)) {
                view.showOperationCancelled();
                loopContinuation = false;
            } else {
                loopContinuation = setName(input);
                new codecoolClassDAO.createCodecoolClass(new CodecoolClass(className));
            }
        }
    }

    private boolean setName(String input){
        boolean nameNotSet = true;
        if (input.matches("[a-zA-Z1-9,. ]+") {
            try{
                if(new CodecoolClassDAO().checkIfClassExists(input)){
                    view.showExistingValueWarning();
                }else{
                    className = input;
                    nameNotSet = false;
                }
            }catch (SQLException e) {
                e.printStackTrace();
                view.showDatabaseError();
            }
        }else{
            view.showWrongClassNameInput()
        }
        return nameNotSet;
    }
}