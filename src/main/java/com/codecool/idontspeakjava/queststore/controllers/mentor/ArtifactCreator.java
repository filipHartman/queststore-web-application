package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.ArtifactsDAO;
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

    private static final String EXIT = "0";
    private MentorView view;
    private DummyItem temporaryArtifact;

    ArtifactCreator(MentorView view) {
        this.view = view;
        temporaryArtifact = new DummyItem();
    }

    @SuppressWarnings("Duplicates")
    void createArtifact() {
        int inputsReceived = 0;
        boolean continueLoop = true;

        final int PROMPTS = 4;

        for (int i = 0; i < PROMPTS && continueLoop; i++) {
            boolean continueIteration = true;
            while (continueIteration) {
                selectPromptForCreateQuest(i);
                String input = view.getUserInput();

                if (input.equals(EXIT)) {
                    continueLoop = false;
                    continueIteration = false;
                } else {
                    continueIteration = !setAttribute(i, input);
                }
            }
            if (continueLoop) {
                inputsReceived++;
            }
        }
        if (inputsReceived == PROMPTS) {
            addArtifactToDatabase();
        } else {
            view.showOperationCancelled();
        }
    }

    private void addArtifactToDatabase() {
        Artifact artifact = new Artifact(
                temporaryArtifact.getTitle(),
                temporaryArtifact.getCategory().equals(BASIC_CATEGORY) ? ArtifactCategory.Basic : ArtifactCategory.Magic,
                temporaryArtifact.getDescription(),
                temporaryArtifact.getRewardOrPrice());

        new ArtifactsDAO().createArtifact(artifact);
        view.showArtifactCreated();
    }

    private void selectPromptForCreateQuest(int promptNumber) {
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

    private boolean setAttribute(int promptNumber, String input) {
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
        List<Artifact> artifacts = new ArtifactsDAO().getAllArtifacts();
        return artifacts.stream().map(Artifact::getTitle).collect(Collectors.toList());
    }
}