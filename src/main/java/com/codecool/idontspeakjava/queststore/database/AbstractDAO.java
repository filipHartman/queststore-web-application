package com.codecool.idontspeakjava.queststore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AbstractDAO implements Database {

    private static Connection connection = null;

    @Override
    public void connectToDatabase() throws SQLException, ClassNotFoundException {
        Class.forName("org.sqlite.JDBC");
        connection = DriverManager.getConnection("jdbc:sqlite:quest_store.db?foreign_keys=on;");
        connection.setAutoCommit(true);
    }

    @Override
    public Connection getConnection() {
        if (connection == null) {
            try {
                connectToDatabase();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
