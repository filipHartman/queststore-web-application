package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.WalletsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteQuestsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteWalletsDAO;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.Quest;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.List;
import java.util.stream.Collectors;

public class QuestMarker {

    private static final String EXIT = "0";

    private MentorView view;
    private List<Quest> quests;
    private List<User> codecoolers;

    private User selectedUser;
    private Quest selectedQuest;

    QuestMarker(MentorView view) {
        this.view = view;
        quests = new SQLiteQuestsDAO().getAllQuests();
        codecoolers = new SQLiteUserDAO().getUsersByPermission(Permissions.Student);
    }

    void markQuest() {
        Validator validator = new Validator();
        int temporaryIndex;

        if (quests.isEmpty() || codecoolers.isEmpty()) {
            view.showMarkingNotPossible();
            return;
        }
        view.showUsers(getUsersFullNames());
        String input = view.getUserInput();

        if (input.equals(EXIT)) {
            view.showOperationCancelled();
        } else {
            if (validator.isSelectFromListInvalid(codecoolers, input)) {
                view.showWrongInput();
            } else {
                temporaryIndex = Integer.parseInt(input) - 1;
                selectedUser = codecoolers.get(temporaryIndex);
                view.showQuests(createQuestsToPrint());
                input = view.getUserInput();
                if (input.equals(EXIT)) {
                    view.showOperationCancelled();
                } else {
                    if (validator.isSelectFromListInvalid(quests, input)) {
                        view.showWrongInput();
                    } else {
                        temporaryIndex = Integer.parseInt(input) - 1;
                        selectedQuest = quests.get(temporaryIndex);
                        addCoinsFromQuest();
                    }
                }
            }
        }
    }

    private List<String> createQuestsToPrint() {
        return quests.stream().map(q -> String.format("%s - reward : %d", q.getTitle(), q.getReward())).collect(Collectors.toList());
    }

    private void addCoinsFromQuest() {
        int reward = selectedQuest.getReward();
        WalletsDAO walletsDAO = new SQLiteWalletsDAO();
        Wallet wallet = walletsDAO.getWalletByUserID(selectedUser.getId());

        long currentCoins = wallet.getCurrentState();
        long totalEarnings = wallet.getTotalEarnings();
        wallet.setCurrentState(currentCoins + reward);
        wallet.setTotalEarnings(totalEarnings + reward);

        walletsDAO.updateWallet(wallet);
        view.showCoinsAdded();
    }

    private List<String> getUsersFullNames() {
        return codecoolers.stream().map(codecooler -> String.format(
                "%s %s", codecooler.getFirstName(), codecooler.getLastName())).collect(Collectors.toList());
    }
}
