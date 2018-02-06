package com.codecool.idontspeakjava.queststore.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class OrderTest {

    @Test
    public void nonDefaultConstructorsShouldInitiateClassProperly() {
        Order order = new Order(12, 14, false);
        Order order2 = new Order(78, 98, 100, true);

        assertEquals(order.getArtifactID(), 12);
        assertEquals(order.getWalletID(), 14);
        assertEquals(order.isUsed(), false);


        assertEquals(order2.getId(), 78);
        assertEquals(order2.getArtifactID(), 98);
        assertEquals(order2.getWalletID(), 100);
        assertEquals(order2.isUsed(), true);

    }

    @Test
    public void settersAndGettersShouldSetAndReturnCorrectValue() {
        Order order = new Order();

        order.setId(88);
        order.setUsed(true);
        order.setArtifactID(122);
        order.setWalletID(213123);

        assertEquals(order.getId(), 88);
        assertEquals(order.getArtifactID(), 122);
        assertEquals(order.getWalletID(), 213123);
        assertEquals(order.isUsed(), true);

    }

    @Test
    public void builderShouldInitiateNewObjectWithCorrectFields() {

        Order order = new Order.Builder()
                .setId(88)
                .setIsUsed(true)
                .setArtifactID(122)
                .setWalletID(213123)
                .build();

        assertEquals(order.getId(), 88);
        assertEquals(order.getArtifactID(), 122);
        assertEquals(order.getWalletID(), 213123);
        assertEquals(order.isUsed(), true);
    }

}