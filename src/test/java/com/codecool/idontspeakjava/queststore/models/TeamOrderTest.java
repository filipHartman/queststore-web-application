package com.codecool.idontspeakjava.queststore.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TeamOrderTest {

    @Test
    public void nonDefaultConstructorsShouldInitiateClassProperly() {
        TeamOrder teamOrder = new TeamOrder(12, 14, false, 5555);
        TeamOrder teamOrder2 = new TeamOrder(78, 98, 100, true, 6666);

        assertEquals(teamOrder.getArtifactID(), 12);
        assertEquals(teamOrder.getTeamID(), 14);
        assertEquals(teamOrder.isUsed(), false);
        assertEquals(teamOrder.getCollectedMoney(), 5555);


        assertEquals(teamOrder2.getId(), 78);
        assertEquals(teamOrder2.getArtifactID(), 98);
        assertEquals(teamOrder2.getTeamID(), 100);
        assertEquals(teamOrder2.isUsed(), true);
        assertEquals(teamOrder2.getCollectedMoney(), 6666);

    }

    @Test
    public void settersAndGettersShouldSetAndReturnCorrectValue() {
        TeamOrder teamOrder = new TeamOrder();

        teamOrder.setId(88);
        teamOrder.setArtifactID(122);
        teamOrder.setTeamID(213123);
        teamOrder.setUsed(true);
        teamOrder.setCollectedMoney(7654);

        assertEquals(teamOrder.getId(), 88);
        assertEquals(teamOrder.getArtifactID(), 122);
        assertEquals(teamOrder.getTeamID(), 213123);
        assertEquals(teamOrder.isUsed(), true);
        assertEquals(teamOrder.getCollectedMoney(), 7654);
    }


}