package com.codecool.idontspeakjava.queststore.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CodecoolClassTest {

    @Test
    public void constructorShouldInitializeClassProperly() {
        CodecoolClass codecoolClass = new CodecoolClass("codecool");
        CodecoolClass codecoolClass2 = new CodecoolClass(1, "codecool2");

        assertEquals(codecoolClass.getName(), "codecool");
        assertEquals(codecoolClass2.getName(), "codecool2");
        assertEquals(1, codecoolClass2.getId());
    }

    @Test
    public void settersSouldInitializeClassFieldProperly() {
        CodecoolClass codecoolClass = new CodecoolClass(1, "test");

        codecoolClass.setId(100);
        codecoolClass.setName("newName");

        assertEquals(codecoolClass.getId(), 100);
        assertEquals(codecoolClass.getName(), "newName");
    }

}