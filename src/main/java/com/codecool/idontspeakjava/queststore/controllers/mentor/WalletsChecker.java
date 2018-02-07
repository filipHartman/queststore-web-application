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

        List<String> ordersToPrint = createInfoAboutOrders(Utilities.createOrders(user));
        view.printUserWallet(fullName, currentCoins, allEarnings, ordersToPrint);
    }

    private List<String> createInfoAboutOrders(List<Order> orders) {
        SQLiteArtifactsDAO dao = new SQLiteArtifactsDAO();
        return orders.stream().map(
                order -> String.format("%s - Is %s - %s",
                        dao.getArtifact(order.getArtifactID()).getTitle(),
                        order.isUsed() ? "used" : "not used",
                        order instanceof TeamOrder ? "magic artifact" : "normal artifact")).collect(Collectors.toList());
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