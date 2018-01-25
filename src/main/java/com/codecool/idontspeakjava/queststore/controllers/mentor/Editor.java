package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.ArtifactsDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.Arrays;
import java.util.List;

abstract class Editor {

    private static final String EXIT = "0";
    private String[] availableOptions = {"1", "2", "3", "4"};

    MentorView view;
    private Validator validator;
    int indexOfItemToEdit;

    Editor(MentorView view) {
        this.view = view;
        this.validator = new Validator();
    }

    void editTitle(Artifact artifact, List<String> titles, String newTitle) {
        if (validator.checkIfTitleIsValid(newTitle, titles)) {
            artifact.setTitle(newTitle);
            new ArtifactsDAO().updateArtifact(artifact);
        } else {
            view.showWrongTitleInput();
        }
    }

    void editPrice(Artifact artifact, String input) {
        if (validator.checkIfPriceOrRewardIsValid(input)) {
            artifact.setPrice(Integer.parseInt(input));
            new ArtifactsDAO().updateArtifact(artifact);
        } else {
            view.showWrongDigitInput();
        }
    }

    void editDescription(Artifact artifact, String input) {
        if (validator.checkIfDescriptionIsValid(input)) {
            artifact.setDescription(input);
            new ArtifactsDAO().updateArtifact(artifact);
        } else {
            view.showWrongDescriptionInput();
        }
    }

    void editCategory(Artifact artifact, String input) {
        if (validator.checkIfCategoryIsValid(input)) {
            ArtifactCategory category = input.equals(Validator.BASIC_CATEGORY) ? ArtifactCategory.Basic : ArtifactCategory.Magic;
            artifact.setCategory(category);
            new ArtifactsDAO().updateArtifact(artifact);
        } else {
            view.showWrongInput();
        }
    }

    void edit() {
        boolean continueEdit = true;

        if (getCollection().isEmpty()) {
            view.showNoArtifacts();
            return;
        }
        while (continueEdit) {
            view.selectArtifacts(getTitles());
            String input = view.getUserInput();

            if (input.equals(EXIT)) {
                continueEdit = false;
                view.showOperationCancelled();
            } else if (validator.isSelectFromListInvalid(getCollection(), input)) {
                view.showWrongInput();
            } else {
                indexOfItemToEdit = Integer.parseInt(input) - 1;
                view.selectAttributeOfArtifactToEdit();
                input = view.getUserInput();

                if (input.equals(EXIT)) {
                    view.showOperationCancelled();
                    continueEdit = false;
                } else {
                    selectPrompt(input);
                    if (Arrays.asList(availableOptions).contains(input)) {
                        String selectedOption = input;
                        input = view.getUserInput();
                        if (!input.equals(EXIT)) {
                            editAttribute(selectedOption, input);
                        }
                    }
                }
            }
        }
    }

    abstract void selectPrompt(String input);

    abstract void editAttribute(String selectedOption, String input);

    abstract List getCollection();

    abstract List<String> getTitles();
}
