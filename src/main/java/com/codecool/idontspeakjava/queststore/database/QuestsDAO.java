package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuestsDAO extends AbstractDAO {

    public static final Logger log = LoggerFactory.getLogger(QuestsDAO.class);

    public void createQuest(Quest quest) {
        String title = quest.getTitle();
        String category = String.valueOf(quest.getCategory());
        String questDescription = quest.getDescription();
        int reward = quest.getReward();

        String query = String.format("INSERT INTO quests(title, category, quest_description, reward) " +
                "VALUES('%s', '%s', '%s', %d)", title, category, questDescription, reward);

        try {
            if (!checkIfQuestExists(quest.getTitle())) {
                log.info(query);
                getConnection().createStatement().executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Quest> getAllQuests() {
        String query = "SELECT * FROM quests";
        List<Quest> quests = new ArrayList<>();
        log.info(query);

        try {
            ResultSet resultSet = getConnection().createStatement().executeQuery(query);
            while (resultSet.next()) {
                Quest quest = new Quest.Builder()
                        .setId(resultSet.getInt("id"))
                        .setTitle(resultSet.getString("title"))
                        .setCategory(QuestCategory.valueOf(resultSet.getString("category")))
                        .setDescription(resultSet.getString("quest_description"))
                        .setReward(resultSet.getInt("reward"))
                        .build();
                quests.add(quest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return quests;
    }


    public boolean checkIfQuestExists(String title) throws SQLException {
        String query = String.format("SELECT * FROM quests WHERE title='%s';", title);
        return executeQuery(query).next();

    }

    public boolean checkIfQuestExists(int id) throws SQLException {
        String query = String.format("SELECT * FROM quests WHERE id=%d;", id);
        return executeQuery(query).next();
    }

    public ResultSet executeQuery(String query) {
        ResultSet resultSet = null;
        try {
            resultSet = getConnection()
                    .createStatement()
                    .executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

}
