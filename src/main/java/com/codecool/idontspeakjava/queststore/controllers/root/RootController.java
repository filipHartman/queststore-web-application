package com.codecool.idontspeakjava.queststore.controllers.root;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.UserDAO;

import java.sql.SQLException;

public class RootController {
    private RootView view;
    private User root;

    private static final String CREATE_MENTOR = "1";
    private static final String CREATE_CODECOOL_CLASS = "2";
    private static final String ASSIGN_MENTOR_TO_CLASS = "3";
    private static final String EDIT_MENTOR = "4";
    private static final String SHOW_MENTOR = "5";
    private static final String SHOW_CLASS_FOR_MENTOR = "6";
    private static final String CREATE_EXPERIENCE_LEVEL = "7";
    private static final String EXIT = "0";

    public RootController(User user) {
        view = new RootView();
        root = user;
        
    }

    public void start() throws SQLException {
        boolean runProgram = true;

        while (runProgram) {
            view.showMainMenu();
            String userInput = view.getUserInput();
            switch (userInput) {
                case CREATE_MENTOR:
                    new MentorCreator(view).createMentor();
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
                case SHOW_CLASS_FOR_MENTOR:
                    //showCodecoolClassOfMentor();
                    break;
                case CREATE_EXPERIENCE_LEVEL:
                    //createExperienceLevel();
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




//     private void showCodecoolClassOfMentor() {
//         for (User mentorUser : userDAO.getUsersByPermission(Permissions.Mentor)) {
//             System.out.println(mentorUser.getFirstName() + " " + mentorUser.getLastName() + " " + mentorUser.getEmail());
//         }
//         view.inputInfoMentorEmail();
//         String mentorEmail = view.getUserInput();
//         User selectedMentor = userDAO.getUserByEmail(mentorEmail);
//         for (CodecoolClass codecoolClass : codecoolClassDAO.getAllCodecoolClasses()) {
//             if (codecoolClass.getName().equals(codecoolClassDAO.getUserCodecoolClass(selectedMentor).getName())) {
//                 System.out.println("Class " + codecoolClass.getName());
//             }
//         }
//     }

//     private boolean createExperienceLevel() {
//         return true;
//     }
// }