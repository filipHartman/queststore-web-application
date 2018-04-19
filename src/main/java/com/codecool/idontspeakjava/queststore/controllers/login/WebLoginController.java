package com.codecool.idontspeakjava.queststore.controllers.login;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.services.PasswordService;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.HttpCookie;
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
        String uri = exchange.getRequestURI().toString();
        if (!uri.equals("/")) {
            redirectToLocation(exchange, "/");
            return;
        }

        String method = exchange.getRequestMethod();
        String cookieStr = exchange.getRequestHeaders().getFirst("Cookie");
        HttpCookie cookie;
        String sid;
        if (cookieStr == null) {
            sid = generateSID();
            cookie = new HttpCookie("SID", sid);
            exchange.getResponseHeaders().add("Set-Cookie", cookie.toString());
        } else {
            sid = getSidFromCookieStr(cookieStr);
        }

        if (method.equals("GET") && !isLoggedIn(sid)) {
            sendTemplateResponse(exchange, "login");

        } if (method.equals("GET") && isLoggedIn(sid)) {
            int userId = getSessionIdContainer().getUserId(sid);
            Optional<User> user = Optional.ofNullable(usersDAO.getUserById(userId));
            redirectToCorrectMenu(exchange, user);

        } else if (method.equals("POST")) {

                Map<String, String> inputs = readFormData(exchange);
                String email = inputs.get("email");
                String candidatePassword = inputs.get("password");

                Optional<User> user = Optional.ofNullable(processCredentialsAndReturnUserInstance(email));
                if (user.isPresent() && checkIfUserProvideCorrectPassword(user.get(), candidatePassword)) {
                    getSessionIdContainer().add(sid, user.get().getId());
                    redirectToCorrectMenu(exchange, user);

                } else {
                    redirectToLocation(exchange,"/");
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
                location = "/";
        }

        redirectToLocation(exchange, location);
    }

    private String generateSID() {
        return UUID.randomUUID().toString();
    }

    private User processCredentialsAndReturnUserInstance(String email) {
        return usersDAO.getUserByEmail(email);
    }

    private boolean checkIfUserProvideCorrectPassword(User user, String candidatePassword) {
        String userPassword = user.getPasswordHash();
        if (userPassword.isEmpty()) {
            return false;
        } else {
            return passwordService.checkPassword(candidatePassword, userPassword); 
        }
    }

}
