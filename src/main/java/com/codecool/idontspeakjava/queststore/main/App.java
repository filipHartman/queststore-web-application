package com.codecool.idontspeakjava.queststore.main;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("It works !!!");
        new AbstractDAO().connectToDatabase();
        new AbstractDAO().connectToDatabase();
    }
}
