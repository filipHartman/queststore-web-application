package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TeamsDAO extends AbstractDAO {

    public static final Logger log = LoggerFactory.getLogger(TeamsDAO.class);

    public void createTeam(Team team) {
        String query = String.format("INSERT INTO teams(name) VALUES('%s')", team.getName());

        try {
            if (!checkIfTeamExists(team.getName())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Team getTeam(String name) {
        String query = String.format("SELECT * FROM teams WHERE name = '%s'", name);
        Team team = null;
        try {
            if (checkIfTeamExists(name)) {
                log.info(query);
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                team = new Team(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    public List<Team> getAllTeams() {
        List<Team> teames = new ArrayList<>();
        String query = "SELECT * FROM teams";
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                teames.add(new Team(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return teames;
    }

    public void updateTeam(Team team) {
        String query = String.format("UPDATE teams SET name = '%s' WHERE id  = %d", team.getName(), team.getId());

        try {
            if (checkIfTeamExists(team.getId())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteTeam(Team team) {
        String query = String.format("DELETE FROM teams WHERE name = '%s'", team.getName());
        try {
            getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUserToTeam(User user, Team team) {
        String query = String.format("INSERT INTO users_in_teams(team_id, user_id) VALUES(%d, %d)",
                user.getId(), getTeamIDByName(team.getName()));
        try {
            if (checkIfTeamExists(team.getName()) && !checkIfUserIsInTeam(user)) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Team getUserTeam(User user) {
        Team team = null;
        String query = String.format("SELECT * FROM teams\n" +
                "INNER JOIN users_in_teams ON teams.id = users_in_teams.team_id\n" +
                "WHERE users_in_teams.user_id = %d;", user.getId());

        try {
            if (checkIfUserIsInTeam(user)) {
                log.info(query);
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                team = new Team(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    public void removeUserFromTeam(User user) {
        String query = String.format("DELETE FROM users_in_teams WHERE user_id = %d", user.getId());
        log.info(query);
        try {
            getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfTeamExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM teams WHERE id=%d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }


    public boolean checkIfTeamExists(String name) throws SQLException {
        String query = String.format("SELECT * FROM teams WHERE name='%s';", name);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

    public boolean checkIfUserIsInTeam(User user) throws SQLException {
        String query = String.format("SELECT * FROM users_in_teams WHERE user_id = %d ", user.getId());
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

    public int getTeamIDByName(String name) {
        Integer result = null;
        String query = String.format("SELECT id FROM teams WHERE name = '%s'", name);
        try {
            result = getConnection().createStatement().executeQuery(query).getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
