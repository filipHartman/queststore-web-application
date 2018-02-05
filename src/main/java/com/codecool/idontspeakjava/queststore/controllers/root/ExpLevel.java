package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.database.ExperienceLevelDAO;
import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;

import java.sql.SQLException;

class ExpLevel{
    private static final String EXIT = "0";
    private static final int SELECT_NAME = 0;
    private static final int SELECT_THRESHOLD = 1;

    private RootView view;
    private long threshold;
    private String lvlName;

    ExpLevel(RootView view){
        this.view = view;
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
            new ExperienceLevelDAO().createExperienceLevel(new ExperienceLevel(lvlName, threshold));
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
        if (input.matches("[a-zA-Z0-9,. ]+")){
            try{
                if (!new ExperienceLevelDAO().checkIfExperienceLevelExists(input)){
                    lvlName = input;
                    nameNotSet = false;
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
        return nameNotSet;
    }

    private boolean setThreshold(String input){
        boolean thresholdNotSet = true;
        if (input.matches("[0-9]+")){
            threshold = new Long(input).longValue();
            thresholdNotSet = false;
        }else{
            view.WrongThresholdInput();
        }
        return thresholdNotSet;
    }
}