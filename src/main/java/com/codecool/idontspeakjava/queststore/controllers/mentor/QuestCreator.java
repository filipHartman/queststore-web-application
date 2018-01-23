package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.QuestsDAO;
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

    private static final String EXIT = "0";
    private MentorView view;

    private DummyItem temporaryQuest;

    QuestCreator(MentorView view) {
        this.view = view;
        temporaryQuest = new DummyItem();
    }

    void createQuest() {
        int inputsReceived = 0;
        boolean continueLoop = true;

        final int PROMPTS = 4;

        for (int i = 0; i < PROMPTS && continueLoop; i++) {
            boolean continueIteration = true;
            while (continueIteration) {
                selectPromptForCreateQuest(i);
                String input = view.getUserInput();

                if (input.equals(EXIT)) {
                    continueLoop = false;
                    continueIteration = false;
                } else {
                    continueIteration = !setAttribute(i, input);
                }
            }
            if (continueLoop) {
                inputsReceived++;
            }
        }

        if (inputsReceived == PROMPTS) {
            addQuestToDatabase();
        } else {
            view.showOperationCancelled();
        }
    }

    private void addQuestToDatabase() {
        Quest quest = new Quest(
                temporaryQuest.getTitle(),
                temporaryQuest.getCategory().equals(BASIC_CATEGORY) ? QuestCategory.Basic : QuestCategory.Extra,
                temporaryQuest.getDescription(),
                temporaryQuest.getRewardOrPrice());
        QuestsDAO questsDAO = new QuestsDAO();
        questsDAO.createQuest(quest);
        view.showQuestCreated();
    }

    private void selectPromptForCreateQuest(int promptNumber) {
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

    private boolean setAttribute(int promptNumber, String input) {
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
        List<Quest> quests = new QuestsDAO().getAllQuests();
        return quests.stream().map(Quest::getTitle).collect(Collectors.toList());
    }
}
