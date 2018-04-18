package com.codecool.idontspeakjava.queststore.controllers.admin.web;

import j2html.tags.Tag;

import java.util.List;

import static j2html.TagCreator.*;

public class HTMLGenerator {

    public static String generateFormToCreateUser(){

        return div(
                                fieldset(
                                        form().withMethod("post").with(
                                                getLabel("Name"),
                                                getInput("name"),
                                                getLabel("Lastname"),
                                                getInput("lastname"),
                                                getLabel("pasword"),
                                                getPassword(),
                                                getLabel("E-mail"),
                                                getEmail(),
                                                getButton()
                                        )
                                )
                        )
                .render();

    }

    public static Tag getInput(String message){
        return input().withName(message).isRequired();
    }

    public static Tag getEmail(){
        return input().withType("e-mail").withName("email").isRequired();
    }

    public static Tag getButton(){
        return input().withType("submit").withValue("Submit");
    }

    public static Tag getLabel(String text){
        return label(text);
    }

    public static Tag getPassword(){
        return input().withType("password").withName("password").isRequired();
    }

    public static String getRadioForm(List<String> collection){


        String form = "<fieldset> \n"+
                "<form method = \"post\">";


        for(int i = 0;i< collection.size(); i++){
            form += "<label> <input type = \"radio\" name = \"name\" value =\"pupa\" required> "+collection.get(i)+"</label>";
        }

        form += "<input type = \"submit\" value = \"Choose\">" +
                "</form> </fieldset>";

        return form;

    }
}
