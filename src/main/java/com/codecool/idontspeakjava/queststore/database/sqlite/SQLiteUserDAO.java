package com.codecool.idontspeakjava.queststore.database.sqlite;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteUserDAO extends AbstractDAO implements com.codecool.idontspeakjava.queststore.database.UserDAO {

    private static final Logger log = LoggerFactory.getLogger(SQLiteUserDAO.class);

    @Override
    public void createUser(User user) throws SQLException {

        String email = user.getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String passwordHash = user.getPasswordHash();
        String permission = String.valueOf(user.getPermission());

        if (!checkIfUsersExists(email)) {
            String query = "INSERT INTO users(email, first_name, last_name, password_hash, permission)"
                    + " VALUES (?, ?, ?, ?, ?)";

            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, firstName);
            preparedStatement.setString(3, lastName);
            preparedStatement.setString(4, passwordHash);
            preparedStatement.setString(5, permission);

            preparedStatement.executeUpdate();
            user.setId(getUserByEmail(user.getEmail()).getId());
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        User user = null;
        try {
            if (checkIfUsersExists(email)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, email);


                ResultSet resultSet = preparedStatement.executeQuery();
                user = new User.Builder()
                        .setId(resultSet.getInt("id"))
                        .setLastName(resultSet.getString("last_name"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setEmail(resultSet.getString("email"))
                        .setPasswordHash(resultSet.getString("password_hash"))
                        .setPermission(Permissions.valueOf(resultSet.getString("permission")))
                        .build();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getUserById(int id) {
        String query = "SELECT * FROM users WHERE id = ?";
        User user = null;
        try {
            if (checkIfUsersExists(id)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, id);

                ResultSet resultSet = preparedStatement.executeQuery();
                user = new User.Builder()
                        .setId(resultSet.getInt("id"))
                        .setLastName(resultSet.getString("last_name"))
                        .setFirstName(resultSet.getString("first_name"))
                        .setEmail(resultSet.getString("email"))
                        .setPasswordHash(resultSet.getString("password_hash"))
                        .setPermission(Permissions.valueOf(resultSet.getString("permission")))
                        .build();

            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            log.info(preparedStatement.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(getUserByEmail(resultSet.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public List<User> getUsersByPermission(Permissions permission) {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM users WHERE permission = ?";


        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, String.valueOf(permission));

            log.info(preparedStatement.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                users.add(getUserByEmail(resultSet.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;
    }

    @Override
    public void updateUser(User user) {
        String query = "UPDATE users SET email = ?, first_name = ?, last_name = ?, password_hash = ?, permission = ?\n" +
                "WHERE id =?;";

        try {
            if (checkIfUsersExists(user.getId())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getEmail());
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getLastName());
                preparedStatement.setString(4, user.getPasswordHash());
                preparedStatement.setString(5, String.valueOf(user.getPermission()));
                preparedStatement.setInt(6, user.getId());


                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean deleteUser(User user) {
        boolean operationSuccessful = false;
        String query = "DELETE FROM users WHERE email = ?";

        try {
            if (checkIfUsersExists(user.getEmail())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, user.getEmail());

                preparedStatement.executeUpdate();
                operationSuccessful = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operationSuccessful;
    }

    @Override
    public boolean checkIfUsersExists(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email= ?;";
        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, email);

        ResultSet resultSet = preparedStatement.executeQuery();
        return resultSet.next();
    }

    @Override
    public boolean checkIfUsersExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM users WHERE id= %d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }
}
