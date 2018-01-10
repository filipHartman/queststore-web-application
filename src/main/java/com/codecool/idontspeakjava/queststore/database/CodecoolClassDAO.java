package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CodecoolClassDAO extends AbstractDAO {

    public static final Logger log = LoggerFactory.getLogger(CodecoolClassDAO.class);


    public void createCodecoolClass(CodecoolClass codecoolClass) {
        String query = String.format("INSERT INTO classes(name) VALUES('%s')", codecoolClass.getName());

        try {
            if (!checkIfClassExists(codecoolClass.getName())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CodecoolClass getCodecoolClass(String name) {
        String query = String.format("SELECT * FROM classes WHERE name = '%s'", name);
        CodecoolClass codecoolClass = null;
        try {
            if (checkIfClassExists(name)) {
                log.info(query);
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                codecoolClass = new CodecoolClass(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codecoolClass;
    }

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

    public void updateCodecoolClass(CodecoolClass codecoolClass) {
        String query = String.format("UPDATE classes SET name = '%s' WHERE id  = %d", codecoolClass.getName(), codecoolClass.getId());

        try {
            if (checkIfClassExists(codecoolClass.getId())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCodecoolClass(CodecoolClass codecoolClass) {
        String query = String.format("DELETE FROM classes WHERE name = '%s'", codecoolClass.getName());
        try {
            getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUserToCodecoolClass(User user, CodecoolClass codecoolClass) {
        String query = String.format("INSERT INTO users_in_classes(class_id, user_id) VALUES(%d, %d)",
                user.getId(), codecoolClass.getId());
        try {
            if (checkIfClassExists(codecoolClass.getName()) && !checkIfUserIsInClass(user)) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public CodecoolClass getUserCodecoolClass(User user) {
        CodecoolClass codecoolClass = null;
        String query = String.format("SELECT * FROM classes\n" +
                "INNER JOIN users_in_classes ON classes.id = users_in_classes.class_id\n" +
                "WHERE users_in_classes.user_id = %d;", user.getId());

        try {
            if (checkIfUserIsInClass(user)) {
                log.info(query);
                ResultSet resultSet = getConnection().createStatement().executeQuery(query);
                codecoolClass = new CodecoolClass(resultSet.getInt("id"), resultSet.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codecoolClass;
    }

    public void removeUserFromCodecoolClass(User user) {
        String query = String.format("DELETE FROM users_in_classes WHERE user_id = %d", user.getId());
        log.info(query);
        try {
            getConnection().createStatement().executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkIfClassExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM classes WHERE id=%d;", id);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }


    public boolean checkIfClassExists(String name) throws SQLException {
        String query = String.format("SELECT * FROM classes WHERE name='%s';", name);
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

    public boolean checkIfUserIsInClass(User user) throws SQLException {
        String query = String.format("SELECT * FROM users_in_classes WHERE user_id = %d ", user.getId());
        log.info(query);
        ResultSet resultSet = getConnection()
                .createStatement()
                .executeQuery(query);

        return resultSet.next();
    }

}
