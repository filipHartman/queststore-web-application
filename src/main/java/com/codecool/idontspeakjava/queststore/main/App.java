package com.codecool.idontspeakjava.queststore.main;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Permissions;

import java.sql.SQLException;

public class App {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        System.out.println("It works !!!");
        new AbstractDAO().connectToDatabase();       

        UserDAO userDao = new UserDAO();
        User user = new User("Przemek", "Nachel", "haslo", "cygan@nic.pl", Permissions.Student);
        userDao.createUser(user);
        user = userDao.getUserByEmail("cygan@nic.pl");
        System.out.println(user.getFirstName());



        
    }
}
