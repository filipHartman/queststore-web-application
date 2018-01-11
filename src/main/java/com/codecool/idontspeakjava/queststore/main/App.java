package com.codecool.idontspeakjava.queststore.main;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.controllers.codecooler.CodecoolerController;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("It works !!!");
        new AbstractDAO().connectToDatabase();       

        CodecoolerController controller = new CodecoolerController();
        controller.run();
    }
}
