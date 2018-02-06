package com.codecool.idontspeakjava.queststore.controllers.mentor;

import java.util.Arrays;
import java.util.List;

public class Validator {

    public final static String BASIC_CATEGORY = "1";
    private final static String EXTRA_CATEGORY = "2";

    public boolean checkIfIsPositiveInteger(String input) {
        boolean priceIsValid = false;
        if (input.matches("\\d+")) {
            if (Integer.valueOf(input) > 0) {
                priceIsValid = true;
            }
        }
        return priceIsValid;
    }

    public boolean checkIfDescriptionIsValid(String input) {
        boolean descriptionIsValid = false;
        if (input.matches("[a-zA-Z1-9,.! ]+")) {
            descriptionIsValid = true;
        }
        return descriptionIsValid;
    }

    public boolean checkIfTitleIsValid(String input, List<String> titles) {
        boolean titleIsValid = false;
        if (input.matches("[a-zA-Z1-9 ]+")) {
            if (!titles.contains(input)) {
                titleIsValid = true;
            }
        }
        return titleIsValid;
    }

    public boolean checkIfCategoryIsValid(String input) {
        String[] validInputs = {BASIC_CATEGORY, EXTRA_CATEGORY};
        return Arrays.asList(validInputs).contains(input);
    }

    public boolean isSelectFromListInvalid(List collection, String input) {
        boolean inputIsInvalid = true;
        if (input.matches("\\d+")) {
            if (Integer.parseInt(input) <= collection.size()) {
                inputIsInvalid = false;
            }
        }
        return inputIsInvalid;
    }

    public boolean checkIfNameIsValid(String input) {
        boolean nameIsValid = false;
        if (input.matches("[a-zA-Z]+")) {
            nameIsValid = true;
        }
        return nameIsValid;
    }
}
