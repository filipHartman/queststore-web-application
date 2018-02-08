package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.models.DummyItem;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.List;

abstract class Creator {

    private static final String EXIT = "0";

    MentorView view;
    private Validator validator;

    Creator(MentorView view) {
        this.view = view;
        validator = new Validator();
    }

    boolean setTitle(DummyItem dummy, String input, List<String> titles, MentorView view) {
        if (validator.checkIfTitleIsValid(input, titles)) {
            dummy.setTitle(input);
            return true;
        } else {
            view.showWrongTitleInput();
            return false;
        }
    }

    boolean setCategory(DummyItem dummy, String input, MentorView view) {
        if (validator.checkIfCategoryIsValid(input)) {
            dummy.setCategory(input);
            return true;
        } else {
            view.showWrongInput();
            return false;
        }
    }

    boolean setDescription(DummyItem dummy, String input, MentorView view) {
        if (validator.checkIfDescriptionIsValid(input)) {
            dummy.setDescription(input);
            return true;
        } else {
            view.showWrongDescriptionInput();
            return false;
        }
    }

    boolean setPriceOrReward(DummyItem dummy, String input, MentorView view) {
        if (validator.checkIfIsPositiveInteger(input)) {
            dummy.setRewardOrPrice(input);
            return true;
        } else {
            view.showWrongDigitInput();
            return false;
        }
    }

    void create() {
        int inputsReceived = 0;
        boolean continueLoop = true;

        final int PROMPTS = 4;
        for (int i = 0; i < PROMPTS && continueLoop; i++) {
            boolean continueIteration = true;
            while (continueIteration) {
                view.clearScreen();
                selectPrompt(i);
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
            addToDatabase();
        } else {
            view.showOperationCancelled();
        }
    }

    abstract void selectPrompt(int promptNumber);

    abstract void addToDatabase();

    abstract boolean setAttribute(int promptNumber, String input);
}
