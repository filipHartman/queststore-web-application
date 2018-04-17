package com.codecool.idontspeakjava.queststore.controllers.admin.terminal;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.models.User;

import java.sql.SQLException;

public class RootController {
    private RootView view;

    private static final String CREATE_MENTOR = "1";
    private static final String REMOVE_MENTOR = "2";
    private static final String CREATE_CODECOOL_CLASS = "3";
    private static final String ASSIGN_MENTOR_TO_CLASS = "4";
    private static final String EDIT_MENTOR = "5";
    private static final String SHOW_MENTOR = "6";
    private static final String CREATE_EXPERIENCE_LEVEL = "7";
    private static final String EXIT = "0";

    public RootController(User user) {
        view = new RootView();
    }

    public void start() throws SQLException {
        boolean runProgram = true;

        while (runProgram) {
            view.clearScreen();
            view.showMainMenu();
            String userInput = view.getUserInput();
            switch (userInput) {
                case CREATE_MENTOR:
                    new MentorCreator(view).createMentor();
                    break;
                case REMOVE_MENTOR:
                    new RemoveMentor(view).deleteMentor();
                    break;
                case CREATE_CODECOOL_CLASS:
                    new ClassCreator(view).createClass();
                    break;
                case ASSIGN_MENTOR_TO_CLASS:
                    new AssignMentorToClass(view).assignMentor();
                    break;
                case EDIT_MENTOR:
                    new MentorEdit(view).editMentor();
                    break;
                case SHOW_MENTOR:
                    new MentorData(view).showMentor();
                    break;
                case CREATE_EXPERIENCE_LEVEL:
                    new ExpLevel(view).CreateExpLvl();
                    break;
                case EXIT:
                    runProgram = false;
                    break;
                default:
                    view.showWrongInput();
            }
        }
    }
}




