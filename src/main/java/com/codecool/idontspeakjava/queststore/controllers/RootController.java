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

    public RootController(User user){
        this.view = new RootView();
        this.codecoolClassDAO = new CodecoolClassDAO();
        this.user = user;
        this.userDAO = new UserDAO();
    }

    private boolean createMentor() throws SQLException{
        view.inputInfoMentorName();
        String firstName = view.getUserInput();
        view.inputInfoMentorLastName();
        String lastName = view.getUserInput();
        view.inputInfoMentorPassword();
        String passwordHash = view.getUserInput();
        view.inputInfoMentorEmail();
        String email = view.getUserInput();
        userDAO.createUser(new User(firstName, lastName, passwordHash, email, Permissions.Root));
        return true;
    }

    private boolean createCodecoolClass(){
        view.inputInfoClassName();
        String className = view.getUserInput();
        codecoolClassDAO.createCodecoolClass(new CodecoolClass(className));
        return true;
    }

    private void assignMentorToClass(){
        for(User mentorUser : userDAO.getUsersByPermission(Permissions.Mentor)){
            System.out.println(mentorUser);
        }
        view.inputInfoMentorEmail();
        String mentorEmail = view.getUserInput();
        User selectedMentor = userDAO.getUserByEmail(mentorEmail);
        for(CodecoolClass codecoolClass : codecoolClassDAO.getAllCodecoolClasses()){
            System.out.println(codecoolClass.getName());
        }
        view.inputInfoClassName();
        String className = view.getUserInput();
        CodecoolClass selectedClass = codecoolClassDAO.getCodecoolClass(className);
        codecoolClassDAO.addUserToCodecoolClass(selectedMentor, selectedClass);

    }

    private void editMentor(){
        for(User mentorUser : userDAO.getUsersByPermission(Permissions.Mentor)){
            System.out.println(mentorUser);
        }
        view.inputInfoMentorEmail();
        String mentorEmail = view.getUserInput();
        User selectedMentor = userDAO.getUserByEmail(mentorEmail);
        if(view.editMentorOptionAsk("Email")){
            view.inputInfoNewMentorEmail();
            String newEmail = view.getUserInput();
            selectedMentor.setEmail(newEmail);
        }
        System.out.println(codecoolClassDAO.getUserCodecoolClass(selectedMentor).getName());
        if(view.editMentorOptionAsk("Class")){
            codecoolClassDAO.removeUserFromCodecoolClass(selectedMentor);
            for(CodecoolClass codecoolClass : codecoolClassDAO.getAllCodecoolClasses()){
                System.out.println(codecoolClass.getName());
            }
            view.inputInfoClassName();
            String className = view.getUserInput();
            CodecoolClass selectedClass = codecoolClassDAO.getCodecoolClass(className);
            codecoolClassDAO.addUserToCodecoolClass(selectedMentor, selectedClass);
        }
    }

    private void showMentor(){

    }

    private void showCodecoolClassOfMentor(){

    }

    private boolean createExperienceLevel(){

    }
}