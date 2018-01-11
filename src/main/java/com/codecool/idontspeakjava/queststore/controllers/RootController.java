package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.UserDAO;

import java.sql.SQLException;

class RootController {
    private RootView view;
    private User user;
    private CodecoolClassDAO codecoolClassDAO;
    private UserDAO userDAO;

    private static final String CREATE_MENTOR = "1";
    private static final String CREATE_CODECOOL_CLASS = "2";
    private static final String ASSIGN_MENTOR_TO_CLASS = "3";
    private static final String EDIT_MENTOR = "4";
    private static final String SHOW_MENTOR = "5";
    private static final String SHOW_CLASS_FOR_MENTOR = "6";
    private static final String CREATE_EXPERIENCE_LEVEL = "7";
    private static final String EXIT = "0";

    RootController(User user) {
        this.view = new RootView();
        this.codecoolClassDAO = new CodecoolClassDAO();
        this.user = user;
        this.userDAO = new UserDAO();
    }

    void start() throws SQLException {
        boolean runProgram = true;

        while (runProgram) {
            view.showMainMenu();
            String userInput = view.getUserInput();
            switch (userInput) {
                case CREATE_MENTOR:
                    createMentor();
                    break;
                case CREATE_CODECOOL_CLASS:
                    createCodecoolClass();
                    break;
                case ASSIGN_MENTOR_TO_CLASS:
                    assignMentorToClass();
                    break;
                case EDIT_MENTOR:
                    editMentor();
                    break;
                case SHOW_MENTOR:
                    showMentor();
                    break;
                case SHOW_CLASS_FOR_MENTOR:
                    showCodecoolClassOfMentor();
                    break;
                case CREATE_EXPERIENCE_LEVEL:
                    createExperienceLevel();
                    break;
                case EXIT:
                    runProgram = false;
                    break;
                default:
                    view.showWrongInput();
            }
        }
    }

    private boolean createMentor() throws SQLException {
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

    private boolean createCodecoolClass() {
        view.inputInfoClassName();
        String className = view.getUserInput();
        codecoolClassDAO.createCodecoolClass(new CodecoolClass(className));
        return true;
    }

    private void assignMentorToClass() {
        for (User mentorUser : userDAO.getUsersByPermission(Permissions.Mentor)) {
            System.out.println(mentorUser.getFirstName() + " " + mentorUser.getLastName() + " " + mentorUser.getEmail());
        }
        view.inputInfoMentorEmail();
        String mentorEmail = view.getUserInput();
        User selectedMentor = userDAO.getUserByEmail(mentorEmail);
        for (CodecoolClass codecoolClass : codecoolClassDAO.getAllCodecoolClasses()) {
            System.out.println(codecoolClass.getName());
        }
        view.inputInfoClassName();
        String className = view.getUserInput();
        CodecoolClass selectedClass = codecoolClassDAO.getCodecoolClass(className);
        codecoolClassDAO.addUserToCodecoolClass(selectedMentor, selectedClass);

    }

    private void editMentor() {
        for (User mentorUser : userDAO.getUsersByPermission(Permissions.Mentor)) {
            System.out.println(mentorUser.getFirstName() + " " + mentorUser.getLastName() + " " + mentorUser.getEmail());
        }
        view.inputInfoMentorEmail();
        String mentorEmail = view.getUserInput();
        User selectedMentor = userDAO.getUserByEmail(mentorEmail);
        if (view.editMentorOptionAsk("Email")) {
            view.inputInfoNewMentorEmail();
            String newEmail = view.getUserInput();
            selectedMentor.setEmail(newEmail);
        }
        System.out.println(codecoolClassDAO.getUserCodecoolClass(selectedMentor).getName());
        if (view.editMentorOptionAsk("Class")) {
            codecoolClassDAO.removeUserFromCodecoolClass(selectedMentor);
            for (CodecoolClass codecoolClass : codecoolClassDAO.getAllCodecoolClasses()) {
                System.out.println(codecoolClass.getName());
            }
            view.inputInfoClassName();
            String className = view.getUserInput();
            CodecoolClass selectedClass = codecoolClassDAO.getCodecoolClass(className);
            codecoolClassDAO.addUserToCodecoolClass(selectedMentor, selectedClass);
        }
    }

    private void showMentor() {
        for (User mentorUser : userDAO.getUsersByPermission(Permissions.Mentor)) {
            System.out.println(mentorUser.getFirstName() + " " + mentorUser.getLastName() + " " + mentorUser.getEmail());
        }
        view.inputInfoMentorEmail();
        String mentorEmail = view.getUserInput();
        User selectedMentor = userDAO.getUserByEmail(mentorEmail);
        CodecoolClass mentorClass = codecoolClassDAO.getUserCodecoolClass(selectedMentor);
        view.showMentorInfo(selectedMentor, mentorClass);
    }

    private void showCodecoolClassOfMentor() {
        for (User mentorUser : userDAO.getUsersByPermission(Permissions.Mentor)) {
            System.out.println(mentorUser.getFirstName() + " " + mentorUser.getLastName() + " " + mentorUser.getEmail());
        }
        view.inputInfoMentorEmail();
        String mentorEmail = view.getUserInput();
        User selectedMentor = userDAO.getUserByEmail(mentorEmail);
        for (CodecoolClass codecoolClass : codecoolClassDAO.getAllCodecoolClasses()) {
            if (codecoolClass.getName().equals(codecoolClassDAO.getUserCodecoolClass(selectedMentor).getName())) {
                System.out.println("Class " + codecoolClass.getName());
            }
        }
    }

    private boolean createExperienceLevel() {
        return true;
    }
}