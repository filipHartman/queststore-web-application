package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ExperienceLevelDAO extends AbstractDAO {

    private static final Logger log = LoggerFactory.getLogger(ExperienceLevelDAO.class);

    public void createExperienceLevel(ExperienceLevel experienceLevel) {
        String query = String.format("INSERT INTO experience_levels(name, threshold) " +
                "VALUES('%s', %d)", experienceLevel.getName(), experienceLevel.getThreshold());

        try {
            if (!checkIfExperienceLevelExists(experienceLevel.getName())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public void updateExperienceLevel(ExperienceLevel experienceLevel) {

        String query = String.format("UPDATE experience_levels SET name = '%s', threshold = %d WHERE id = %d",
                experienceLevel.getName(), experienceLevel.getThreshold(), experienceLevel.getId());

        try {
            if (checkIfExperienceLevelExists(experienceLevel.getId())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteExperienceLevel(ExperienceLevel experienceLevel) {
        String query = String.format("DELETE FROM experience_levels WHERE id = %d or name = '%s'",
                experienceLevel.getId(), experienceLevel.getName());

        try {
            if (checkIfExperienceLevelExists(experienceLevel.getName())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfExperienceLevelExists(String name) throws SQLException {
        String query = String.format("SELECT * FROM experience_levels WHERE name='%s';", name);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

    public boolean checkIfExperienceLevelExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM experience_levels WHERE id=%d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }


}
