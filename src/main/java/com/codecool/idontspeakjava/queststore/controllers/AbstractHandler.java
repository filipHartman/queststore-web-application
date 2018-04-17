package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.controllers.login.SessionIdContainer;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

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
}
