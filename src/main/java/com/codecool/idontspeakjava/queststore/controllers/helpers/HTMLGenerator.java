package com.codecool.idontspeakjava.queststore.controllers.helpers;

import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import j2html.tags.Tag;

import java.util.List;

import static j2html.TagCreator.*;

public class HTMLGenerator {

    public static String generateFormToCreateUser(String methodName){

        return div(
                                fieldset(
                                        form().withMethod("post").with(
                                                label().with(span("Name")).with(getInput("name")),
                                                label().with(span("Lastname")).with(getInput("lastname")),
                                                label().with(span("Password")).with(getPassword()),
                                                label().with(span("E-mail")).with(getEmail()),
                                                getButton("Submit")
                                        )
                                ).with(getLegend(methodName))
                        ).withClass("form-style")
                .render();

    }

    public static String generateFromWith1Field(String methodName, String label) {
        return div(
                fieldset()

                .with(form()
                        .with(getLegend(methodName))
                        .withMethod("post").with(
                        label().with(span(label)).with(getInput("name"))),
                        getButton("Submit"))
                .withClass("form-style"))
                .render();
    }

    public static String generateFormToCreateLevel(String methodName){
        return div(
                fieldset(
                        form().withMethod("post").with(
                                label().with(span("Level name")).with(getInput("name")),
                               
                                label().with(span("Level threshold")).with(getLevel()),

                                getButton("Submit")
                        )
                ).with(getLegend(methodName))
        ).withClass("form-style").render();
    }

    public static Tag getLevel(){
        return input().withClass("input-field").withName("threshold").withType("number").withPlaceholder("0").withValue("Submit").isRequired();
    }


    public static Tag getInput(String message){
        return input().withType("text").withClass("input-field").withName(message).isRequired();
    }

    public static Tag getRadioInput(String name, String value) {
        return input().withType("radio").withName(name).withValue(value).isRequired();
    }

    public static Tag getEmail(){
        return input().withClass("input-field").withType("email").withName("email").isRequired();
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

    public static String getRadioForm(List <?> collection, String legend, String name){
        return div(
                fieldset(
                        form(each(collection, item ->
                                label()
                                  .with(getRadioInput(name, item.toString()))
                                  .with(span(item.toString()))
                                )).with(getButton("Submit"))
                                .withMethod("post")
                ).with(getLegend(legend))
        ).withClass("form-style").render();
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
                              label().with(span(subTitle)).withClass("subtitle"),
                              each(collection, item ->
                                      label()
                                              .with(getRadioInput(name, item.toString()))
                                              .with(span(item.toString()))
                                ),
                                label().with(span(subTitle2)).withClass("subtitle"),
                                each(collection2, item ->
                                        label()
                                                .with(getRadioInput(name2, item.toString()))
                                                .with(span(item.toString()))
                                )
                        ).with(getButton("Submit"))
                         .withMethod("post")
                ).with(getLegend(legend))
        ).withClass("form-style").render();
    }

    public static String getFormToEditMail(List <?> collection, String legend, String subTitle, String subTitle2, String name){

        return div(
                fieldset(
                        form(
                                label().with(span(subTitle)).withClass("subtitle"),
                                each(collection, item ->
                                        label().with(getRadioInput(name, item.toString()))
                                               .with(span(item.toString()))
                                ),
                                label().with(span(subTitle2)).withClass("subtitle"),
                                label().with(span("E-mail")).with(getEmail())
                        ).with(getButton("Submit"))
                                .withMethod("post")
                ).with(getLegend(legend))
        ).withClass("form-style").render();



    }

    public static String getList(List<String> collection, String legend){
        return div(
                fieldset(
                        form(ul(each(collection, item ->
                        li(
                                span(item)
                        ))))
                ).with(getLegend(legend))
        ).withClass("form-style").render();
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

    public static String generateFormToAddQuest() {
        return div(
                fieldset(
                        form().withMethod("post").with(
                                getLabel("Title"),
                                getInput("title"),
                                getLabel("Description"),
                                getInput("description"),
                                getLabel("Reward"),
                                getInput("reward").withType("number"),
                                getLabel("Choose category"),
                                getLabel("Basic"),
                                getRadioInput("category", QuestCategory.Basic.toString()),
                                getLabel("Extra"),
                                getRadioInput("category", QuestCategory.Extra.toString()),
                                getButton("Add quest")
                        )
                ).with(getLegend("Add quest"))
        ).render();
    }

    public static String generateFormToAddArtifact() {
        return div(
                fieldset(
                        form().withMethod("post").with(
                                getLabel("Title"),
                                getInput("title"),
                                getLabel("Description"),
                                getInput("description"),
                                getLabel("Price"),
                                getInput("price").withType("number"),
                                getLabel("Choose category"),
                                getLabel("Basic"),
                                getRadioInput("category", ArtifactCategory.Basic.toString()),
                                getLabel("Magic"),
                                getRadioInput("category", ArtifactCategory.Magic.toString()),
                                getButton("Add artifact")
                        )
                ).with(getLegend("Add artifact"))
        ).render();
    }
}
