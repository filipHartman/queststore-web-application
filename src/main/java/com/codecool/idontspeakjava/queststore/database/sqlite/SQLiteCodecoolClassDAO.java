package com.codecool.idontspeakjava.queststore.database.sqlite;

import com.codecool.idontspeakjava.queststore.database.AbstractDAO;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
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
    public boolean createCodecoolClass(CodecoolClass codecoolClass) {
        boolean operationSuccessful = false;
        String query = "INSERT INTO classes(name) VALUES(?)";


        try {
            if (!checkIfClassExists(codecoolClass.getName())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, codecoolClass.getName());

                preparedStatement.executeUpdate();
                operationSuccessful = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operationSuccessful;
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
    public boolean addUserToCodecoolClass(User user, CodecoolClass codecoolClass) {
        boolean operationSuccessful = false;
        String query = "INSERT INTO users_in_classes(class_id, user_id) VALUES(?, ?)";

        try {
            if (checkIfClassExists(codecoolClass.getName()) && !checkIfUserIsInClass(user)) {

                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setInt(1, codecoolClass.getId());
                preparedStatement.setInt(2, user.getId());

                preparedStatement.executeUpdate();
                operationSuccessful = true;

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return operationSuccessful;
    }

    public List<User> getClassStudents(CodecoolClass codecoolClass) {
        UserDAO userDao = new SQLiteUserDAO();
        List<User> users = new ArrayList<>();
        String query = "SELECT users.id as id FROM users INNER JOIN users_in_classes ON users.id=users_in_classes.user_id " +
                "WHERE users.permission='Student' AND users_in_classes.class_id = ?;";
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, codecoolClass.getId());
            log.info(preparedStatement.toString());

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                int uderId = resultSet.getInt("id");
                users.add(userDao.getUserById(uderId));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
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
    public boolean removeUserFromCodecoolClass(User user) {
        String query = String.format("DELETE FROM users_in_classes WHERE user_id = ?", user.getId());
        try {
            PreparedStatement preparedStatement = getConnection().prepareStatement(query);
            preparedStatement.setInt(1, user.getId());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
