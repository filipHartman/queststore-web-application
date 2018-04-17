package com.codecool.idontspeakjava.queststore.controllers.login;

import java.util.HashMap;
import java.util.Map;

public class SessionIdContainer {

    private static SessionIdContainer instance;
    private Map<String, Integer> container;

    private SessionIdContainer() {
        container = new HashMap<>();
    }

    public static SessionIdContainer getSessionIdContainer() {
        if (instance == null) {
            instance = new SessionIdContainer();
        }
        return instance;
    }

    public Map<String, Integer> getContainer() {
        return container;
    }

    public void add(String sessionId, int userId) {
        this.container.put(sessionId, userId);
    }

    public void remove(String sessionId) {
        this.container.remove(sessionId);
    }
}
