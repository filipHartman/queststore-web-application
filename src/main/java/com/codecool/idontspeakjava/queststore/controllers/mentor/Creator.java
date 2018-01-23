package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.models.DummyItem;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.Arrays;
import java.util.List;

abstract class Creator {

    final static String BASIC_CATEGORY = "1";
    private final static String EXTRA_CATEGORY = "2";

    private boolean checkIfPriceOrRewardIsValid(String input) {
        boolean priceIsValid = false;
        if (input.matches("\\d+")) {
            if (Integer.valueOf(input) > 0) {
                priceIsValid = true;
            }
        }
        return priceIsValid;
    }

    private boolean checkIfDescriptionIsValid(String input) {
        boolean descriptionIsValid = false;
        if (input.matches("[a-zA-Z1-9,.! ]+")) {
            descriptionIsValid = true;
        }
        return descriptionIsValid;
    }

    private boolean checkIfTitleIsValid(String input, List<String> titles) {
        boolean titleIsValid = false;
        if (input.matches("[a-zA-Z1-9 ]+")) {
            if (!titles.contains(input)) {
                titleIsValid = true;
            }
        }
        return titleIsValid;
    }

    private boolean checkIfCategoryIsValid(String input) {
        String[] validInputs = {BASIC_CATEGORY, EXTRA_CATEGORY};
        return Arrays.asList(validInputs).contains(input);
    }

    boolean setTitle(DummyItem dummy, String input, List<String> titles, MentorView view) {
        if (checkIfTitleIsValid(input, titles)) {
            dummy.setTitle(input);
            return true;
        } else {
            view.showWrongTitleInput();
            return false;
        }
    }

    boolean setCategory(DummyItem dummy, String input, MentorView view) {
        if (checkIfCategoryIsValid(input)) {
            dummy.setCategory(input);
            return true;
        } else {
            view.showWrongInput();
            return false;
        }
    }

    boolean setDescription(DummyItem dummy, String input, MentorView view) {
        if (checkIfDescriptionIsValid(input)) {
            dummy.setDescription(input);
            return true;
        } else {
            view.showWrongDescriptionInput();
            return false;
        }
    }

    boolean setPriceOrReward(DummyItem dummy, String input, MentorView view) {
        if (checkIfPriceOrRewardIsValid(input)) {
            dummy.setRewardOrPrice(input);
            return true;
        } else {
            view.showWrongDigitInput();
            return false;
        }
    }
}
