package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.ArtifactsDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.sql.SQLException;

class ArtifactCreator {

    private static final int TITLE = 0;
    private static final int CATEGORY = 1;
    private static final int DESCRIPTION = 2;
    private static final int PRICE = 3;

    private static final String EXIT = "0";
    private MentorView view;

    private String title;
    private ArtifactCategory category;
    private String description;
    private int price;

    ArtifactCreator(MentorView view) {
        this.view = view;
    }

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
                    continueIteration = setAttribute(i, input);
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
        Artifact artifact = new Artifact(title, category, description, price);
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
        boolean attributeNotSet;
        switch (promptNumber) {
            case TITLE:
                attributeNotSet = setTitle(input);
                break;
            case CATEGORY:
                attributeNotSet = setCategory(input);
                break;
            case DESCRIPTION:
                attributeNotSet = setDescription(input);
                break;
            case PRICE:
                attributeNotSet = setPrice(input);
                break;
            default:
                attributeNotSet = false;
        }
        return attributeNotSet;
    }

    private boolean setPrice(String input) {
        boolean priceNotSet = true;
        if (input.matches("\\d+")) {
            int inputAsInt = Integer.valueOf(input);
            if (inputAsInt > 0) {
                price = inputAsInt;
                priceNotSet = false;
            } else {
                view.showInputMustBeHigherThanZero();
            }
        } else {
            view.showWrongDigitInput();
        }
        return priceNotSet;
    }

    private boolean setDescription(String input) {
        boolean descriptionNotSet = true;
        if (input.matches("[a-zA-Z1-9,.! ]+")) {
            description = input;
            descriptionNotSet = false;
        } else {
            view.showWrongDescriptionInput();
        }
        return descriptionNotSet;
    }

    private boolean setCategory(String input) {
        final String BASIC = "1";
        final String MAGIC = "2";

        boolean categoryNotSet = false;

        switch (input) {
            case BASIC:
                category = ArtifactCategory.Basic;
                break;
            case MAGIC:
                category = ArtifactCategory.Magic;
                break;
            default:
                categoryNotSet = true;
                view.showWrongInput();
        }
        return categoryNotSet;
    }

    private boolean setTitle(String input) {
        boolean titleNotSet = true;
        if (input.matches("[a-zA-Z1-9 ]+")) {
            try {
                if (!new ArtifactsDAO().checkIfArtifactExists(input)) {
                    title = input;
                    titleNotSet = false;
                } else {
                    view.showDuplicateWarning();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                view.showDatabaseError();
            }
        } else {
            view.showWrongTitleInput();
        }
        return titleNotSet;
    }

}
