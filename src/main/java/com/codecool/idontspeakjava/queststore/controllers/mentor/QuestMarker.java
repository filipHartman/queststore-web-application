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
        quests = new QuestsDAO().getAllQuests();
        codecoolers = new UserDAO().getUsersByPermission(Permissions.Student);
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

    private List<String> getUsersFullNames() {
        return codecoolers.stream().map(codecooler -> String.format(
                "%s %s", codecooler.getFirstName(), codecooler.getLastName())).collect(Collectors.toList());
    }
}
