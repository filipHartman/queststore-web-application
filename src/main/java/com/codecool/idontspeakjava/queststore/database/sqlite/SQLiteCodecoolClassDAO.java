package com.codecool.idontspeakjava.queststore.database.sqlite;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SQLiteCodecoolClassDAO extends AbstractDAO implements CodecoolClassDAO {

    public static final Logger log = LoggerFactory.getLogger(SQLiteCodecoolClassDAO.class);


    @Override
    public void createCodecoolClass(CodecoolClass codecoolClass) {
        String query = "INSERT INTO classes(name) VALUES(?)";


        try {
            if (!checkIfClassExists(codecoolClass.getName())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, codecoolClass.getName());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CodecoolClass getCodecoolClass(String name) {
        String query = "SELECT * FROM classes WHERE name = ?";
        CodecoolClass codecoolClass = null;
        try {
            if (checkIfClassExists(name)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, name);

                ResultSet resultSet = preparedStatement.executeQuery();

                codecoolClass = new CodecoolClass(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codecoolClass;
    }

    @Override
    public List<CodecoolClass> getAllCodecoolClasses() {
        List<CodecoolClass> codecoolClasses = new ArrayList<>();
        String query = "SELECT * FROM classes";
        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                codecoolClasses.add(new CodecoolClass(resultSet.getInt("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codecoolClasses;
    }

    @Override
    public void updateCodecoolClass(CodecoolClass codecoolClass) {
        String query = "UPDATE classes SET name = ? WHERE id  = ?";

        try {
            if (checkIfClassExists(codecoolClass.getId())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, codecoolClass.getName());
                preparedStatement.setInt(2, codecoolClass.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCodecoolClass(CodecoolClass codecoolClass) {
        String query = "DELETE FROM classes WHERE name = ?";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, codecoolClass.getName());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addUserToCodecoolClass(User user, CodecoolClass codecoolClass) {
        String query = "INSERT INTO users_in_classes(class_id, user_id) VALUES(?, ?)";

        try {
            if (checkIfClassExists(codecoolClass.getName()) && !checkIfUserIsInClass(user)) {

                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, codecoolClass.getId());
                preparedStatement.setInt(2, user.getId());

                preparedStatement.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public CodecoolClass getUserCodecoolClass(User user) {
        CodecoolClass codecoolClass = null;
        String query = "SELECT * FROM classes\n" +
                "INNER JOIN users_in_classes ON classes.id = users_in_classes.class_id\n" +
                "WHERE users_in_classes.user_id = ?;";

        try {
            if (checkIfUserIsInClass(user)) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, user.getId());

                ResultSet resultSet = preparedStatement.executeQuery();
                codecoolClass = new CodecoolClass(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codecoolClass;
    }

    @Override
    public void removeUserFromCodecoolClass(User user) {
        String query = String.format("DELETE FROM users_in_classes WHERE user_id = ?", user.getId());
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkIfClassExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM classes WHERE id=?;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }


    @Override
    public boolean checkIfClassExists(String name) throws SQLException {
        String query = "SELECT * FROM classes WHERE name=?;";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, name);

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    @Override
    public boolean checkIfUserIsInClass(User user) throws SQLException {
        String query = "SELECT * FROM users_in_classes WHERE user_id = ? ";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, user.getId());

        ResultSet resultSet = preparedStatement.executeQuery();

        return resultSet.next();
    }

    @Override
    public int getClassIDByName(String name) {
        Integer result = null;
        String query = "SELECT id FROM classes WHERE name = ?";

        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setString(1, name);

            result = preparedStatement.executeQuery().getInt("id");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

}
