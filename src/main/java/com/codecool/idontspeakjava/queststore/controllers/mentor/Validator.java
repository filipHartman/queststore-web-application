package com.codecool.idontspeakjava.queststore.controllers.mentor;

import java.util.Arrays;
import java.util.List;

public class Validator {

    public final static String BASIC_CATEGORY = "1";
    private final static String EXTRA_CATEGORY = "2";

    private static final String EMAIL_REGEX = "(?:[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*|\"(?:" +
            "[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(" +
            "?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]" +
            "?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[" +
            "\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])";

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

    public boolean checkIfEmailIsValid(String input, List<String> emails) {
        boolean emailIsValid = false;
        if (input.matches(EMAIL_REGEX)) {
            if (!emails.contains(input)) {
                emailIsValid = true;
            }
        }
        return emailIsValid;
    }
}
