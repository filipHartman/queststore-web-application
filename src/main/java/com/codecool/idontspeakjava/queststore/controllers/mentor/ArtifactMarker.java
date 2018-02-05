package com.codecool.idontspeakjava.queststore.controllers.mentor;

import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteArtifactsDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteOrdersDAO;
import com.codecool.idontspeakjava.queststore.database.sqlite.SQLiteUserDAO;
import com.codecool.idontspeakjava.queststore.models.Order;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.views.MentorView;

import java.util.ArrayList;
import java.util.List;

class ArtifactMarker {
    private static final String EXIT = "0";

    private MentorView view;
    private List<User> codecoolers;
    private List<Order> orders;
    private int temporaryIndex;
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
        view.showUsers(getUsersFullNames());
        String userInput = view.getUserInput();
        boolean inputIsInvalid = validateInput(userInput, codecoolers.size());
        if (!userInput.equals(EXIT)) {
            if (!inputIsInvalid) {
                User selectedUser = codecoolers.get(temporaryIndex);
                orders = new SQLiteOrdersDAO().getAllOrdersByUser(selectedUser);
                filterOrders();
                view.showArtifactsToMark(createArtifactsToPrint());
                if (!orders.isEmpty()) {
                    userInput = view.getUserInput();
                    if (!userInput.equals(EXIT)) {
                        inputIsInvalid = validateInput(userInput, orders.size());
                        if (!inputIsInvalid) {
                            selectedOrder = orders.get(temporaryIndex);
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
        List<Order> filteredOrders = new ArrayList<>();
        for (Order order : orders) {
            if (!order.isUsed()) {
                filteredOrders.add(order);
            }
        }
        orders = filteredOrders;
    }

    private void toggleOrderAsUsed() {
        selectedOrder.setUsed(true);
        new SQLiteOrdersDAO().updateOrder(selectedOrder);
        view.showArtifactUsed();
    }

    private ArrayList<String> createArtifactsToPrint() {
        ArrayList<String> artifactsToPrint = new ArrayList<>();
        SQLiteArtifactsDAO artifactsDAO = new SQLiteArtifactsDAO();
        for (Order order : orders) {
            if (!order.isUsed()) {
                String artifactTitle = artifactsDAO.getArtifact(order.getArtifactID()).getTitle();
                artifactsToPrint.add(String.format("%s", artifactTitle));
            }
        }
        return artifactsToPrint;
    }

    private boolean validateInput(String input, int length) {
        boolean inputIsInvalid = true;
        if (input.matches("\\d+")) {
            int inputAsInt = Integer.parseInt(input);
            if (inputAsInt > 0 && inputAsInt <= length) {
                inputIsInvalid = false;
                temporaryIndex = inputAsInt - 1;
            } else if (inputAsInt != 0) {
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
