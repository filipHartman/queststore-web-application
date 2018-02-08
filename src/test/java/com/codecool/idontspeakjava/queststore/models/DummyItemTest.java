package com.codecool.idontspeakjava.queststore.models;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class DummyItemTest {

    private DummyItem dummyItem;

    @Before
    public void setup() {
        dummyItem = new DummyItem();
    }

    @Test
    public void setterAndGetterForTitleShouldSetAndReturnCorrectValue() throws Exception {

        dummyItem.setTitle("testTitle");
        assertEquals(dummyItem.getTitle(), "testTitle");
    }


    @Test
    public void setterAndGetterForDescriptionShouldSetAndReturnCorrectValue() throws Exception {
        dummyItem.setDescription("testDesc");
        assertEquals(dummyItem.getDescription(), "testDesc");
    }

    @Test
    public void setterAndGetterForCategoryShouldSetAndReturnCorrectValue() throws Exception {
        dummyItem.setCategory("testCategory");
        assertEquals(dummyItem.getCategory(), "testCategory");
    }

    @Test
    public void setterAndGetterForPriceShouldSetAndReturnCorrectValue() throws Exception {
        dummyItem.setRewardOrPrice("123");
        assertEquals(dummyItem.getRewardOrPrice(), 123);
    }

    @Test(expected = NumberFormatException.class)
    public void setterOfRewardOrPriceShouldThrowExceptionIfThereIsNoNumberInString() {
        dummyItem.setRewardOrPrice("dasdad");
        dummyItem.getRewardOrPrice();
    }

}