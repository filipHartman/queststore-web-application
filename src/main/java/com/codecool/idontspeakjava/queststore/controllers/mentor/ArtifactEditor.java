package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.ArtifactsDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

class ArtifactEditor {

    private static final String EDIT_TITLE = "1";
    private static final String EDIT_DESCRIPTION = "2";
    private static final String EDIT_CATEGORY = "3";
    private static final String EDIT_PRICE = "4";

    private static final String EXIT = "0";

    private MentorView view;
    private List<Artifact> artifacts;
    private Artifact selectedArtifact;

    ArtifactEditor(MentorView view) {
        this.view = view;
        artifacts = new ArtifactsDAO().getAllArtifacts();
    }

    void editArtifact() {
        boolean continueLoop = true;
        if (artifacts.isEmpty()) {
            view.showNoArtifacts();
            return;
        }
        while (continueLoop) {
            view.selectArtifacts(getArtifactsTitles());

            String userInput = view.getUserInput();

            if (isArtifactSelected(userInput)) {
                view.selectAttributeOfArtifactToEdit();
                userInput = view.getUserInput();
                continueLoop = checkUserInput(userInput);
            } else {
                if (userInput.equals(EXIT)) {
                    continueLoop = false;
                    view.showOperationCancelled();
                } else {
                    view.showWrongInput();
                }
            }
        }
    }

    private boolean isArtifactSelected(String input) {
        boolean inputIsValid = false;
        if (input.matches("\\d+")) {
            int inputAsInt = Integer.parseInt(input);
            if (inputAsInt > 0 && inputAsInt <= artifacts.size()) {
                selectedArtifact = artifacts.get(inputAsInt - 1);
                inputIsValid = true;
            }
        }
        return inputIsValid;
    }

    private ArrayList<String> getArtifactsTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (Artifact artifact : artifacts) {
            titles.add(artifact.getTitle());
        }
        return titles;
    }

    private boolean checkUserInput(String input) {
        boolean continueProgram = true;
        switch (input) {
            case EDIT_TITLE:
                view.askForArtifactTitle();
                editTitle(selectedArtifact);
                break;
            case EDIT_DESCRIPTION:
                view.askForArtifactDescription();
                editDescription(selectedArtifact);
                break;
            case EDIT_CATEGORY:
                view.askForArtifactCategory();
                editCategory(selectedArtifact);
                break;
            case EDIT_PRICE:
                view.askForArtifactPrice();
                editPrice(selectedArtifact);
                break;
            case EXIT:
                view.showOperationCancelled();
                continueProgram = false;
                break;
            default:
                view.showWrongInput();
        }
        return continueProgram;
    }

    private void editPrice(Artifact artifact) {
        view.showPrice(String.valueOf(artifact.getPrice()));
        String newPrice = view.getUserInput();
        if (newPrice.equals(EXIT)) {
            view.showOperationCancelled();
            return;
        }
        if (newPrice.matches("\\d+")) {
            int inputAsInt = Integer.valueOf(newPrice);
            if (inputAsInt > 0) {
                artifact.setPrice(inputAsInt);
                new ArtifactsDAO().updateArtifact(artifact);
            } else {
                view.showInputMustBeHigherThanZero();
            }
        } else {
            view.showWrongDigitInput();
        }
    }

    private void editDescription(Artifact artifact) {
        view.showDescription(artifact.getDescription());
        String newDescription = view.getUserInput();
        if (newDescription.equals(EXIT)) {
            view.showOperationCancelled();
            return;
        }
        if (newDescription.matches("[a-zA-Z1-9,.! ]+")) {
            artifact.setDescription(newDescription);
            new ArtifactsDAO().updateArtifact(artifact);
        } else {
            view.showWrongDescriptionInput();
        }
    }

    private void editCategory(Artifact artifact) {
        final String BASIC = "1";
        final String MAGIC = "2";
        view.showCategory(artifact.getCategory().toString());
        String newCategory = view.getUserInput();
        if (newCategory.equals(EXIT)) {
            view.showOperationCancelled();
            return;
        }
        ArtifactsDAO artifactsDAO = new ArtifactsDAO();
        switch (newCategory) {
            case BASIC:
                artifact.setCategory(ArtifactCategory.Basic);
                artifactsDAO.updateArtifact(artifact);
                break;
            case MAGIC:
                artifact.setCategory(ArtifactCategory.Magic);
                artifactsDAO.updateArtifact(artifact);
                break;
            default:
                view.showWrongInput();
        }
    }

    private void editTitle(Artifact artifact) {
        view.showTitle(artifact.getTitle());
        String newTitle = view.getUserInput();
        if (newTitle.equals(EXIT)) {
            view.showOperationCancelled();
            return;
        }
        if (newTitle.matches("[a-zA-Z1-9 ]+")) {
            try {
                ArtifactsDAO artifactsDAO = new ArtifactsDAO();
                if (!artifactsDAO.checkIfArtifactExists(newTitle)) {
                    artifact.setTitle(newTitle);
                    artifactsDAO.updateArtifact(artifact);
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
    }
}
