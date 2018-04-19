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

        String button = getButton().render();
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
                                getButton()
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
                                getButton()
                        )
                ).with(getLegend(methodName))
        ).withClass("form-style").render();
    }

    public static Tag getLevel(){
        return input().withClass("input-field").withName("threshold").withType("number").withPlaceholder("0").withValue("Submit");
    }


    public static Tag getInput(String message){
        return input().withClass("input-field").withName(message).isRequired();
    }

    public static Tag getRadioInput(String name, String value) {
        return input().withType("radio").withName(name).withValue(value);
    }

    public static Tag getEmail(){
        return input().withClass("input-field").withType("email").withName("email").isRequired();
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

    public static String getRadioForm(List <?> collection, String legend, String name){
        return div(
                fieldset(
                        form(each(collection, item ->
                                label(item.toString())
                                        .with(getRadioInput(name, item.toString()))
                                )).with(getButton())
                                .withMethod("post")
                ).with(getLegend(legend))
        ).render();
    }

    public static String getRadioForm(List<?> collection,
                                      List<?> collection2,
                                      String legend,
                                      String subTitle,
                                      String subTitle2,
                                      String name,
                                      String name2) {
        return div(
                fieldset(
                        form(
                              label(subTitle),
                              each(collection, item ->
                                      label(item.toString()).with(getRadioInput(name, item.toString()))
                                ),
                                label(subTitle2),
                                each(collection2, item ->
                                        label(item.toString()).with(getRadioInput(name2, item.toString()))
                                )
                        ).with(getButton())
                         .withMethod("post")
                ).with(getLegend(legend))
        ).render();
    }

    public static String getFormToEditMail(List <?> collection, String legend, String subTitle, String subTitle2, String name){

        return div(
                fieldset(
                        form(
                                label(subTitle),
                                each(collection, item ->
                                        label(item.toString()).with(getRadioInput(name, item.toString()))
                                ),
                                label(subTitle2),
                                getEmail()
                        ).with(getButton())
                                .withMethod("post")
                ).with(getLegend(legend))
        ).render();



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

}
