package com.codecool.idontspeakjava.queststore.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class QuestTest {

    @Test
    public void constructorShouldInitiateClassProperly() {

        Quest quest = new Quest("Drink beer", QuestCategory.Basic, "drink", 999);

        assertEquals("Drink beer", quest.getTitle());
        assertEquals("Basic", quest.getCategory().name());
        assertEquals("drink", quest.getDescription());
        assertEquals(999, quest.getReward());


    }

    @Test
    public void settersAndGettersShouldSetAndReturnCorrectValue() {

        Quest quest = new Quest("Drink beer", QuestCategory.Basic, "drink", 999);

        quest.setCategory(QuestCategory.Extra);
        quest.setId(4567);
        quest.setTitle("drink water");
        quest.setDescription("water");
        quest.setReward(1231);

        assertEquals("drink water", quest.getTitle());
        assertEquals("Extra", quest.getCategory().name());
        assertEquals("water", quest.getDescription());
        assertEquals(1231, quest.getReward());
        assertEquals(4567, quest.getId());
    }

    @Test
    public void builderShouldInitiateNewObjectWithCorrectFields() {

        Quest quest = new Quest.Builder()
                .setCategory(QuestCategory.Extra)
                .setTitle("drink water")
                .setDescription("water")
                .setReward(1231)
                .setId(4567)
                .build();

        assertEquals("drink water", quest.getTitle());
        assertEquals("Extra", quest.getCategory().name());
        assertEquals("water", quest.getDescription());
        assertEquals(1231, quest.getReward());
        assertEquals(4567, quest.getId());

    }
}