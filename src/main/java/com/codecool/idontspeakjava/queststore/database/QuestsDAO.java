package com.codecool.idontspeakjava.queststore.database;

import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
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

        String query = "INSERT INTO quests(title, category, quest_description, reward) " +
                "VALUES(?, ?, ?, ?)";

        try {
            if (!checkIfQuestExists(quest.getTitle())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);

                preparedStatement.setString(1, title);
                preparedStatement.setString(2, category);
                preparedStatement.setString(3, questDescription);
                preparedStatement.setInt(4, reward);

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Quest> getAllQuests() {
        String query = "SELECT * FROM quests";
        List<Quest> quests = new ArrayList<>();

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

    public void updateQuest(Quest quest) {
        String query = "UPDATE quests SET title = ?, category = ?, quest_description = ?, reward = ?\n" +
                "WHERE id = ?;";
        try {
            if (checkIfQuestExists(quest.getId())) {
                PreparedStatement preparedStatement = getConnection().prepareStatement(query);
                preparedStatement.setString(1, quest.getTitle());
                preparedStatement.setString(2, String.valueOf(quest.getCategory()));
                preparedStatement.setString(3, quest.getDescription());
                preparedStatement.setInt(4, quest.getReward());
                preparedStatement.setInt(5, quest.getId());

                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public boolean checkIfQuestExists(String title) throws SQLException {
        String query = "SELECT * FROM quests WHERE title=?;";

        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setString(1, title);

        return preparedStatement.executeQuery().next();

    }

    public boolean checkIfQuestExists(int id) throws SQLException {
        String query = "SELECT * FROM quests WHERE id=?;";


        PreparedStatement preparedStatement = getConnection().prepareStatement(query);
        preparedStatement.setInt(1, id);

        return preparedStatement.executeQuery().next();

    }

}
