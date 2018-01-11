package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.controllers.codecooler.CodecoolerController;
import com.codecool.idontspeakjava.queststore.controllers.mentor.MentorController;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.LoginView;
import com.codecool.idontspeakjava.queststore.views.UserView;

import java.util.Optional;

public class LoginController {

    private final LoginView loginView;
    private final UserDAO usersDAO;

    public LoginController() {
        this.loginView = new LoginView();
        this.usersDAO = new UserDAO();
        start();
    }

    private void start() {
        boolean userNotDecidedToExit = true;

        while (userNotDecidedToExit) {
            UserView.clearScreen();
            loginView.showGreeting();
            Optional<User> user = Optional.ofNullable(processCredentialsAndReturnUserInstance(loginView.getUserLogin()));
            if (user.isPresent()) {
                userNotDecidedToExit = false;
                runNextController(user.get());
            } else {
                loginView.showBadCredentials();
            }

        }
    }

    private User processCredentialsAndReturnUserInstance(String email) {
        return usersDAO.getUserByEmail(email);
    }

    private void runNextController(User user) {

        switch (user.getPermission()) {
            case Mentor:
                new MentorController(user).run();
                break;
            case Student:
                new CodecoolerController();
                break;
            case Root:
                System.out.println("Root");
                break;
        }
    }


}
