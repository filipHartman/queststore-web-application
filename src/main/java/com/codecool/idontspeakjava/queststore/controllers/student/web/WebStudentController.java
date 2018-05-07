package com.codecool.idontspeakjava.queststore.controllers.student.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.admin.web.*;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.sun.net.httpserver.HttpExchange;

public class WebStudentController extends AbstractHandler{

    @Override
    public void handle(HttpExchange httpExchange) {
        if (checkPermission(httpExchange, Permissions.Student)) {
            try {
                redirectToActionHandler(httpExchange, getAction(httpExchange));
            } catch (IndexOutOfBoundsException e) {
                sendTemplateResponse(httpExchange, "student_home");
            }
        } else {
            redirectToLocation(httpExchange, "/");
        }
    }
    
}
