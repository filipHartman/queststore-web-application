package com.codecool.idontspeakjava.queststore.controllers.mentor.terminal;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteQuestsDAO;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.List;
import java.util.stream.Collectors;


class QuestEditor extends Editor {

    private static final String EDIT_TITLE = "1";
    private static final String EDIT_DESCRIPTION = "2";
    private static final String EDIT_CATEGORY = "3";
    private static final String EDIT_REWARD = "4";

    private List<Quest> quests;

    QuestEditor(MentorView view) {
        super(view, "Quest");
        quests = new SQLiteQuestsDAO().getAllQuests();
    }

    @Override
    void selectPrompt(String input) {
        Quest quest = quests.get(indexOfItemToEdit);
        switch (input) {
            case EDIT_TITLE:
                view.showTitle(quest.getTitle());
                view.askForQuestTitle();
                break;
            case EDIT_CATEGORY:
                view.showCategory(quest.getCategory().toString());
                view.askForQuestCategory();
                break;
            case EDIT_DESCRIPTION:
                view.showDescription(quest.getDescription());
                view.askForQuestDescription();
                break;
            case EDIT_REWARD:
                view.showReward(String.valueOf(quest.getReward()));
                view.askForQuestReward();
                break;
            default:
                view.showWrongInput();
        }
    }

    @Override
    void editAttribute(String selectedOption, String input) {
        Quest quest = quests.get(indexOfItemToEdit);
        List<String> titles = getTitles();

        switch (selectedOption) {
            case EDIT_TITLE:
                editTitle(quest, titles, input);
                break;
            case EDIT_CATEGORY:
                editCategory(quest, input);
                break;
            case EDIT_DESCRIPTION:
                editDescription(quest, input);
                break;
            case EDIT_REWARD:
                editReward(quest, input);
                break;
        }
    }

    @Override
    public List getCollection() {
        return quests;
    }

    @Override
    public List<String> getTitles() {
        return quests.stream().map(Quest::getTitle).collect(Collectors.toList());
    }
}
