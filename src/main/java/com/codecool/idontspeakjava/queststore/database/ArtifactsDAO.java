package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
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

        String query = "INSERT INTO artifacts(title, category, artifact_description, price) " +
                "VALUES(?, ?, ?, ?)";

        try {
            if (!checkIfArtifactExists(title)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, title);
                preparedStatement.setString(2, category);
                preparedStatement.setString(3, artifactDescription);
                preparedStatement.setInt(4, price);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Artifact getArtifact(int id) {
        String query = String.format("SELECT * FROM artifacts WHERE id = ?", id);
        Artifact artifact = null;

        try {
            if (checkIfArtifactExists(id)) {
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
                artifacts.add(getArtifact(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artifacts;
    }

    public void updateArtifact(Artifact artifact) {
        String query = "UPDATE artifacts SET title = ?, category = ?, artifact_description = ?, price = ? WHERE id = ?";



        try {
            if (checkIfArtifactExists(artifact.getTitle())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, artifact.getTitle());
                preparedStatement.setString(2, String.valueOf(artifact.getCategory()));
                preparedStatement.setInt(3, artifact.getPrice());
                preparedStatement.setInt(4, artifact.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeArtifact(Artifact artifact) {
        String query = "DELETE FROM artifacts WHERE title = ?";


        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, artifact.getTitle());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfArtifactExists(String title) throws SQLException {
        String query = "SELECT * FROM artifacts WHERE title=?;";


        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, title);

        return preparedStatement.executeQuery().next();
    }

    public boolean checkIfArtifactExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM artifacts WHERE id=?;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }
}
