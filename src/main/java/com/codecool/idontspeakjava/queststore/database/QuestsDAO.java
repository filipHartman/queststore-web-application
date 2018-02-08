package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteQuestsDAO;
import com.codecool.idontspeakjava.queststore.models.Quest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;

public interface QuestsDAO {
    Logger log = LoggerFactory.getLogger(SQLiteQuestsDAO.class);

    void createQuest(Quest quest);

    List<Quest> getAllQuests();

    void updateQuest(Quest quest);

    boolean checkIfQuestExists(String title) throws SQLException;

    boolean checkIfQuestExists(int id) throws SQLException;
}
