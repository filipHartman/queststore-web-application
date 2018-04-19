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
                                                getButton("Submit")
                                        )
                                ).with(getLegend(methodName))
                        ).withClass("form-style")
                .render();

    }

    public static String generateFormToEditMail(String methodName, List <?> collection){

        String button = getButton("Submit").render();
        return
                        form().withMethod("post").with(
                                getLabel("Email"),
                                getEmail()
                        )
        .render();
    }

    public static String generateFromWith1Field(String methodName, String label) {
        return div(
                fieldset(
                        form().withMethod("post").with(
                                getLabel(label),
                                getInput("name"),
                                getButton("Submit")
                        )
                ).with(getLegend(methodName))
                ).withClass("form-style")
                .render();
    }

    public static String generateFormToCreateLevel(String methodName){
        return div(
                fieldset(
                        form().withMethod("post").with(
                                getLabel("Level name"),
                                getInput("name"),
                                getLabel("Level threshold"),
                                getLevel(),
                                getButton("Submit")
                        )
                ).with(getLegend(methodName))
        ).withClass("form-style").render();
    }

    public static Tag getLevel(){
        return input().withClass("input-field").withName("threshold").withType("number").withPlaceholder("0").withValue("Submit").isRequired();
    }


    public static Tag getInput(String message){
        return input().withClass("input-field").withName(message).isRequired();
    }

    public static Tag getEmail(){
        return input().withClass("input-field").withType("e-mail").withName("email").isRequired();
    }

    public static Tag getButton(String value){
        return input().withClass("input-field").withType("submit").withValue(value);
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

    public static String getFormToEditMail(List <?> collection){

        String form = "<fieldset> \n"+
                "<form method = \"post\">";

        for(int i = 0;i< collection.size(); i++){
            form += "<label> <input type = \"radio\" name = \"name\" value =\""+collection.get(i).toString()+"\" required> "+collection.get(i).toString()+"</label>";
        }

        form += "<input type = \"email\" name = \"email\">";

        form += "<input type = \"submit\" value = \"Choose\">" +
                "</form> </fieldset>";

        return form;


    }

    public static String getFormToEditClass(List <?> collection, List<?> collection2){

        String form = "<fieldset> \n"+
                "<form method = \"post\">";

        for(int i = 0;i< collection.size(); i++){
            form += "<label> <input type = \"radio\" name = \"name\" value =\""+collection.get(i).toString()+"\" required> "+collection.get(i).toString()+"</label>";
        }

        for(int i = 0;i< collection2.size(); i++){
            form += "<label> <input type = \"radio\" name = \"className\" value =\""+collection2.get(i).toString()+"\" required> "+collection2.get(i).toString()+"</label>";
        }


        form += "<input type = \"submit\" value = \"Choose\">" +
                "</form> </fieldset>";

        return form;


    }

    public static String getList(List<String> collection, String legend){
        return div(
                fieldset(
                        form(ul(each(collection, item ->
                        li(
                                span(item)
                        ))))
                ).with(getLegend(legend))
        ).render();
    }

    public static String getAlertForm(String message) {
        return div(
                fieldset(
                        form().withMethod("post").with(
                                getLabel(message),
                                getButton("OK")
                        )
                )
        ).render();
    }

}
