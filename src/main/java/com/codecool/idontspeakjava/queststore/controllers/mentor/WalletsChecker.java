package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.WalletsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteOrdersDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteWalletsDAO;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.Wallet;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.ArrayList;
import java.util.List;

class WalletsChecker {

    private MentorView view;
    private List<User> codecoolers;
    private WalletsDAO walletsDAO;

    private static final String EXIT = "0";

    WalletsChecker(MentorView view) {
        this.view = view;
        walletsDAO = new SQLiteWalletsDAO();
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
            if (input.matches("\\d+")) {
                int inputAsInt = Integer.parseInt(input);
                if (inputAsInt > 0 && inputAsInt <= codecoolers.size()) {
                    printSelectedUser(codecoolers.get(inputAsInt - 1));
                }
            } else {
                view.showWrongDigitInput();
            }
        }
    }

    private void printSelectedUser(User user) {
        Wallet userWallet = walletsDAO.getWalletByUserID(user.getId());
        String fullName = String.format("%s %s", user.getFirstName(), user.getLastName());
        String currentCoins = String.valueOf(userWallet.getCurrentState());
        String allEarnings = String.valueOf(userWallet.getTotalEarnings());
        List<Order> orders = new SQLiteOrdersDAO().getAllOrdersByUser(user);
        ArrayList<String> ordersToPrint = createInfoAboutOrders(orders);

        view.printUserWallet(fullName, currentCoins, allEarnings, ordersToPrint);
    }

    private ArrayList<String> createInfoAboutOrders(List<Order> orders) {
        ArrayList<String> infoAboutOrders = new ArrayList<>();
        SQLiteArtifactsDAO artifactsDAO = new SQLiteArtifactsDAO();
        for (Order order : orders) {
            String isUsed = order.isUsed() ? "used" : "not used";
            String artifactTitle = artifactsDAO.getArtifact(order.getArtifactID()).getTitle();
            String info = String.format("%s - Is %s", artifactTitle, isUsed);

            infoAboutOrders.add(info);
        }
        return infoAboutOrders;
    }

    private ArrayList<String> getUserFullNames() {
        ArrayList<String> fullNames = new ArrayList<>();
        for (User user : codecoolers) {
            fullNames.add(String.format("%s %s", user.getFirstName(), user.getLastName()));
        }
        return fullNames;
    }

    private ArrayList<String> getCoinsFromWallets() {
        ArrayList<String> coinsInWallets = new ArrayList<>();
        for (User user : codecoolers) {
            Wallet wallet = walletsDAO.getWalletByUserID(user.getId());
            coinsInWallets.add(String.valueOf(wallet.getCurrentState()));
        }
        return coinsInWallets;
    }

}