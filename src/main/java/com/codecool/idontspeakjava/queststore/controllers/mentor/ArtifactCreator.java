package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.models.DummyItem;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.List;
import java.util.stream.Collectors;


class ArtifactCreator extends Creator {

    private static final int TITLE = 0;
    private static final int CATEGORY = 1;
    private static final int DESCRIPTION = 2;
    private static final int PRICE = 3;

    private DummyItem temporaryArtifact;

    ArtifactCreator(MentorView view) {
        super(view);
        temporaryArtifact = new DummyItem();
    }

    @Override
    void addToDatabase() {
        Artifact artifact = new Artifact(
                temporaryArtifact.getTitle(),
                temporaryArtifact.getCategory().equals(Validator.BASIC_CATEGORY) ? ArtifactCategory.Basic : ArtifactCategory.Magic,
                temporaryArtifact.getDescription(),
                temporaryArtifact.getRewardOrPrice());

        new SQLiteArtifactsDAO().createArtifact(artifact);
        view.showArtifactCreated();
    }

    @Override
    void selectPrompt(int promptNumber) {
        switch (promptNumber) {
            case TITLE:
                view.askForArtifactTitle();
                break;
            case CATEGORY:
                view.askForArtifactCategory();
                break;
            case DESCRIPTION:
                view.askForArtifactDescription();
                break;
            case PRICE:
                view.askForArtifactPrice();
                break;
        }
    }

    @Override
    boolean setAttribute(int promptNumber, String input) {
        boolean attributeSet;
        switch (promptNumber) {
            case TITLE:
                attributeSet = setTitle(temporaryArtifact, input, getArtifactsTitles(), view);
                break;
            case CATEGORY:
                attributeSet = setCategory(temporaryArtifact, input, view);
                break;
            case DESCRIPTION:
                attributeSet = setDescription(temporaryArtifact, input, view);
                break;
            case PRICE:
                attributeSet = setPriceOrReward(temporaryArtifact, input, view);
                break;
            default:
                attributeSet = false;
        }
        return attributeSet;
    }

    private List<String> getArtifactsTitles() {
        List<Artifact> artifacts = new SQLiteArtifactsDAO().getAllArtifacts();
        return artifacts.stream().map(Artifact::getTitle).collect(Collectors.toList());
    }
}