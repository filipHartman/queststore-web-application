package com.codecool.idontspeakjava.queststore.database.sqlite;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.models.Team;
import com.codecool.idontspeakjava.queststore.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteTeamsDAO extends AbstractDAO implements com.codecool.idontspeakjava.queststore.database.TeamsDAO {

    public static final Logger log = LoggerFactory.getLogger(SQLiteTeamsDAO.class);

    @Override
    public boolean createTeam(Team team) {
        String query = "INSERT INTO teams(name) VALUES(?)";

        try {
            if (!checkIfTeamExists(team.getName())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, team.getName());
                preparedStatement.executeUpdate();
                team.setId(getTeam(team.getName()).getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Team getTeam(String name) {
        String query = "SELECT * FROM teams WHERE name = ?";
        Team team = null;
        try {
            if (checkIfTeamExists(name)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, name);
                ResultSet resultSet = preparedStatement.executeQuery();
                team = new Team(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    @Override
    public Team getTeam(int teamID) {
        String query = "SELECT * FROM teams WHERE id = ?";
        Team team = null;
        try {
            if (checkIfTeamExists(teamID)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, teamID);
                ResultSet resultSet = preparedStatement.executeQuery();
                team = new Team(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    @Override
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

    @Override
    public void updateTeam(Team team) {
        String query = "UPDATE teams SET name = ? WHERE id  = ?";

        try {
            if (checkIfTeamExists(team.getId())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, team.getName());
                preparedStatement.setInt(2, team.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteTeam(Team team) {
        String query = "DELETE FROM teams WHERE name = ?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, team.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean addUserToTeam(User user, Team team) {
        String query = "INSERT INTO users_in_teams(team_id, user_id) VALUES(?, ?)";
        try {
            if (checkIfTeamExists(team.getName()) && !checkIfUserIsInTeam(user)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, team.getId());
                preparedStatement.setInt(2, user.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public Team getUserTeam(User user) {
        Team team = null;
        String query = "SELECT * FROM teams\n" +
                "INNER JOIN users_in_teams ON teams.id = users_in_teams.team_id\n" +
                "WHERE users_in_teams.user_id = ?";

        try {
            if (checkIfUserIsInTeam(user)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, user.getId());

                ResultSet resultSet = preparedStatement.executeQuery();
                team = new Team(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return team;
    }

    @Override
    public boolean removeUserFromTeam(User user) {
        String query = "DELETE FROM users_in_teams WHERE user_id = ?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean checkIfTeamExists(int id) throws SQLException {
        String query = "SELECT * FROM teams WHERE id=?;";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();


        return resultSet.next();
    }


    @Override
    public boolean checkIfTeamExists(String name) throws SQLException {
        String query = "SELECT * FROM teams WHERE name=?;";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();


        return resultSet.next();
    }

    @Override
    public boolean checkIfUserIsInTeam(User user) {
        try{
            String query = "SELECT * FROM users_in_teams WHERE user_id = ? ";

            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, user.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
        e.printStackTrace();
        return false;
        }
    }

    @Override
    public int getTeamIDByName(String name) {
        Integer result = null;
        String query = "SELECT id FROM teams WHERE name = ?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);

            result = preparedStatement.executeQuery().getInt("id");

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
