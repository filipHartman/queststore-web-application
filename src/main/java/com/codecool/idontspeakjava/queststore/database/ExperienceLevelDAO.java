package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.ExperienceLevel;

import java.sql.SQLException;
import java.util.List;

public interface ExperienceLevelDAO {
    boolean createExperienceLevel(ExperienceLevel experienceLevel);

    List<ExperienceLevel> getAllExperienceLevels();

    void updateExperienceLevel(ExperienceLevel experienceLevel);

    void deleteExperienceLevel(ExperienceLevel experienceLevel);

    boolean checkIfExperienceLevelExists(String name) throws SQLException;

    boolean checkIfExperienceLevelExists(int id) throws SQLException;
}
