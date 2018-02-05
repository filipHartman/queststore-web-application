package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.User;

import java.sql.SQLException;
import java.util.List;

public interface TeamsDAO {
    void createTeam(Team team);

    Team getTeam(String name);

    Team getTeam(int teamID);

    List<Team> getAllTeams();

    void updateTeam(Team team);

    void deleteTeam(Team team);

    void addUserToTeam(User user, Team team);

    Team getUserTeam(User user);

    void removeUserFromTeam(User user);

    boolean checkIfTeamExists(int id) throws SQLException;

    boolean checkIfTeamExists(String name) throws SQLException;

    boolean checkIfUserIsInTeam(User user) throws SQLException;

    int getTeamIDByName(String name);
}
