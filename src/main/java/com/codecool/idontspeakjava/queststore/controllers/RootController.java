package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.UserDAO;

import java.sql.SQLException;

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

    public void start() throws SQLException{
        boolean startProgram = true;
    
        while (startProgram) {
            view.showMainMenu();
            String userInput = view.getUserInput();
            if(userInput=="1"){
                createMentor();
            }else if(userInput=="2"){
                createCodecoolClass();
            }else if(userInput=="3"){
                assignMentorToClass();
            }else if(userInput=="4"){
                editMentor();
            }else if(userInput=="5"){
                showMentor();
            }else if(userInput=="6"){
                showCodecoolClassOfMentor();
            }else if(userInput=="7"){
                createExperienceLevel();
            }else if(userInput=="0"){
                startProgram = false;
                break;
            }else{
                view.showWrongInput();
            }
        }
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
            System.out.println(mentorUser.getFirstName() +" "+ mentorUser.getLastName() +" "+ mentorUser.getEmail());
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
            System.out.println(mentorUser.getFirstName() +" "+ mentorUser.getLastName() +" "+ mentorUser.getEmail());
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
        for(User mentorUser : userDAO.getUsersByPermission(Permissions.Mentor)){
            System.out.println(mentorUser.getFirstName() +" "+ mentorUser.getLastName() +" "+ mentorUser.getEmail());
        }
        view.inputInfoMentorEmail();
        String mentorEmail = view.getUserInput();
        User selectedMentor = userDAO.getUserByEmail(mentorEmail);
        CodecoolClass mentorClass = codecoolClassDAO.getUserCodecoolClass(selectedMentor);
        view.showMentorInfo(selectedMentor, mentorClass);
    }

    private void showCodecoolClassOfMentor(){
        for(User mentorUser : userDAO.getUsersByPermission(Permissions.Mentor)){
            System.out.println(mentorUser.getFirstName() +" "+ mentorUser.getLastName() +" "+ mentorUser.getEmail());
        }
        view.inputInfoMentorEmail();
        String mentorEmail = view.getUserInput();
        User selectedMentor = userDAO.getUserByEmail(mentorEmail);
        for(CodecoolClass codecoolClass : codecoolClassDAO.getAllCodecoolClasses()){
            if(codecoolClass.getName() == codecoolClassDAO.getUserCodecoolClass(selectedMentor).getName()){
                System.out.println("Class " + codecoolClass.getName());
            }
        }
    }

    private boolean createExperienceLevel(){
        return true;
    }
}