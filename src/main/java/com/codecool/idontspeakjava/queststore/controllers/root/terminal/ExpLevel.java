package com.codecool.idontspeakjava.queststore.controllers.root.terminal;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteExperienceLevelDAO;
import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;
import com.codecool.idontspeakjava.queststore.views.RootView;

class ExpLevel{
    private static final String EXIT = "0";
    private static final int SELECT_NAME = 0;
    private static final int SELECT_THRESHOLD = 1;

    private RootView view;
    private long threshold;
    private String lvlName;
    private InputValidation validation;

    ExpLevel(RootView view){
        this.view = view;
        validation = new InputValidation(view);
    }

    void CreateExpLvl(){
        boolean iterationContinuation = true;
        int prompt = 2;
        int userInput = 0;
        for (int i = 0; i < prompt && iterationContinuation; i++){
            boolean loopContinuation = true;
            while(loopContinuation){
                selectPromptInfo(i);
                String input = view.getUserInput();
                if (input.equals(EXIT)) {
                    loopContinuation = false;
                    iterationContinuation = false;
                } else {
                    loopContinuation = selectPrompt(i, input);
                }
            }
            if(iterationContinuation){    
                userInput++;
            }
        }if(userInput == prompt){
            new SQLiteExperienceLevelDAO().createExperienceLevel(new ExperienceLevel(lvlName, threshold));
            view.showExpLvlCreated();
        }else{
            view.showOperationCancelled();
        }
    }

    private void selectPromptInfo(int prompt){
        switch(prompt){
            case SELECT_NAME:
                view.askForExpLvlName();
                break;
            case SELECT_THRESHOLD:
                view.askForExpLvlThreshold();
                break;
        }
    }

    private boolean selectPrompt(int prompt, String input){
        boolean promptNotSet = true;
        switch(prompt){
            case SELECT_NAME:
                promptNotSet = setName(input);
                break;
            case SELECT_THRESHOLD:
                promptNotSet = setThreshold(input);
                break;
        }
        return promptNotSet;
    }

    private boolean setName(String input){
        boolean nameNotSet = true;
        if (validation.checkExpLvlName(input)){
            lvlName = input;
            nameNotSet = false;
        }else{
            view.showWrongExpLvlInput();
        }
        return nameNotSet;
    }

    private boolean setThreshold(String input){
        boolean thresholdNotSet = true;
        if (validation.setThreshold(input)){
            threshold = Long.valueOf(input).longValue();
            thresholdNotSet = false;
        }
        return thresholdNotSet;
    }
}