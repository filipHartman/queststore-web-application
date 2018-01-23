package com.codecool.idontspeakjava.queststore.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDAO implements Database {

    private static Connection connection = null;

    @Override
    public void connectToDatabase() throws SQLException, ClassNotFoundException {

        Class.forName("net.sf.log4jdbc.DriverSpy");
        connection = DriverManager.getConnection("jdbc:log4jdbc:sqlite:quest_store.db?foreign_keys=on;");
        connection.setAutoCommit(true);
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connectToDatabase();
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }
}
