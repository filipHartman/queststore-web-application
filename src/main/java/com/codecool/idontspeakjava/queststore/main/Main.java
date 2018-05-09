package com.codecool.idontspeakjava.queststore.main;

import com.codecool.idontspeakjava.queststore.controllers.AlertController;
import com.codecool.idontspeakjava.queststore.controllers.Static;
import com.codecool.idontspeakjava.queststore.controllers.admin.web.WebAdminController;
import com.codecool.idontspeakjava.queststore.controllers.codecooler.web.WebCodecoolerController;
import com.codecool.idontspeakjava.queststore.controllers.codecooler.web.WebManageTeam;
import com.codecool.idontspeakjava.queststore.controllers.login.*;

import com.codecool.idontspeakjava.queststore.controllers.mentor.web.WebMentorController;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class Main {

    public static void main(String[] args) {
        HttpServer server = null;
        try {
            server = HttpServer.create(new InetSocketAddress(8888),0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        server.createContext("/", new WebLoginController());
        server.createContext("/static", new Static());
        server.createContext("/alert", new AlertController());
        server.createContext("/admin", new WebAdminController());
        server.createContext("/mentor", new WebMentorController());
        server.createContext("/student", new WebCodecoolerController());
        server.createContext("/team", new WebManageTeam());
        server.createContext("/logout", new WebLogoutController());

        server.setExecutor(null);

        server.start();
    }

}
