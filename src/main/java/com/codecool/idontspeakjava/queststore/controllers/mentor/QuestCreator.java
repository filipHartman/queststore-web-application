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
}
