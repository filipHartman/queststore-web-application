package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.User;

import java.sql.SQLException;
import java.util.List;

public interface CodecoolClassDAO {
    boolean createCodecoolClass(CodecoolClass codecoolClass);

    CodecoolClass getCodecoolClass(String name);

    List<CodecoolClass> getAllCodecoolClasses();

    void updateCodecoolClass(CodecoolClass codecoolClass);

    void deleteCodecoolClass(CodecoolClass codecoolClass);

    boolean addUserToCodecoolClass(User user, CodecoolClass codecoolClass);

    CodecoolClass getUserCodecoolClass(User user);

    void removeUserFromCodecoolClass(User user);

    boolean checkIfClassExists(int id) throws SQLException;

    boolean checkIfClassExists(String name) throws SQLException;

    boolean checkIfUserIsInClass(User user) throws SQLException;

    int getClassIDByName(String name);
}
