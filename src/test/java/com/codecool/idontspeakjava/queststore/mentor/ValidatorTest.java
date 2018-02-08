package com.codecool.idontspeakjava.queststore.mentor;

import com.codecool.idontspeakjava.queststore.controllers.mentor.Validator;
import org.junit.Test;

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
    public void checkIfDescriptionIsValidShouldReturnFalseWhenHaveCharacterOtherThanDotSpaceOrComma() {
        assertFalse(new Validator().checkIfDescriptionIsValid("Ultra super, test % .."));
    }

    @Test
    public void checkIfDescriptionIsValidShouldReturnTrueWhenDescriptionDontHaveOtherCharsThanDotSpaceOrComma() {
        assertTrue(new Validator().checkIfDescriptionIsValid("This is, a, correct description. Probably."));
    }
}