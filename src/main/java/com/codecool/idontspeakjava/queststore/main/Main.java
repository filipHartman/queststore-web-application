package com.codecool.idontspeakjava.queststore.main;

import com.codecool.idontspeakjava.queststore.controllers.Static;
import com.codecool.idontspeakjava.queststore.controllers.codecooler.web.WebCodecoolerController;
import com.codecool.idontspeakjava.queststore.controllers.login.WebLoginController;
import com.codecool.idontspeakjava.queststore.controllers.login.*;
import com.codecool.idontspeakjava.queststore.controllers.mentor.web.WebMentorController;
import com.codecool.idontspeakjava.queststore.controllers.root.web.WebAdminController;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8000),0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/login", new WebLoginController());
        server.createContext("/static", new Static());

        server.createContext("/cos", new Test());

        server.createContext("/admin", new WebAdminController());
        server.createContext("/mentor", new WebMentorController());
        server.createContext("/student", new WebCodecoolerController());

        server.setExecutor(null);

        server.start();
    }

}