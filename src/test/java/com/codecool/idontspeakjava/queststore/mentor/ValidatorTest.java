package com.codecool.idontspeakjava.queststore.mentor;

import com.codecool.idontspeakjava.queststore.controllers.mentor.Validator;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ValidatorTest {

    @Test
    public void checkIfIsPositiveInteger_InputHaveOnlyLetters_ReturnFalse() {
        assertFalse(new Validator().checkIfIsPositiveInteger("Krowa"));
    }

    @Test
    public void checkIfIsPositiveInteger_InputIsFloat_ReturnFalse() {
        assertFalse(new Validator().checkIfIsPositiveInteger("1.2"));
    }

    @Test
    public void checkIfIsPositiveInteger_InputIsZero_ReturnFalse() {
        assertFalse(new Validator().checkIfIsPositiveInteger("0"));
    }

    @Test
    public void checkIfIsPositiveInteger_InputBelowZero_ReturnFalse() {
        assertFalse(new Validator().checkIfIsPositiveInteger("-5"));
    }

    @Test
    public void checkIfIsPositiveInteger_InputIsIntegerAboveZero_ReturnTrue() {
        assertTrue(new Validator().checkIfIsPositiveInteger("5"));
    }

    @Test
    public void checkIfDescriptionIsValid_HaveCharsBesideSpaceDigitLetterDotComma_ReturnFalse() {
        assertFalse(new Validator().checkIfDescriptionIsValid("Ultra super, test % .."));
    }

    @Test
    public void checkIfDescriptionIsValid_DoNotHaveCharsBesideSpaceDigitLetterDotComma_ReturnTrue() {
        assertTrue(new Validator().checkIfDescriptionIsValid("This is, a, correct description. Probably."));
    }

    @Test
    public void checkIfTitleIsValid_HaveCharsOtherThanLettersOrSpace_ReturnFalse() {
        List<String> titles = Arrays.asList("Title", "Title 2", "Title 3");
        assertFalse(new Validator().checkIfTitleIsValid("Title 4 !", titles));
    }

    @Test
    public void checkIfTitleIsValid_TitleExists_ReturnFalse() {
        List<String> titles = Arrays.asList("Title", "Title 2", "Title 3");
        assertFalse(new Validator().checkIfTitleIsValid("Title 2", titles));
    }

    @Test
    public void checkIfTitleIsValid_HaveLettersOrSpaceAndIsUnique_ReturnTrue() {
        List<String> titles = Arrays.asList("Title", "Title 2", "Title 3");
        assertTrue(new Validator().checkIfTitleIsValid("Title 4", titles));
    }
}