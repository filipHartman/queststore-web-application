package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.controllers.login.SessionIdContainer;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.*;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
import java.net.URI;
import java.net.URLDecoder;
import java.util.*;

public abstract class AbstractHandler implements HttpHandler {
    private SessionIdContainer sessionIdContainer;

     public AbstractHandler() {
         this.sessionIdContainer = SessionIdContainer.getSessionIdContainer();
     }

    public SessionIdContainer getSessionIdContainer() {
        return sessionIdContainer;
    }

    public void redirectToLocation(HttpExchange exchange, String location) {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Location", location);
        try {
            exchange.sendResponseHeaders(302, -1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        exchange.close();
    }

    public void sendResponse(HttpExchange exchange, String response) {
        byte[] bytes = response.getBytes();
        try {
            exchange.sendResponseHeaders(200, bytes.length);
            OutputStream os = exchange.getResponseBody();
            os.write(bytes);
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendTemplateResponse(HttpExchange exchange, String templateName) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.twig", templateName));
        JtwigModel model = JtwigModel.newModel();
        String response = template.render(model);
        sendResponse(exchange, response);
    }


    public void sendTemplateResponseWithForm(HttpExchange exchange, String templateName, String form){
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.twig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("form", form);
        String response = template.render(model);
        sendResponse(exchange, response);

    }

    public void sendTemplateResponseWithForm(HttpExchange exchange, String templateName, String form, String form2){
        JtwigTemplate template = JtwigTemplate.classpathTemplate(String.format("templates/%s.twig", templateName));
        JtwigModel model = JtwigModel.newModel();
        model.with("form", form);
        model.with("form2", form2);
        String response = template.render(model);
        sendResponse(exchange, response);

    }

    public Map<String, String> readFormData(HttpExchange exchange) {
        String loginData = "";

         try {
             InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
             BufferedReader br = new BufferedReader(isr);
             loginData = br.readLine();
         }catch (IOException e){
             e.printStackTrace();
         }
        return parseFormData(loginData);
    }

    public Map<String, String> parseFormData(String formData) {
         Map<String, String> inputs = new HashMap<>();
        String key;
        String value;

        String[] pairs = formData.split("&");
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


    public boolean isLoggedIn(String sid) {
        return getSessionIdContainer().contains(sid);
    }

    public String getSidFromCookieStr(String cookieStr) {
        HttpCookie cookie = HttpCookie.parse(cookieStr).get(0);
        return cookie.toString().split("=")[1];
    }

    public String getAction(HttpExchange httpExchange){
        int actionIndex = 2;

        String uri = httpExchange.getRequestURI().toString();
        String[] uriParts = uri.split("/");

        return uriParts[actionIndex];
    }

    public boolean checkPermission(HttpExchange httpExchange, Permissions permission) {
        String cookieStr = httpExchange.getRequestHeaders().getFirst("Cookie");
        String sid = getSidFromCookieStr(cookieStr);

        if (!isLoggedIn(sid)) {
            return false;
        }

        int userId = getSessionIdContainer().getUserId(sid);
        Optional<User> user = Optional.ofNullable(new SQLiteUserDAO().getUserById(userId));

        return user.get().getPermission() == permission;
    }

    public User getChosenUser(List<User> users, String name){
        User editedUser = null;
        for (User user : users) {
            if (user.toString().equals(name)) {
                editedUser = user;
            }
        }
        return editedUser;
    }

    public CodecoolClass getChosenClass(List<CodecoolClass> codecoolClasses, String className) {
        CodecoolClass choosenClass = null;

        for (CodecoolClass codecoolClass : codecoolClasses) {
            if (codecoolClass.toString().equals(className)) {
                choosenClass = codecoolClass;
                break;
            }
        }
        return choosenClass;
    }

    public Quest getQuestByName(String questName, List<Quest> allQuests) {
        for (Quest quest : allQuests) {
            if (quest.toString().equals(questName)) {
                return quest;
            }
        }
        return null;
    }

    public Artifact getArtifactByName(String questName, List<Artifact> allArtifacts) {
        for (Artifact artifact : allArtifacts) {
            if (artifact.toString().equals(questName)) {
                return artifact;
            }
        }
        return null;
    }

    public int getUserIDFromURI(HttpExchange httpExchange){
        String uri = httpExchange.getRequestURI().toString();
        String[] uriParts = uri.split("/");
        int userID = -1;
        try {
            userID = Integer.parseInt(uriParts[uriParts.length -1]);

        }catch (NumberFormatException e){
            return userID;

        }
        return userID;

    }

    public String getHomeLocationFromSid(String sid) {
        String location;
        int userId = getSessionIdContainer().getUserId(sid);
        Optional<User> user = Optional.ofNullable(new SQLiteUserDAO().getUserById(userId));

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
        return location;
    }

}
