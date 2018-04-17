package com.codecool.idontspeakjava.queststore.controllers.login;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.services.PasswordService;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class WebLoginController extends AbstractHandler {
    private final UserDAO usersDAO;
    private final PasswordService passwordService;

    public WebLoginController() {
        super();
        this.usersDAO = new SQLiteUserDAO();
        this.passwordService = new PasswordService();
    }

    @Override
    public void handle(HttpExchange exchange) {
        String response;
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            sendTemplateResponse(exchange,"login");

        } else if (method.equals("POST")) {
            try {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String loginData = br.readLine();
                Map<String, String> inputs = parseFormData(loginData);

                String email = inputs.get("email");
                String candidatePassword = inputs.get("password"); 

                Optional<User> user = Optional.ofNullable(processCredentialsAndReturnUserInstance(email));
                if (user.isPresent() && checkIfUserProvideCorrectPassword(user.get(), candidatePassword)) {
                    String sid = generateSID();
                    getSessionIdContainer().add(sid, user.get().getId());
                    redirectToCorrectMenu(exchange, user);

                } else {
                    redirectToLocation(exchange,"/login");
                }

            } catch (IOException e) {
                e.printStackTrace();
                redirectToLocation(exchange,"/login");
            }
        }
    }

    private void redirectToCorrectMenu(HttpExchange exchange, Optional<User> user) {
        String location;

        switch (user.get().getPermission()) {
            case Mentor:
                location = "/mentor";
                break;
            case Student:
                location = "/student";
                break;
            case Root:
                location = "/admin";
                break;
            default:
                location = "/login";
        }

        redirectToLocation(exchange, location);
    }

    private String generateSID() {
        return UUID.randomUUID().toString();
    }

    private User processCredentialsAndReturnUserInstance(String email) {
        return usersDAO.getUserByEmail(email);
    }

    private boolean checkIfUserProvideCorrectPassword(User user, String canditatePassword) {
        String userPassword = user.getPasswordHash();
        if (userPassword.isEmpty()) {
            return false;
        } else {
            return passwordService.checkPassword(canditatePassword, userPassword);
        }
    }


}
