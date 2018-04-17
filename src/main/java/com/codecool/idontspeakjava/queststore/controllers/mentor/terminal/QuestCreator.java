package com.codecool.idontspeakjava.queststore.controllers.mentor.terminal;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteQuestsDAO;
import com.codecool.idontspeakjava.queststore.models.DummyItem;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.List;
import java.util.stream.Collectors;

class QuestCreator extends Creator {

    private static final int TITLE = 0;
    private static final int CATEGORY = 1;
    private static final int DESCRIPTION = 2;
    private static final int REWARD = 3;

    private DummyItem temporaryQuest;

    QuestCreator(MentorView view) {
        super(view);
        temporaryQuest = new DummyItem();
    }

    @Override
    void addToDatabase() {
        Quest quest = new Quest(
                temporaryQuest.getTitle(),
                temporaryQuest.getCategory().equals(Validator.BASIC_CATEGORY) ? QuestCategory.Basic : QuestCategory.Extra,
                temporaryQuest.getDescription(),
                temporaryQuest.getRewardOrPrice());
        SQLiteQuestsDAO questsDAO = new SQLiteQuestsDAO();
        questsDAO.createQuest(quest);
        view.showQuestCreated();
    }

    @Override
    void selectPrompt(int promptNumber) {
        switch (promptNumber) {
            case TITLE:
                view.askForQuestTitle();
                break;
            case CATEGORY:
                view.askForQuestCategory();
                break;
            case DESCRIPTION:
                view.askForQuestDescription();
                break;
            case REWARD:
                view.askForQuestReward();
                break;
        }
    }

    @Override
    boolean setAttribute(int promptNumber, String input) {
        boolean attributeNotSet;
        switch (promptNumber) {
            case TITLE:
                attributeNotSet = setTitle(temporaryQuest, input, getQuestsTitles(), view);
                break;
            case CATEGORY:
                attributeNotSet = setCategory(temporaryQuest, input, view);
                break;
            case DESCRIPTION:
                attributeNotSet = setDescription(temporaryQuest, input, view);
                break;
            case REWARD:
                attributeNotSet = setPriceOrReward(temporaryQuest, input, view);
                break;
            default:
                attributeNotSet = false;
        }
        return attributeNotSet;
    }

    private List<String> getQuestsTitles() {
        List<Quest> quests = new SQLiteQuestsDAO().getAllQuests();
        return quests.stream().map(Quest::getTitle).collect(Collectors.toList());
    }
}
