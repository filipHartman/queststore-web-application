package com.codecool.idontspeakjava.queststore.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeamTest {


    @Test
    public void constructorShouldInitiateClassProperly() {

        Team team = new Team("codecoolers");
        Team team2 = new Team(123, "avengers");

        assertEquals(team.getName(), "codecoolers");

        assertEquals(team2.getName(), "avengers");
        assertEquals(team2.getId(), 123);

    }

    @Test
    public void settersAndGettersShouldSetAndReturnCorrectValue() {

        Team team = new Team(123, "avengers");

        team.setId(367);
        team.setName("java");


        assertEquals(367, team.getId());
        assertEquals("java", team.getName());
    }

    @Test
    public void overriddenToStringMethodShouldReturnCorrectValue() {
        Team team = new Team(123, "avengers");

        String correctReturn = "ID: 123 Name: avengers";

        assertEquals(correctReturn, team.toString());

    }
}