package com.codecool.idontspeakjava.queststore.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class WalletTest {

    @Test
    public void constructorShouldInitiateClassProperly() {
        Wallet wallet = new Wallet(32, 123, 432, 321);

        assertEquals(32, wallet.getId());
        assertEquals(123, wallet.getUserID());
        assertEquals(432, wallet.getCurrentState());
        assertEquals(321, wallet.getTotalEarnings());
    }

    @Test
    public void settersAndGettersShouldSetAndReturnCorrectValue() {

        Wallet wallet = new Wallet(32, 123, 432, 321);

        wallet.setTotalEarnings(26464);
        wallet.setCurrentState(4664);
        wallet.setId(56);
        wallet.setUserID(95);

        assertEquals(56, wallet.getId());
        assertEquals(95, wallet.getUserID());
        assertEquals(4664, wallet.getCurrentState());
        assertEquals(26464, wallet.getTotalEarnings());
    }

    @Test
    public void builderShouldInitiateNewObjectWithCorrectFields() {

        Wallet wallet = new Wallet.Builder()
                .setTotalEarnings(26464)
                .setCurrentState(4664)
                .setId(56)
                .setUserID(95)
                .build();

        assertEquals(56, wallet.getId());
        assertEquals(95, wallet.getUserID());
        assertEquals(4664, wallet.getCurrentState());
        assertEquals(26464, wallet.getTotalEarnings());
    }


}