package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.QuestsDAO;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.database.WalletsDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.ArrayList;
import java.util.List;

public class QuestMarker {

    private static final String EXIT = "0";

    private MentorView view;
    private List<Quest> quests;
    private List<User> codecoolers;

    private int temporaryIndex;
    private User selectedUser;
    private Quest selectedQuest;

    QuestMarker(MentorView view) {
        this.view = view;
        quests = new QuestsDAO().getAllQuests();
        codecoolers = new UserDAO().getUsersByPermission(Permissions.Student);
    }

    void markQuest() {
        if (quests.isEmpty() || codecoolers.isEmpty()) {
            view.showMarkingNotPossible();
            return;
        }
        view.showUsers(getUsersFullNames());
        String input = view.getUserInput();

        if (input.equals(EXIT)) {
            view.showOperationCancelled();
        } else {
            boolean inputIsInvalid = validateInput(input, codecoolers.size());
            if (inputIsInvalid) {
                view.showWrongInput();
            } else {
                selectedUser = codecoolers.get(temporaryIndex);
                view.showQuests(createQuestsToPrint());
                input = view.getUserInput();
                if (input.equals(EXIT)) {
                    view.showOperationCancelled();
                } else {
                    inputIsInvalid = validateInput(input, quests.size());
                    if (inputIsInvalid) {
                        view.showWrongInput();
                    } else {
                        selectedQuest = quests.get(temporaryIndex);
                        addCoinsFromQuest();
                    }
                }
            }
        }
    }

    private ArrayList<String> createQuestsToPrint() {
        ArrayList<String> questsToPrint = new ArrayList<>();
        for (Quest quest : quests) {
            questsToPrint.add(String.format("%s - reward : %d", quest.getTitle(), quest.getReward()));
        }
        return questsToPrint;
    }

    private void addCoinsFromQuest() {
        int reward = selectedQuest.getReward();
        WalletsDAO walletsDAO = new WalletsDAO();
        Wallet wallet = walletsDAO.getWalletByUserID(selectedUser.getId());

        long currentCoins = wallet.getCurrentState();
        long totalEarnings = wallet.getTotalEarnings();
        wallet.setCurrentState(currentCoins + reward);
        wallet.setTotalEarnings(totalEarnings + reward);

        walletsDAO.updateWallet(wallet);
        view.showCoinsAdded();
    }

    private boolean validateInput(String input, int length) {
        boolean inputIsInvalid = true;
        if (input.matches("\\d+")) {
            int inputAsInt = Integer.parseInt(input);
            if (inputAsInt > 0 && inputAsInt <= length) {
                inputIsInvalid = false;
                temporaryIndex = inputAsInt - 1;
            } else {
                view.showWrongInput();
            }
        } else {
            view.showWrongInput();
        }
        return inputIsInvalid;
    }

    private ArrayList<String> getUsersFullNames() {
        ArrayList<String> usersFullNames = new ArrayList<>();
        for (User user : codecoolers) {
            usersFullNames.add(String.format("%s %s", user.getFirstName(), user.getLastName()));
        }
        return usersFullNames;
    }
}
