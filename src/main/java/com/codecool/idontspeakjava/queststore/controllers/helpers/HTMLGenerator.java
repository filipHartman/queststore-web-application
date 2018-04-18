package com.codecool.idontspeakjava.queststore.controllers.helpers;

import j2html.tags.Tag;

import java.util.List;

import static j2html.TagCreator.*;

public class HTMLGenerator {

    public static String generateFormToCreateUser(String methodName){

        return div(
                                fieldset(
                                        form().withMethod("post").with(
                                                getLabel("Name"),
                                                getInput("name"),
                                                getLabel("Lastname"),
                                                getInput("lastname"),
                                                getLabel("password"),
                                                getPassword(),
                                                getLabel("E-mail"),
                                                getEmail(),
                                                getButton()
                                        )
                                ).with(getLegend(methodName))
                        ).withClass("form-style")
                .render();

    }

    public static String generateFormToEditMail(String methodName, List <?> collection){
        return
                        form().withMethod("post").with(
                                getLabel("Email"),
                                getEmail()
                        )
        .render() + getRadioFormNoButton(collection);
    }

    public static String generateFromWith1Field(String methodName, String label) {
        return div(
                fieldset(
                        form().withMethod("post").with(
                                getLabel(label),
                                getInput("name"),
                                getButton()
                        )
                ).with(getLegend(methodName))
                ).withClass("form-style")
                .render();
    }

    public static Tag getLevel(){
        return input().withClass("input-field").withName("threshold").withType("number").withPlaceholder("0").withValue("Submit");
    }


    public static Tag getInput(String message){
        return input().withClass("input-field").withName(message).isRequired();
    }

    public static Tag getEmail(){
        return input().withClass("input-field").withType("e-mail").withName("email").isRequired();
    }

    public static Tag getButton(){
        return input().withClass("input-field").withType("submit").withValue("Submit");
    }

    public static Tag getLabel(String text){
        return label().with(span(text));
    }

    public static Tag getPassword(){
        return input().withClass("input-field").withType("password").withName("password").isRequired();
    }

    public static Tag getLegend(String text) { return legend(text);}

    public static String getRadioForm(List <?> collection){

        String form = "<fieldset> \n"+
                "<form method = \"post\">";


        for(int i = 0;i< collection.size(); i++){
            form += "<label> <input type = \"radio\" name = \"name\" value =\""+collection.get(i).toString()+"\" required> "+collection.get(i).toString()+"</label>";
        }

        form += "<input type = \"submit\" value = \"Choose\">" +
                "</form> </fieldset>";

        return form;

    }

    public static String getRadioFormNoButton(List <?> collection){

        String form = "";
        for(int i = 0;i< collection.size(); i++){
            form += "<label> <input type = \"radio\" name = \"name\" value =\""+collection.get(i).toString()+"\" required> "+collection.get(i).toString()+"</label>";
        }

        return form;

    }
}
