package com.codecool.idontspeakjava.queststore.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExperienceLevelTest {

    @Test
    public void constructorShouldInitiateClassProperly() {

        ExperienceLevel experienceLevel1 = new ExperienceLevel("testName", 1000);
        ExperienceLevel experienceLevel2 = new ExperienceLevel(12, "testName2", 2000);

        assertEquals("testName", experienceLevel1.getName());
        assertEquals(1000, experienceLevel1.getThreshold());

        assertEquals(12, experienceLevel2.getId());
        assertEquals("testName2", experienceLevel2.getName());
        assertEquals(2000, experienceLevel2.getThreshold());
    }

    @Test
    public void settersAndGettersShouldSetAndReturnCorrectValue() {
        ExperienceLevel experienceLevel = new ExperienceLevel("testname", 123123);

        experienceLevel.setName("newTestName");
        experienceLevel.setId(4321);
        experienceLevel.setThreshold(543);

        assertEquals("newTestName", experienceLevel.getName());
        assertEquals(4321, experienceLevel.getId());
        assertEquals(543, experienceLevel.getThreshold());

    }

    @Test
    public void builderShouldInitiateNewObjectWithCorrectFields() {
        ExperienceLevel experienceLevel = new ExperienceLevel.Builder()
                .setThreshold(890)
                .setName("builder")
                .setId(55).build();

        assertEquals("builder", experienceLevel.getName());
        assertEquals(55, experienceLevel.getId());
        assertEquals(890, experienceLevel.getThreshold());
    }


}