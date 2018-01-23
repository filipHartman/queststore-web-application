package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.MentorView;

public class MentorController {
    private MentorView view;
    private User mentor;

    private static final String CREATE_CODECOOLER = "1";
    private static final String ADD_QUEST = "2";
    private static final String ADD_ARTIFACT = "3";
    private static final String EDIT_QUEST = "4";
    private static final String EDIT_ARTIFACT = "5";
    private static final String MARK_QUEST = "6";
    private static final String MARK_ARTIFACT = "7";
    private static final String CHECK_WALLETS = "8";
    private static final String EXIT = "0";


    public MentorController(User user) {
        view = new MentorView();
        mentor = user;
    }

    public void run() {
        boolean runProgram = true;

        while (runProgram) {
            view.showMainMenu(mentor.getFirstName());
            runProgram = selectAction(view.getUserInput());
        }
    }

    private boolean selectAction(String input) {
        boolean continueRunning = true;
        switch (input) {
            case CREATE_CODECOOLER:
                new CodecoolerCreator(view).createCodecooler();
                break;
            case ADD_QUEST:
                new QuestCreator(view).create();
                break;
            case ADD_ARTIFACT:
                new ArtifactCreator(view).create();
                break;
            case EDIT_QUEST:
                new QuestEditor(view).editQuest();
                break;
            case EDIT_ARTIFACT:
                new ArtifactEditor(view).editArtifact();
                break;
            case MARK_QUEST:
                new QuestMarker(view).markQuest();
                break;
            case MARK_ARTIFACT:
                new ArtifactMarker(view).markArtifact();
                break;
            case CHECK_WALLETS:
                new WalletsChecker(view).showWallets();
                break;
            case EXIT:
                continueRunning = false;
                break;
            default:
                view.showWrongInput();
        }
        return continueRunning;
    }
}
