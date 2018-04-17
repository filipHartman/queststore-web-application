package com.codecool.idontspeakjava.queststore.controllers.mentor.terminal;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteOrdersDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.TeamOrder;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.List;
import java.util.stream.Collectors;

class ArtifactMarker {
    private static final String EXIT = "0";

    private MentorView view;
    private List<User> codecoolers;
    private List<Order> orders;
    private Order selectedOrder;

    ArtifactMarker(MentorView view) {
        this.view = view;
        codecoolers = new SQLiteUserDAO().getUsersByPermission(Permissions.Student);
    }

    void markArtifact() {
        if (codecoolers.isEmpty()) {
            view.showNoUsers();
            return;
        }
        Validator validator = new Validator();
        view.showUsers(getUsersFullNames());
        String userInput = view.getUserInput();

        if (!userInput.equals(EXIT)) {
            if (!validator.isSelectFromListInvalid(codecoolers, userInput)) {
                User selectedUser = codecoolers.get(Integer.parseInt(userInput) - 1);
                orders = Utilities.createOrders(selectedUser);
                filterOrders();
                view.showArtifactsToMark(createArtifactsToPrint());
                if (!orders.isEmpty()) {
                    userInput = view.getUserInput();
                    if (!userInput.equals(EXIT)) {
                        if (!validator.isSelectFromListInvalid(orders, userInput)) {
                            selectedOrder = orders.get(Integer.parseInt(userInput) - 1);
                            toggleOrderAsUsed();
                        } else {
                            view.showWrongInput();
                        }
                    } else {
                        view.showOperationCancelled();
                    }
                } else {
                    view.showThereIsNothingToShow();
                }
            } else {
                view.showWrongInput();
            }
        } else {
            view.showOperationCancelled();
        }

    }

    private void filterOrders() {
        orders = orders.stream().filter(order -> !order.isUsed()).collect(Collectors.toList());
    }

    private void toggleOrderAsUsed() {
        selectedOrder.setUsed(true);
        if (selectedOrder instanceof TeamOrder) {
            new SQLiteOrdersDAO().updateOrder((TeamOrder) selectedOrder);
        } else {
            new SQLiteOrdersDAO().updateOrder(selectedOrder);
        }
        view.showArtifactUsed();
    }

    private List<String> createArtifactsToPrint() {
        SQLiteArtifactsDAO artifactsDAO = new SQLiteArtifactsDAO();
        return orders.stream()
                .filter(order -> !order.isUsed())
                .map(order -> artifactsDAO.getArtifact(order.getArtifactID()).getTitle())
                .collect(Collectors.toList());
    }

    private List<String> getUsersFullNames() {
        return codecoolers.stream().map(User::getFullName).collect(Collectors.toList());
    }
}
