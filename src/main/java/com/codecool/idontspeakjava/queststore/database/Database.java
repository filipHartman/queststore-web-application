package com.codecool.idontspeakjava.queststore.database;

import java.sql.Connection;

public interface Database {

    void connectToDatabase();

    Connection getConnection();
}
