package com.codecool.idontspeakjava.queststore.controllers.login;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.IOException;
import java.io.OutputStream;

public class WebLoginController implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) {
        JtwigTemplate template = JtwigTemplate.classpathTemplate("templates/login.twig");
        JtwigModel model = JtwigModel.newModel();

        String response = template.render(model);

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
}
