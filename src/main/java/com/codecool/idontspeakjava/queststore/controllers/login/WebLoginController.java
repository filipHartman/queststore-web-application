package com.codecool.idontspeakjava.queststore.controllers.login;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.codecooler.terminal.CodecoolerController;
import com.codecool.idontspeakjava.queststore.controllers.mentor.terminal.MentorController;
import com.codecool.idontspeakjava.queststore.controllers.root.terminal.RootController;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.services.PasswordService;
import com.sun.net.httpserver.HttpExchange;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.HashMap;
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
        String response = "";
        String method = exchange.getRequestMethod();

        if (method.equals("GET")) {
            JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
            JtwigModel model = JtwigModel.newModel();
            response = template.render(model);
            sendResponse(exchange, response);

        } else if (method.equals("POST")) {
            try {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
                BufferedReader br = new BufferedReader(isr);
                String loginData = br.readLine();
                Map<String, String> inputs = parseLoginData(loginData);

                String email = inputs.get("email");
                String canditatePassword = inputs.get("password");

                Optional<User> user = Optional.ofNullable(processCredentialsAndReturnUserInstance(email));
                if (user.isPresent() && checkIfUserProvideCorrectPassword(user.get(), canditatePassword)) {
                    //set sessionId and redirect to user menu
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

    private Map<String, String> parseLoginData(String loginData) {
        Map<String, String> inputs = new HashMap<>();
        String key;
        String value;

        String[] pairs = loginData.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            key = keyValue[0];

            try {
                value = new URLDecoder().decode(keyValue[1], "UTF-8");
                inputs.put(key, value);

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return inputs;
    }

    private User processCredentialsAndReturnUserInstance(String email) {
        return usersDAO.getUserByEmail(email);
    }

    private boolean checkIfUserProvideCorrectPassword(User user, String canditatePassword) {
        String userPassword = user.getPasswordHash();
        if (userPassword.isEmpty()) {
//            return runPasswordGenerator(user); decide if we want direct to webpage to create password for new User
            System.out.println("No password for user");
            return false;
        } else {
            return passwordService.checkPassword(canditatePassword, userPassword);
        }
    }


}
