package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.models.Permissions;
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


    public MentorController() {
        view = new MentorView();
        mentor = new User("Henryk", "Pryk", "ahbgd", "h.pryk@email.com", Permissions.Mentor);
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
                new QuestCreator(view).createQuest();
                break;
            case ADD_ARTIFACT:
                addArtifact();
                break;
            case EDIT_QUEST:
                editQuest();
                break;
            case EDIT_ARTIFACT:
                editArtifact();
                break;
            case MARK_QUEST:
                markQuest();
                break;
            case MARK_ARTIFACT:
                markArtifact();
                break;
            case CHECK_WALLETS:
                checkWallets();
                break;
            case EXIT:
                continueRunning = false;
                break;
            default:
                view.showWrongInput();
        }
        return continueRunning;
    }

    private void addArtifact() {

    }

    private void editQuest() {

    }

    private void editArtifact() {

    }

    private void markQuest() {

    }

    private void markArtifact() {

    }

    private void checkWallets() {

    }
}
