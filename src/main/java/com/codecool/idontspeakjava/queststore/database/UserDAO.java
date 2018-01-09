package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends AbstractDAO {

    private static final Logger log = LoggerFactory.getLogger(UserDAO.class);

    public void createUser(User user) throws SQLException {

        String email = user.getEmail();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String passwordHash = user.getPasswordHash();
        String permission = String.valueOf(user.getPermission());

        if (!checkIfUsersExists(email)) {
            String query = "INSERT INTO users(email, first_name, last_name, password_hash, permission)"
                    + String.format(" VALUES ('%s', '%s', '%s', '%s', '%s')", email, firstName, lastName, passwordHash, permission);

            log.info(query);
            getConnection()
                    .createStatement()
                    .executeUpdate(query);
        }
    }

    public User getUserByEmail(String email) {
        try {
            if (checkIfUsersExists(email)) {
                String query = String.format("SELECT * FROM users WHERE email ='%s'", email);
                log.info(query);
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                return new User.Builder()
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
        return null;
    }

    public List<User> getUsersByPermission(Permissions permission) {
        List<User> users = new ArrayList<>();
        String query = String.format("SELECT * FROM users WHERE permission = '%s'", permission);

        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                users.add(getUserByEmail(resultSet.getString("email")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return users;
    }

    public void updateUser(User user) {
        String query = String.format("UPDATE users SET email = '%s', first_name = '%s', last_name = '%s', password_hash = '%s', permission = '%s'\n" +
                "WHERE id = '%d';", user.getEmail(), user.getFirstName(), user.getLastName(), user.getPasswordHash(), user.getPermission(), user.getId());
        try {
            if (checkIfUsersExists(user.getId())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void deleteUser(User user) {
        String query = String.format("DELETE FROM users WHERE email = '%s'", user.getEmail());

        try {
            if (checkIfUsersExists(user.getEmail())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfUsersExists(String email) throws SQLException {
        String query = String.format("SELECT * FROM users WHERE email='%s';", email);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

    public boolean checkIfUsersExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM users WHERE id=%d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }
}
