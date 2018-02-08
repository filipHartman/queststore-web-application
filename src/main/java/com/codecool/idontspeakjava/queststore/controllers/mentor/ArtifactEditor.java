package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.List;
import java.util.stream.Collectors;

class ArtifactEditor extends Editor {

    private static final String EDIT_TITLE = "1";
    private static final String EDIT_DESCRIPTION = "2";
    private static final String EDIT_CATEGORY = "3";
    private static final String EDIT_PRICE = "4";

    private List<Artifact> artifacts;

    ArtifactEditor(MentorView view) {
        super(view, "Artifact");
        artifacts = new SQLiteArtifactsDAO().getAllArtifacts();
    }

    @Override
    void selectPrompt(String input) {
        Artifact artifact = artifacts.get(indexOfItemToEdit);
        switch (input) {
            case EDIT_TITLE:
                view.showTitle(artifact.getTitle());
                view.askForArtifactTitle();
                break;
            case EDIT_CATEGORY:
                view.showCategory(artifact.getCategory().toString());
                view.askForArtifactCategory();
                break;
            case EDIT_DESCRIPTION:
                view.showDescription(artifact.getDescription());
                view.askForArtifactDescription();
                break;
            case EDIT_PRICE:
                view.showPrice(String.valueOf(artifact.getPrice()));
                view.askForArtifactPrice();
                break;
            default:
                view.showWrongInput();
        }
    }

    @Override
    void editAttribute(String selectedOption, String input) {
        Artifact artifact = artifacts.get(indexOfItemToEdit);
        List<String> titles = getTitles();

        switch (selectedOption) {
            case EDIT_TITLE:
                editTitle(artifact, titles, input);
                break;
            case EDIT_CATEGORY:
                editCategory(artifact, input);
                break;
            case EDIT_DESCRIPTION:
                editDescription(artifact, input);
                break;
            case EDIT_PRICE:
                editPrice(artifact, input);
                break;
        }
    }

    @Override
    public List getCollection() {
        return artifacts;
    }

    @Override
    public List<String> getTitles() {
        return artifacts.stream().map(Artifact::getTitle).collect(Collectors.toList());
    }
}
