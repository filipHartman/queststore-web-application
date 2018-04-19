package com.codecool.idontspeakjava.queststore.database.sqlite;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteExperienceLevelDAO extends AbstractDAO implements com.codecool.idontspeakjava.queststore.database.ExperienceLevelDAO {

    private static final Logger log = LoggerFactory.getLogger(SQLiteExperienceLevelDAO.class);

    @Override
    public boolean createExperienceLevel(ExperienceLevel experienceLevel) {
        boolean operationSucceeded = false;
        String query = "INSERT INTO experience_levels(name, threshold) " +
                "VALUES(?, ?)";

        try {
            if (!checkIfExperienceLevelExists(experienceLevel.getName()) && !checkIfExperienceLevelExists(experienceLevel.getThreshold())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);

                preparedStatement.setString(1, experienceLevel.getName());
                preparedStatement.setLong(2, experienceLevel.getThreshold());

                preparedStatement.executeUpdate();
                operationSucceeded = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  operationSucceeded;
    }

    @Override
    public List<ExperienceLevel> getAllExperienceLevels() {
        //language=SQL
        String query = "SELECT * FROM experience_levels";
        List<ExperienceLevel> experienceLevels = new ArrayList<>();

        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                ExperienceLevel experienceLevel = new ExperienceLevel.Builder()
                        .setId(resultSet.getInt("id"))
                        .setName(resultSet.getString("name"))
                        .setThreshold(resultSet.getLong("threshold"))
                        .build();
                experienceLevels.add(experienceLevel);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return experienceLevels;
    }

    @Override
    public void updateExperienceLevel(ExperienceLevel experienceLevel) {

        String query = "UPDATE experience_levels SET name = ?, threshold = ? WHERE id = ?";

        try {
            if (checkIfExperienceLevelExists(experienceLevel.getId())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);

                preparedStatement.setString(1, experienceLevel.getName());
                preparedStatement.setLong(2, experienceLevel.getThreshold());
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteExperienceLevel(ExperienceLevel experienceLevel) {
        String query = "DELETE FROM experience_levels WHERE id = ? or name = ?";

        try {
            if (checkIfExperienceLevelExists(experienceLevel.getName())) {

                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, experienceLevel.getId());
                preparedStatement.setString(2, experienceLevel.getName());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkIfExperienceLevelExists(String name) throws SQLException {
        String query = "SELECT * FROM experience_levels WHERE name=?";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, name);
        return preparedStatement.executeQuery().next();
    }

    private boolean checkIfExperienceLevelExists(Long threshold) throws SQLException {
        String query = "SELECT * FROM experience_levels WHERE threshold=?";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setLong(1, threshold);
        return preparedStatement.executeQuery().next();
    }

    @Override
    public boolean checkIfExperienceLevelExists(int id) throws SQLException {
        String query = "SELECT * FROM experience_levels WHERE id=?";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeQuery().next();
    }


}
