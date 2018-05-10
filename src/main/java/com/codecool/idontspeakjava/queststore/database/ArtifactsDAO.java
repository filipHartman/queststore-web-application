package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;

import java.sql.SQLException;
import java.util.List;

public interface ArtifactsDAO {
    void createArtifact(Artifact artifact);

    Artifact getArtifact(int id);

    List<Artifact> getAllArtifacts();

    List<Artifact> getArtifacts(ArtifactCategory category);

    void updateArtifact(Artifact artifact);

    void removeArtifact(Artifact artifact);

    boolean checkIfArtifactExists(String title) throws SQLException;

    boolean checkIfArtifactExists(int id) throws SQLException;
}
