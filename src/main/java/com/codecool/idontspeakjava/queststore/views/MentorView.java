package com.codecool.idontspeakjava.queststore.views;

import java.util.List;

public class MentorView extends UserView {

    private static final String ERROR_COLOR = Colors.RED_BRIGHT;
    private static final String MENU_HEADER_COLOR = Colors.BLUE_BOLD;
    private static final String MENU_OPTIONS_COLOR = Colors.CYAN;
    private static final String PROMPT_COLOR = Colors.GREEN;
    private static final String INFORMATION_COLOR = Colors.PURPLE_BRIGHT;
    private static final String VALUE_COLOR = Colors.YELLOW;

    public void showMainMenu(String userName) {
        clearScreen();
        System.out.print(MENU_OPTIONS_COLOR);
        printTable(new String[][]{
                {null, String.format("Hello %s ! Select your option", userName)},
                {"1", "Create new codecooler"},
                {"2", "Add new quest"},
                {"3", "Add new artifact"},
                {"4", "Edit existing quest"},
                {"5", "Edit existing artifact"},
                {"6", "Mark quest for a codecooler"},
                {"7", "Mark artifact for a codecooler"},
                {"8", "Check the wallets of codocoolers"},
                {"0", "Exit the program"}
        });
    }

    public void showWrongInput() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"Your input is wrong."}});
        continuePrompt();
    }

    public void showCodecoolerCreated() {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{{"You created a new codecooler"}});
        continuePrompt();
    }

    public void showCodecoolerCreationFailed() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"An error occurred. The new codecooler was not created"}});
        continuePrompt();
    }

    public void showOperationCancelled() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"Operation cancelled"}});
        continuePrompt();
    }

    public void askForCodecoolerName() {
        clearScreen();
        System.out.print(PROMPT_COLOR);
        printTable(new String[][]{{"Enter the name of codecoolers or 0 to cancel"}});
    }

    public void askForSecondName() {
        clearScreen();
        System.out.print(PROMPT_COLOR);
        printTable(new String[][]{{"Enter the second name of codecooler, or 0 to cancel"}});
    }

    public void askForEmail() {
        clearScreen();
        System.out.print(PROMPT_COLOR);
        printTable(new String[][]{{"Enter the codecooler email, or 0 to cancel"}});
    }

    public void askForQuestTitle() {
        System.out.print(PROMPT_COLOR);
        printTable(new String[][]{{"Enter the quest title or 0 to cancel"}});
    }

    public void askForQuestDescription() {
        System.out.print(PROMPT_COLOR);
        printTable(new String[][]{{"Enter the description of the quest or 0 to cancel"}});
    }

    public void askForQuestReward() {
        System.out.print(PROMPT_COLOR);
        printTable(new String[][]{{"Enter the reward for this quest, or 0 to cancel"}});
    }

    public void askForQuestCategory() {
        System.out.print(MENU_HEADER_COLOR);
        printTable(new String[][]{
                {null, "Select quest category"},
                {"1", "Basic"},
                {"2", "Extra"},
                {"0", "Cancel"}
        });
    }

    public void showWrongTitleInput() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"Wrong or duplicated title. Only letters, digits and whitespaces are allowed."}});
        continuePrompt();
    }

    public void showWrongDescriptionInput() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"Wrong description. Only letters, digits, whitespaces, commas and dots are allowed."}});
        continuePrompt();
    }

    public void showWrongDigitInput() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"Wrong input. Only digits are allowed"}});
        continuePrompt();
    }

    public void showQuestCreated() {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{{"The quest have been successfully created"}});
        continuePrompt();
    }

    public void showWrongEmailInput() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"Wrong input. The valid email address is -> localpart @ domain.com"}});
        continuePrompt();
    }

    public void askForCodecoolClass(List<String> classesTitles) {
        clearScreen();
        System.out.print(MENU_HEADER_COLOR);
        printTable(new String[][]{{"Select to which class you want to add student, or 0 to cancel"}});
        System.out.print(MENU_OPTIONS_COLOR);
        printTable(classesTitles);
    }

    public void showClassesDontExist() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"There aren't any classes. The student can't be created."}});
        continuePrompt();
    }

    public void askForArtifactTitle() {
        System.out.print(PROMPT_COLOR);
        printTable(new String[][]{{"Enter the name of new artifact or 0 to cancel"}});
    }

    public void askForArtifactCategory() {
        System.out.print(MENU_HEADER_COLOR);
        printTable(new String[][]{
                {null, "Select artifact category"},
                {"1", "Basic"},
                {"2", "Magic"},
                {"0", "Cancel"}
        });
    }

    public void showArtifactCreated() {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{{"New artifact have been created."}});
        continuePrompt();
    }

    public void askForArtifactDescription() {
        System.out.print(PROMPT_COLOR);
        printTable(new String[][]{{"Enter the description of your artifact or 0 to cancel"}});
    }

    public void askForArtifactPrice() {
        System.out.print(PROMPT_COLOR);
        printTable(new String[][]{{"Enter the price of your artifact or 0 to cancel"}});
    }

    public void showTitle(String title) {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{{"The current title", title}});
    }

    public void showCategory(String category) {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{{"The current category", category}});
    }

    public void showDescription(String description) {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{
                {"The current description"},
                {description}});
    }

    public void showReward(String reward) {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{{"The current reward", reward}});
    }

    public void showPrice(String price) {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{{"The current price", price}});
    }

    public void showThereIsNothingToShow() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"There aren't anything to show"}});
        continuePrompt();
    }

    public void printListForSelection(List<String> titles) {
        clearScreen();
        System.out.print(MENU_HEADER_COLOR);
        printTable(new String[][]{{"Select what to edit or 0 to cancel"}});
        System.out.print(MENU_OPTIONS_COLOR);
        printTable(titles);
    }

    public void selectAttributeToEdit(String priceOrReward) {
        clearScreen();
        System.out.print(MENU_OPTIONS_COLOR);
        printTable(new String[][]{
                {null, "Select your option"},
                {"1", "Edit title"},
                {"2", "Edit description"},
                {"3", "Edit category"},
                {"4", String.format("Edit %s", priceOrReward)},
                {"0", "Cancel"}});
    }

    public void showNoUsers() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"There are no codecoolers"}});
        continuePrompt();
    }

    public void showUsersAndWallets(List<String> userFullNames, List<String> coinsFromWallets) {
        clearScreen();
        final int INDEX = 0;
        final int FULL_NAME = 1;
        final int COINS = 2;
        final int COLUMNS = 3;

        System.out.print(MENU_HEADER_COLOR);
        printTable(new String[][]{{"Select which user details you want to show, or 0 to cancel"}});
        System.out.print(MENU_OPTIONS_COLOR);

        String[][] rows = new String[userFullNames.size() + 1][COLUMNS];
        rows[0][INDEX] = "Index";
        rows[0][FULL_NAME] = "Full name";
        rows[0][COINS] = "Coins in wallet";

        for (int i = 0; i < userFullNames.size(); i++) {
            rows[i + 1][INDEX] = String.valueOf(i + 1);
            rows[i + 1][FULL_NAME] = userFullNames.get(i);
            rows[i + 1][COINS] = coinsFromWallets.get(i);
        }
        printTable(rows);
    }

    public void printUserWallet(String fullName, String currentCoins, String allEarnings, List<String[]> ordersToPrint) {
        clearScreen();
        final int TITLE = 0;
        final int IS_USED = 1;
        final int CATEGORY = 2;
        final int COLUMNS = 3;

        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{
                {"Full name", fullName},
                {"Current coins", currentCoins},
                {"All earnings", allEarnings}});

        if (!ordersToPrint.isEmpty()) {
            System.out.print(VALUE_COLOR);
            printTable(new String[][]{{"History of bought artifacts:"}});
            System.out.print(INFORMATION_COLOR);
            String[][] orders = new String[ordersToPrint.size() + 1][COLUMNS];
            orders[0][TITLE] = "Title";
            orders[0][IS_USED] = "Is used";
            orders[0][CATEGORY] = "Category";
            for (int i = 0; i < ordersToPrint.size(); i++) {
                orders[i + 1][TITLE] = ordersToPrint.get(i)[TITLE];
                orders[i + 1][IS_USED] = ordersToPrint.get(i)[IS_USED];
                orders[i + 1][CATEGORY] = ordersToPrint.get(i)[CATEGORY];
            }
            printTable(orders);
        } else {
            System.out.print(ERROR_COLOR);
            printTable(new String[][]{{"This codecooler didn't bought any artifacts"}});
        }
        continuePrompt();
    }

    public void showMarkingNotPossible() {
        clearScreen();
        System.out.print(ERROR_COLOR);
        printTable(new String[][]{{"There are no quests or codecoolers"}});
        continuePrompt();
    }

    public void showUsers(List<String> usersFullNames) {
        clearScreen();
        System.out.print(MENU_HEADER_COLOR);
        printTable(new String[][]{{"Select user to mark or 0 to cancel"}});
        System.out.print(MENU_OPTIONS_COLOR);
        printTable(usersFullNames);
    }

    public void showCoinsAdded() {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{{"Coins for the completion of the quest have been added"}});
        continuePrompt();
    }

    public void showQuests(List<String[]> questsToPrint) {
        final int INDEX = 0;
        final int TITLE = 1;
        final int REWARD = 2;
        final int COLUMNS = 3;

        clearScreen();
        System.out.print(MENU_HEADER_COLOR);
        printTable(new String[][]{{"Select quest to mark"}});
        System.out.print(MENU_OPTIONS_COLOR);

        String[][] rows = new String[questsToPrint.size() + 1][COLUMNS];
        rows[0][INDEX] = "Index";
        rows[0][TITLE] = "Title";
        rows[0][REWARD] = "Reward";

        for (int i = 0; i < questsToPrint.size(); i++) {
            rows[i + 1][INDEX] = String.valueOf(i + 1);
            rows[i + 1][TITLE] = questsToPrint.get(i)[TITLE];
            rows[i + 1][REWARD] = questsToPrint.get(i)[REWARD];
        }
        printTable(rows);
    }

    public void showArtifactsToMark(List<String> artifactsToPrint) {
        clearScreen();
        System.out.print(MENU_HEADER_COLOR);
        printTable(new String[][]{{"Select artifact to activate or 0 to cancel"}});
        System.out.print(MENU_OPTIONS_COLOR);
        printTable(artifactsToPrint);
    }

    public void showArtifactUsed() {
        clearScreen();
        System.out.print(INFORMATION_COLOR);
        printTable(new String[][]{{"This artifact have been marked as used"}});
        continuePrompt();
    }
}
