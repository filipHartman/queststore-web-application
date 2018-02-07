package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.views.RootView;

class ClassCreator {
    private static final String EXIT = "0";

    private String className;
    private RootView view;
    private InputValidation validation;

    ClassCreator(RootView view) {
        this.view = view;
        validation = new InputValidation(view);
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
        if (validation.checkClassName(input)) {
            className = input;
            nameNotSet = false;
        }
        return nameNotSet;
    }
}