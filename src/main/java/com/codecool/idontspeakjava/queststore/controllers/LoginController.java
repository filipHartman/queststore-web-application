package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.controllers.codecooler.CodecoolerController;
import com.codecool.idontspeakjava.queststore.controllers.mentor.MentorController;
import com.codecool.idontspeakjava.queststore.controllers.root.RootController;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.services.PasswordService;
import com.codecool.idontspeakjava.queststore.views.LoginView;

import java.sql.SQLException;
import java.util.Optional;

public class LoginController {

    private final LoginView loginView;
    private final UserDAO usersDAO;
    private final PasswordService passwordService;

    public LoginController() {
        this.loginView = new LoginView();
        this.usersDAO = new SQLiteUserDAO();
        this.passwordService = new PasswordService();
        start();
    }

    private void start() {
        boolean userNotDecidedToExit = true;

        while (userNotDecidedToExit) {
            loginView.clearScreen();
            loginView.showGreeting();
            String userLogin = loginView.getUserLogin();
            if (userLogin.isEmpty() || userLogin.equals("0")) {
                userNotDecidedToExit = false;
            } else {
                Optional<User> user = Optional.ofNullable(processCredentialsAndReturnUserInstance(userLogin));
                if (user.isPresent()) {
                    if (checkIfUserProvideCorrectPassword(user.get())) {
                        runNextController(user.get());
                    } else {
                        loginView.showBadCredentials();
                    }
                } else {
                    loginView.showBadCredentials();
                }
            }


        }
    }

    private User processCredentialsAndReturnUserInstance(String email) {
        return usersDAO.getUserByEmail(email);
    }

    private boolean checkIfUserProvideCorrectPassword(User user) {
        String userPassword = user.getPasswordHash();
        if (userPassword.isEmpty()) {

            return runPasswordGenerator(user);
        } else {
            String candidatePassword = loginView.getUserPassword();
            return passwordService.checkPassword(candidatePassword, userPassword);
        }
    }

    private boolean runPasswordGenerator(User user) {
        boolean passwordGeneratorIsRunning = true;
        boolean isPasswordCreated = false;
        while (passwordGeneratorIsRunning) {
            loginView.clearScreen();
            String newPassword = loginView.getNewUserPassword();
            if (passwordService.checkIfNewPasswordIsCorrect(newPassword)) {
                String hash = passwordService.hashPassword(newPassword);
                user.setPasswordHash(hash);
                usersDAO.updateUser(user);
                passwordGeneratorIsRunning = false;
                isPasswordCreated = true;
            } else {
                loginView.showNewPasswordIsIncorrect();
            }
        }
        return isPasswordCreated;
    }


    private void runNextController(User user) {

        try {
            switch (user.getPermission()) {
                case Mentor:
                    new MentorController(user).run();
                    break;
                case Student:
                    new CodecoolerController(user).run();
                    break;
                case Root:
                    new RootController(user).start();
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
