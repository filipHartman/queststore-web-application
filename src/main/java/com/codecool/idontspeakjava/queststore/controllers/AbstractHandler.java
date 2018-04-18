package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.controllers.login.SessionIdContainer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.*;
import java.net.HttpCookie;
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

    public Map<String, String> readFormData(HttpExchange exchange) throws IOException {
        InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "UTF-8");
        BufferedReader br = new BufferedReader(isr);
        String loginData = br.readLine();
        return parseFormData(loginData);
    }

    public Map<String, String> parseFormData(String formData) {
        System.out.println(formData);
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

}
