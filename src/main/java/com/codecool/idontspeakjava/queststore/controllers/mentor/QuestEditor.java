package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.QuestsDAO;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.QuestCategory;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


class QuestEditor {

    private static final String EDIT_TITLE = "1";
    private static final String EDIT_DESCRIPTION = "2";
    private static final String EDIT_CATEGORY = "3";
    private static final String EDIT_REWARD = "4";

    private static final String EXIT = "0";

    private MentorView view;
    private List<Quest> quests;
    private Quest selectedQuest;

    QuestEditor(MentorView view) {
        this.view = view;
        quests = new QuestsDAO().getAllQuests();
    }

    void editQuest() {
        boolean continueLoop = true;
        if (quests.isEmpty()) {
            view.showNoQuests();
            return;
        }
        while (continueLoop) {
            view.selectQuest(getQuestsTitles());

            String userInput = view.getUserInput();

            if (isQuestSelected(userInput)) {
                view.selectAttributeOfQuestToEdit();
                userInput = view.getUserInput();
                continueLoop = checkUserInput(userInput);
            } else {
                if (userInput.equals(EXIT)) {
                    continueLoop = false;
                }
                view.showWrongInput();
            }
        }
    }

    private boolean isQuestSelected(String input) {
        boolean inputIsValid = false;
        if (input.matches("\\d+")) {
            int inputAsInt = Integer.parseInt(input);
            if (inputAsInt > 0 && inputAsInt <= quests.size()) {
                selectedQuest = quests.get(inputAsInt - 1);
                inputIsValid = true;
            }
        }
        return inputIsValid;
    }

    private ArrayList<String> getQuestsTitles() {
        ArrayList<String> titles = new ArrayList<>();
        for (Quest quest : quests) {
            titles.add(quest.getTitle());
        }
        return titles;
    }

    private boolean checkUserInput(String input) {
        boolean continueProgram = true;
        switch (input) {
            case EDIT_TITLE:
                view.askForQuestTitle();
                editTitle(selectedQuest);
                break;
            case EDIT_DESCRIPTION:
                view.askForQuestDescription();
                editDescription(selectedQuest);
                break;
            case EDIT_CATEGORY:
                view.askForQuestCategory();
                editCategory(selectedQuest);
                break;
            case EDIT_REWARD:
                view.askForQuestReward();
                editReward(selectedQuest);
                break;
            case EXIT:
                view.showOperationCancelled();
                continueProgram = false;
                break;
            default:
                view.showWrongInput();
        }
        return continueProgram;
    }

    private void editReward(Quest quest) {
        view.showReward(String.valueOf(quest.getReward()));
        String newReward = view.getUserInput();
        if (newReward.equals(EXIT)) {
            view.showOperationCancelled();
            return;
        }
        if (newReward.matches("\\d+")) {
            int inputAsInt = Integer.valueOf(newReward);
            if (inputAsInt > 0) {
                quest.setReward(inputAsInt);
                new QuestsDAO().updateQuest(quest);
            } else {
                view.showInputMustBeHigherThanZero();
            }
        } else {
            view.showWrongDigitInput();
        }
    }

    private void editDescription(Quest quest) {
        view.showDescription(quest.getDescription());
        String newDescription = view.getUserInput();
        if (newDescription.equals(EXIT)) {
            view.showOperationCancelled();
            return;
        }
        if (newDescription.matches("[a-zA-Z1-9,.! ]+")) {
            quest.setDescription(newDescription);
            new QuestsDAO().updateQuest(quest);
        } else {
            view.showWrongDescriptionInput();
        }
    }

    private void editCategory(Quest quest) {
        final String BASIC = "1";
        final String EXTRA = "2";
        view.showCategory(quest.getCategory().toString());
        String newCategory = view.getUserInput();
        if (newCategory.equals(EXIT)) {
            view.showOperationCancelled();
            return;
        }
        QuestsDAO questsDAO = new QuestsDAO();
        switch (newCategory) {
            case BASIC:
                quest.setCategory(QuestCategory.Basic);
                questsDAO.updateQuest(quest);
                break;
            case EXTRA:
                quest.setCategory(QuestCategory.Extra);
                questsDAO.updateQuest(quest);
                break;
            default:
                view.showWrongInput();
        }
    }

    private void editTitle(Quest quest) {
        view.showTitle(quest.getTitle());
        String newTitle = view.getUserInput();
        if (newTitle.equals(EXIT)) {
            view.showOperationCancelled();
            return;
        }
        if (newTitle.matches("[a-zA-Z1-9 ]+")) {
            try {
                QuestsDAO questsDAO = new QuestsDAO();
                if (!questsDAO.checkIfQuestExists(newTitle)) {
                    quest.setTitle(newTitle);
                    questsDAO.updateQuest(quest);
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
    }
}
