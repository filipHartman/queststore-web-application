package com.codecool.idontspeakjava.queststore.mentor;

import com.codecool.idontspeakjava.queststore.controllers.mentor.Validator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {

    @Test
    public void checkIfIsPositiveIntegerShouldReturnFalseWhenInputIsText() {
        assertFalse(new Validator().checkIfIsPositiveInteger("Krowa"));
    }

    @Test
    public void checkIfIsPositiveIntegerShouldReturnFalseWhenInputIsFloat() {
        assertFalse(new Validator().checkIfIsPositiveInteger("1.2"));
    }

    @Test
    public void checkIfIsPositiveIntegerShouldReturnFalseWhenInputIsZero() {
        assertFalse(new Validator().checkIfIsPositiveInteger("0"));
    }

    @Test
    public void checkIfIsPositiveIntegerShouldReturnFalseWhenInputIsBelowZero() {
        assertFalse(new Validator().checkIfIsPositiveInteger("-5"));
    }

    @Test
    public void checkIfIsPositiveIntegerShouldReturnTrueWhenInputIsAnIntegerAboveZero() {
        assertTrue(new Validator().checkIfIsPositiveInteger("5"));
    }

    @Test
    public void checkIfDescriptionIsValidShouldReturnFalseWhenHaveCharacterOtherThanDotSpaceDigitsLettersOrComma() {
        assertFalse(new Validator().checkIfDescriptionIsValid("Ultra super, test % .."));
    }

    @Test
    public void checkIfDescriptionIsValidShouldReturnTrueWhenDescriptionDontHaveOtherCharsThanDotSpaceOrComma() {
        assertTrue(new Validator().checkIfDescriptionIsValid("This is, a, correct description. Probably."));
    }

    @Test
    public void checkIfTitleIsValidShouldReturnFalseWhenHaveCharsOtherThanLetters() {
        List<String> titles = Arrays.asList("Title", "Title 2", "Title 3");
        assertFalse(new Validator().checkIfTitleIsValid("Title 4 !", titles));
    }

    @Test
    public void checkIfTitleIsValidShouldReturnFalseWhenTitleExists() {
        List<String> titles = Arrays.asList("Title", "Title 2", "Title 3");
        assertFalse(new Validator().checkIfTitleIsValid("Title 2", titles));
    }

    @Test
    public void checkIfTitleIsValidShouldReturnTrueWhenHaveLettersNumbersSpacesAndIsUnique() {
        List<String> titles = Arrays.asList("Title", "Title 2", "Title 3");
        assertTrue(new Validator().checkIfTitleIsValid("Title 4", titles));
    }
}