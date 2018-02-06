package com.codecool.idontspeakjava.queststore.models;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class ArtifactTest {


    @Test
    public void constructorShouldInitializeFieldsProperly() {
        Artifact artifact = new Artifact("test", ArtifactCategory.Magic, "testDesc", 100);
        assertArrayEquals(new String[]{"test", "Magic", "100"},
                new String[]{artifact.getTitle(), artifact.getCategory().name(), String.valueOf(artifact.getPrice())});
    }

    @Test
    public void settersShouldInitializeCorrectData() {
        Artifact artifact = new Artifact("test", ArtifactCategory.Magic, "testDesc", 100);

        artifact.setDescription("newDesc");
        artifact.setCategory(ArtifactCategory.Basic);
        artifact.setPrice(55);
        artifact.setTitle("newTitle");
        artifact.setId(12);

        assertEquals("newDesc", artifact.getDescription());
        assertEquals("Basic", artifact.getCategory().name());
        assertEquals(55, artifact.getPrice());
        assertEquals("newTitle", artifact.getTitle());
        assertEquals(12, artifact.getId());


    }

    @Test
    public void builderPatternShouldInitiateInstanceProperly() {
        Artifact artifact = new Artifact.Builder()
                .setDescription("newDesc")
                .setCategory(ArtifactCategory.Basic)
                .setPrice(55)
                .setTitle("newTitle")
                .setId(12).build();

        assertEquals("newDesc", artifact.getDescription());
        assertEquals("Basic", artifact.getCategory().name());
        assertEquals(55, artifact.getPrice());
        assertEquals("newTitle", artifact.getTitle());
        assertEquals(12, artifact.getId());

    }
}