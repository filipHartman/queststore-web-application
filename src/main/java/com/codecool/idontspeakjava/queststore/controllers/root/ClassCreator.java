package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.views.RootView;

import java.sql.SQLException;

class ClassCreator {
    private static final String EXIT = "0";

    private String className;
    private RootView view;

    ClassCreator(RootView view) {
        this.view = view;
    }

    void createClass() {
        boolean loopContinuation = true;
        while (loopContinuation) {
            view.askForClassName();
            String input = view.getUserInput();
            if (input.equals(EXIT)) {
                view.showOperationCancelled();
                loopContinuation = false;
            } else {
                if(!setName(input)){
                    new SQLiteCodecoolClassDAO().createCodecoolClass(new CodecoolClass(className));
                    loopContinuation = false;
                    view.showClassCreateComplete();
                }
            }
        }
    }

    private boolean setName(String input){
        boolean nameNotSet = true;
        if (input.matches("[a-zA-Z0-9,. ]+")) {
            try{
                if (new SQLiteCodecoolClassDAO().checkIfClassExists(input)) {
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
            view.showWrongClassNameInput();
        }
        return nameNotSet;
    }
}