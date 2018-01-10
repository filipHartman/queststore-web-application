package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ArtifactsDAO extends AbstractDAO {

    public static final Logger log = LoggerFactory.getLogger(ArtifactsDAO.class);

    public void createArtifact(Artifact artifact) {
        String title = artifact.getTitle();
        String category = String.valueOf(artifact.getCategory());
        String artifactDescription = artifact.getDescription();
        int price = artifact.getPrice();

        String query = String.format("INSERT INTO artifacts(title, category, artifact_description, price) " +
                "VALUES('%s', '%s', '%s', %d)", title, category, artifactDescription, price);

        try {
            if (!checkIfArtifactExists(title)) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Artifact getArtifact(String title) {
        String query = String.format("SELECT * FROM artifacts WHERE title = '%s'", title);
        Artifact artifact = null;

        try {
            if (checkIfArtifactExists(title)) {
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                log.info(query);
                artifact = new Artifact.Builder()
                        .setId(resultSet.getInt("id"))
                        .setTitle(resultSet.getString("title"))
                        .setCategory(ArtifactCategory.valueOf(resultSet.getString("category")))
                        .setDescription(resultSet.getString("artifact_description"))
                        .setPrice(resultSet.getInt("price"))
                        .build();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artifact;
    }

    public List<Artifact> getAllArtifacts() {
        //language=SQL
        String query = "SELECT * FROM artifacts";
        List<Artifact> artifacts = new ArrayList<>();
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                artifacts.add(getArtifact(resultSet.getString("title")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artifacts;
    }

    public void updateArtifact(Artifact artifact) {
        String query = String.format("UPDATE artifacts SET title = '%s', category = '%s', artifact_description = '%s', price = %d",
                artifact.getTitle(), artifact.getCategory(), artifact.getDescription(), artifact.getPrice());

        try {
            if (checkIfArtifactExists(artifact.getTitle())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeArtifact(Artifact artifact) {
        String query = String.format("DELETE FROM artifacts WHERE title = '%s'", artifact.getTitle());
        log.info(query);
        try {
            getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfArtifactExists(String title) throws SQLException {
        String query = String.format("SELECT * FROM artifacts WHERE title='%s';", title);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

    public boolean checkIfArtifactExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM artifacts WHERE id=%d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }
}
