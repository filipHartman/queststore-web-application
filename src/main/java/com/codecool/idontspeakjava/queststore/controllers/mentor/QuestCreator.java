package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import com.codecool.idontspeakjava.queststore.views.MentorView;

class QuestCreator {

    private static final int TITLE = 0;
    private static final int CATEGORY = 1;
    private static final int DESCRIPTION = 2;
    private static final int REWARD = 3;

    private static final String EXIT = "0";
    private MentorView view;

    private String title;
    private QuestCategory category;
    private String description;
    private int reward;

    QuestCreator(MentorView view) {
        this.view = view;
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
                    continueIteration = setAttribute(i, input);
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
                attributeNotSet = setTitle(input);
                break;
            case CATEGORY:
                attributeNotSet = setCategory(input);
                break;
            case DESCRIPTION:
                attributeNotSet = setDescription(input);
                break;
            case REWARD:
                attributeNotSet = setReward(input);
                break;
            default:
                attributeNotSet = false;
        }
        return attributeNotSet;
    }

    private boolean setReward(String input) {
        boolean rewardNotSet = true;
        if (input.matches("\\d+")) {
            reward = Integer.valueOf(input);
            rewardNotSet = false;
        } else {
            view.showWrongRewardInput();
        }
        return rewardNotSet;
    }
}
