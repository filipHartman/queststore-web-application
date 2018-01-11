package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.QuestsDAO;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.sql.SQLException;

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

    private void addQuestToDatabase() {
        Quest quest = new Quest(title, category, description, reward);
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
            int inputAsInt = Integer.valueOf(input);
            if (inputAsInt > 0) {
                reward = inputAsInt;
                rewardNotSet = false;
            } else {
                view.showInputMustBeHigherThanZero();
            }
        } else {
            view.showWrongDigitInput();
        }
        return rewardNotSet;
    }

    private boolean setDescription(String input) {
        boolean descriptionNotSet = true;
        if (input.matches("[a-zA-Z1-9,.! ]+")) {
            description = input;
            descriptionNotSet = false;
        } else {
            view.showWrongDescriptionInput();
        }
        return descriptionNotSet;
    }

    private boolean setCategory(String input) {
        final String BASIC = "1";
        final String EXTRA = "2";

        boolean categoryNotSet = false;

        switch (input) {
            case BASIC:
                category = QuestCategory.Basic;
                break;
            case EXTRA:
                category = QuestCategory.Extra;
                break;
            default:
                categoryNotSet = true;
                view.showWrongInput();
        }
        return categoryNotSet;
    }

    private boolean setTitle(String input) {
        boolean titleNotSet = true;
        if (input.matches("[a-zA-Z1-9 ]+")) {
            try {
                if (!new QuestsDAO().checkIfQuestExists(input)) {
                    title = input;
                    titleNotSet = false;
                } else {
                    view.showDuplicateWarning();
                }
            } catch (SQLException e) {
                e.printStackTrace();
                view.showDatabaseError();
            }
        } else {
            view.showWrongTitleInput();
        }
        return titleNotSet;
    }
}
