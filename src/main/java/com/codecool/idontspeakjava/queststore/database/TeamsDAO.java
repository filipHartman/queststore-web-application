package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.User;

import java.sql.SQLException;
import java.util.List;

public interface TeamsDAO {
    boolean createTeam(Team team);

    Team getTeam(String name);

    Team getTeam(int teamID);

    List<Team> getAllTeams();

    void updateTeam(Team team);

    void deleteTeam(Team team);

    boolean addUserToTeam(User user, Team team);

    Team getUserTeam(User user);

    boolean removeUserFromTeam(User user);

    boolean checkIfTeamExists(int id) throws SQLException;

    boolean checkIfTeamExists(String name) throws SQLException;

    boolean checkIfUserIsInTeam(User user);

    int getTeamIDByName(String name);
}
