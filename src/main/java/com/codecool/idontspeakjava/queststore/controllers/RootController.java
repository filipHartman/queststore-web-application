package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;;

public class RootController{
    private RootView view;
    private User user;
    private CodecoolClassDAO codecoolClassDAO;

    public RootController(User user){
        this.view = new RootView();
        this.codecoolClassDAO = new CodecoolClassDAO();
        this.user = user;
    }

    private boolean createMentor(){

    }

    private boolean createCodecoolClass(){
        view.showCreateCodecoolClassMenu();
        String className = view.getUserInput();
        for (CodecoolClass codecoolClass : codecoolClassDAO.getAllCodecoolClasses())
            if(className == codecoolClass.getName())
                return false;
        codecoolClassDAO.createCodecoolClass(new CodecoolClass(className));
        return true;
    }

    private void assignMentorToClass(){

    }

    private void editMentor(){
        
    } 

    private void showMentor(){

    }

    private void showCodecoolClassOfMentor(){

    }

    private boolean createExperienceLevel(){

    }
}