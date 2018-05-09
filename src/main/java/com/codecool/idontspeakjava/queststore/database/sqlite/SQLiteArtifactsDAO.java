package com.codecool.idontspeakjava.queststore.database.sqlite;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.models.Artifact;
import com.codecool.idontspeakjava.queststore.models.ArtifactCategory;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.TeamOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteArtifactsDAO extends AbstractDAO implements com.codecool.idontspeakjava.queststore.database.ArtifactsDAO {

    private static final Logger log = LoggerFactory.getLogger(SQLiteArtifactsDAO.class);


    @Override
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


    @Override
    public Artifact getArtifact(int id) {
        String query = String.format("SELECT * FROM artifacts WHERE id = %d", id);
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

    public List<Artifact> getAllArtifacts(ArtifactCategory artifactCategory) {
        //language=SQL
        String query = "SELECT * FROM artifacts WHERE category=?";
        List<Artifact> artifacts = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(artifactCategory));
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                artifacts.add(getArtifact(resultSet.getInt("id")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return artifacts;
    }

    @Override
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

    @Override
    public void updateArtifact(Artifact artifact) {
        String query = "UPDATE artifacts SET title = ?, category = ?, artifact_description = ?, price = ? WHERE id = ?";
        try {
            if (checkIfArtifactExists(artifact.getId())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, artifact.getTitle());
                preparedStatement.setString(2, String.valueOf(artifact.getCategory()));
                preparedStatement.setString(3, artifact.getDescription());
                preparedStatement.setInt(4, artifact.getPrice());
                preparedStatement.setInt(5, artifact.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
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

    @Override
    public boolean checkIfArtifactExists(String title) throws SQLException {
        String query = "SELECT * FROM artifacts WHERE title=?;";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, title);

        return preparedStatement.executeQuery().next();
    }

    @Override
    public boolean checkIfArtifactExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM artifacts WHERE id=%d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

    public List<String> getInfoAboutArtifacts(List<Order> orders) {
        List<String> ordersToPrint = new ArrayList<>();

        for (Order o : orders) {
            String orderInfo = getArtifact(o.getArtifactID()).getTitle() + ", ";
            orderInfo += o.isUsed() ? "Used" : "Not used";
            orderInfo += ", ";
            orderInfo += o instanceof TeamOrder ? "Magic" : "Basic";
            ordersToPrint.add(orderInfo);
        }
        return ordersToPrint;
    }
}
