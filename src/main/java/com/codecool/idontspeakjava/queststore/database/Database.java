package com.codecool.idontspeakjava.queststore.database;

import java.sql.Connection;
import java.sql.SQLException;

public interface Database {

    void connectToDatabase() throws SQLException, ClassNotFoundException;

    Connection getConnection() throws SQLException;
}
