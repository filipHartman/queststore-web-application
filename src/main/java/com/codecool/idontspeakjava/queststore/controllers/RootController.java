package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.UserDAO;

public class RootController{
    private RootView view;
    private User user;
    private CodecoolClassDAO codecoolClassDAO;
    private UserDAO userDAO;
    private Permissions permission;

    public RootController(User user){
        this.view = new RootView();
        this.codecoolClassDAO = new CodecoolClassDAO();
        this.user = user;
        this.userDAO = new UserDAO();
    }

    private boolean createMentor() throws SQLException{
        view.showCreateMentorMenu("firstName");
        String firstName = view.getUserInput();
        view.showCreateMentorMenu("lastName");
        String lastName = view.getUserInput();
        view.showCreateMentorMenu("password");
        String passwordHash = view.getUserInput();
        view.showCreateMentorMenu("email");
        String email = view.getUserInput();
        userDAO.createUser(new User(firstName, lastName, passwordHash, email, permission.Root));
        return true;
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