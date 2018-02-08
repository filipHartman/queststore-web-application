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
        boolean inputIsValid = false;
        if (input.matches("\\d+")) {
            if (Integer.valueOf(input) > 0) {
                inputIsValid = true;
            }
        }
        return inputIsValid;
    }

    public boolean checkIfDescriptionIsValid(String input) {
        return input.matches("[a-zA-Z1-9,. ]+");
    }

    public boolean checkIfTitleIsValid(String input, List<String> titles) {
        return input.matches("[a-zA-Z1-9 ]+") && !titles.contains(input);
    }

    public boolean checkIfCategoryIsValid(String input) {
        return Arrays.asList(new String[]{BASIC_CATEGORY, EXTRA_CATEGORY}).contains(input);
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
        return input.matches("[a-zA-Z]+");
    }

    public boolean checkIfEmailIsValid(String input, List<String> emails) {
        return input.matches(EMAIL_REGEX) && !emails.contains(input);
    }
}
