package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import com.codecool.idontspeakjava.queststore.controllers.AbstractHandler;
import com.codecool.idontspeakjava.queststore.controllers.helpers.HTMLGenerator;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteCodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.sun.net.httpserver.HttpExchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

public class WebShowMentor extends AbstractHandler {

    @Override
    public void handle(HttpExchange httpExchange){
        String method = httpExchange.getRequestMethod();

        List<User> userCollection = new SQLiteUserDAO().getUsersByPermission(Permissions.Mentor);
        String form = HTMLGenerator.getRadioForm(userCollection, "show mentors", "name");

        if (method.equals("GET")) {
            sendTemplateResponseWithForm(httpExchange, "admin_home", form);
        }

        if (method.equals("POST")) {

            String classForm = getMentorsClassForm(httpExchange, userCollection);
            sendTemplateResponseWithForm(httpExchange, "admin_home", classForm);
        }

    }
    private String getMentorsClassForm(HttpExchange httpExchange, List<User> mentors) {
        User mentor = getChosenMentor(httpExchange, mentors);
        CodecoolClass mentorClass = getMentorsClass(mentor);

        String className = "No class assigned";
        List<String> mentorStudents = asList("No students to show");
        String listStyle = "list-style-type: none";
        if(mentorClass != null) {
            className = mentorClass.getName();
            mentorStudents = getClassStudentsList(mentorClass);
            listStyle = "list-style-type: decimal";
        }

        return HTMLGenerator.getList(mentorStudents, className, listStyle);
    }
    private CodecoolClass getMentorsClass(User mentor){
        CodecoolClass mentorClass = null;
        try {
            mentorClass = new SQLiteCodecoolClassDAO().getUserCodecoolClass(mentor);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return mentorClass;
    }

    private User getChosenMentor(HttpExchange httpExchange, List<User> users) {
        Map<String, String> data = readFormData(httpExchange);
        String name = data.get("name");

        User chosenUser = null;
        for (User user : users) {
            if (user.toString().equals(name)) {
                chosenUser = user;
                break;
            }
        }
        return chosenUser;
    }

    private List<String> getClassStudentsList(CodecoolClass mentorClass) {
        List<User> allStudents = new SQLiteCodecoolClassDAO().getClassStudents(mentorClass);
        List<String> studentsInfo = new ArrayList<>();

        for (User pupil : allStudents) {
            studentsInfo.add(pupil.toString());
        }
        return studentsInfo;

    }
}
