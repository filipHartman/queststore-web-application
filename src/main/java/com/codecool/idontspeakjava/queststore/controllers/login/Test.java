package com.codecool.idontspeakjava.queststore.controllers.login;

import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

public class Test implements HttpHandler {

    public void handle(HttpExchange httpExchange) throws IOException {


        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/test.twig");
        JtwigModel model = JtwigModel.newModel();

        String response = template.render(model);

        byte[] bytes = response.getBytes();

        httpExchange.sendResponseHeaders(200, bytes.length);
        OutputStream os = httpExchange.getResponseBody();
        os.write(bytes);
        os.close();


    }

}
