package com.codecool.idontspeakjava.queststore.controllers.helpers;

import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import j2html.tags.Tag;

import java.util.List;
import java.util.Map;

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
        return input().withClass("input-field").withName("threshold").attr("min", 0).withType("number").withPlaceholder("0").withValue("Submit").isRequired();
    }


    public static Tag getInput(String message){
        return input().withType("text").withClass("input-field").withName(message).isRequired();
    }

    public static Tag getNumberImput(String message) {
        return input().withType("number")
                .withName(message)
                .withClass("input-field")
                .attr("min", 0)
                .isRequired();
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

    public static String getList(List<String> collection, String legend, String listStyle){
        return div(
                fieldset(
                        form(ol(each(collection, item ->
                        li(
                                span(item)
                        ))).withStyle(listStyle)
                        )
                ).with(getLegend(legend))
        ).withClass("form-style").render();
    }

    public static String getAlertForm(String message) {
        return div(
                fieldset(
                        form().withMethod("post").with(
                                label().with(span(message)),
                                getButton("OK")
                        )
                )
        ).withClass("form-style")
         .render();
    }

    public static String generateFormToAddQuest() {
        return div(
                fieldset(
                        form().withMethod("post").with(
                                label().with(span("Title")).with(getInput("title")),

                                label().with(span("Description")).with(getInput("description")),

                                label().with(span("Reward")).with(getNumberImput("reward")),

                                label().with(span("Choose category")).withClass("subtitle"),
                                label().with(span("Basic")).with(getRadioInput("category", QuestCategory.Basic.toString())),
                                label().with(span("Extra")).with(getRadioInput("category", QuestCategory.Extra.toString())),
                                getButton("Add quest")
                        )
                ).with(getLegend("Add quest"))
        ).withClass("form-style")
         .render();
    }

    public static String generateFormToAddArtifact() {
        return div(
                fieldset(
                        form().withMethod("post").with(
                                label().with(span("Title")).with(getInput("title")),

                                label().with(span("Description")).with(getInput("description")),

                                label().with(span("Price")).with(getNumberImput("price")),

                                label().with(span("Choose category")).withClass("subtitle"),
                                label().with(span("Basic")).with(getRadioInput("category", ArtifactCategory.Basic.toString())),
                                label().with(span("Magic")).with(getRadioInput("category", ArtifactCategory.Magic.toString())),
                                getButton("Add artifact")
                        )
                ).with(getLegend("Add artifact"))
        ).withClass("form-style")
         .render();
    }

    public static String generateFormToEditQuestOrArtifact(List<?> collection, String priceOrReward, String Category, String legend) {
        return div(
                fieldset(label().with(span("Choose quest")).withClass("subtitle"),
                        form(each(collection, item ->
                                label().with(getRadioInput("quest", item.toString()))
                                       .with(span(item.toString()))
                        ),
                                label().with(span(legend)).withClass("subtitle"),
                                label().with(span("Title")).with(getInput("title")),
                                label().with(span("Description")).with(getInput("description")),
                                label().with(span(priceOrReward)).with(getInput(priceOrReward).withType("number")),
                                label().with(span("Choose category")).withClass("subtitle"),
                                label().with(span("Basic")).with(getRadioInput("category", "Basic")),
                                label().with(span(Category)).with(getRadioInput("category", Category))
                ).with(getButton("Submit")).withMethod("post")
                ).with(getLegend(legend))
        ).withClass("form-style").render();
    }


    public static String generateFormToBuyArtifactWithTeam(List <?> collection, String legend, String name ){
        return div(
                fieldset(label().with(span("Choose artifact")).withClass("subtitle"),
                        form(each(collection, item ->
                                        label().with(getRadioInput("artifact", item.toString()))
                                                .with(span(item.toString()))
                                ),
                                label().with(span("Contribution")).withClass("subtitle"),
                                label().with(span(name)).with(getInput(name).withType("contribution"))
                        ).with(getButton("Submit")).withMethod("post")
                ).with(getLegend(legend))
        ).withClass("form-style").render();

    }
}
