package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteWalletsDAO;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.TeamOrder;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class WalletsChecker {

    private MentorView view;
    private List<User> codecoolers;

    private static final String EXIT = "0";

    WalletsChecker(MentorView view) {
        this.view = view;
        codecoolers = new SQLiteUserDAO().getUsersByPermission(Permissions.Student);
    }

    void showWallets() {
        if (codecoolers.isEmpty()) {
            view.showNoUsers();
            return;
        }
        view.showUsersAndWallets(getUserFullNames(), getCoinsFromWallets());
        String input = view.getUserInput();

        if (input.equals(EXIT)) {
            view.showOperationCancelled();
        } else {
            showUserDetails(input);
        }
    }

    private void showUserDetails(String input) {
        if (input.equals(EXIT)) {
            view.showOperationCancelled();
        } else {
            if (!new Validator().isSelectFromListInvalid(codecoolers, input)) {
                printSelectedUser(codecoolers.get(Integer.parseInt(input) - 1));
            } else {
                view.showWrongDigitInput();
            }
        }
    }

    private void printSelectedUser(User user) {
        Wallet userWallet = new SQLiteWalletsDAO().getWalletByUserID(user.getId());

        String fullName = user.getFullName();
        String currentCoins = String.valueOf(userWallet.getCurrentState());
        String allEarnings = String.valueOf(userWallet.getTotalEarnings());

        List<String[]> ordersToPrint = createInfoAboutOrders(Utilities.createOrders(user));
        view.printUserWallet(fullName, currentCoins, allEarnings, ordersToPrint);
    }

    private List<String[]> createInfoAboutOrders(List<Order> orders) {
        final int TITLE = 0;
        final int IS_USED = 1;
        final int CATEGORY = 2;
        final int ARRAY_SIZE = 3;

        SQLiteArtifactsDAO dao = new SQLiteArtifactsDAO();
        List<String[]> ordersToPrint = new ArrayList<>();

        for (Order o : orders) {
            String[] arrayWithOrder = new String[ARRAY_SIZE];
            arrayWithOrder[TITLE] = dao.getArtifact(o.getArtifactID()).getTitle();
            arrayWithOrder[IS_USED] = o.isUsed() ? "Used" : "Not used";
            arrayWithOrder[CATEGORY] = o instanceof TeamOrder ? "Magic" : "Basic";
            ordersToPrint.add(arrayWithOrder);
        }
        return ordersToPrint;
    }

    private List<String> getUserFullNames() {
        return codecoolers.stream().map(User::getFullName).collect(Collectors.toList());
    }

    private List<String> getCoinsFromWallets() {
        SQLiteWalletsDAO dao = new SQLiteWalletsDAO();
        return codecoolers.stream().map(
                user -> String.valueOf(dao.getWalletByUserID(user.getId()).getCurrentState())).collect(Collectors.toList());
    }
}