package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    void createUser(User user) throws SQLException;

    User getUserByEmail(String email);

    User getUserById(int id);

    List<User> getUsersByPermission(Permissions permission);

    List<User> getAllUsers();

    void updateUser(User user);

    void deleteUser(User user);

    boolean checkIfUsersExists(String email) throws SQLException;

    boolean checkIfUsersExists(int id) throws SQLException;
}
