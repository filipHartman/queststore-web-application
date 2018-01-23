package com.codecool.idontspeakjava.queststore.views;


import java.util.ArrayList;
import java.util.ListIterator;

public class MentorView extends UserView {

    private static final String ERROR_COLOR = Colors.RED_BRIGHT;
    private static final String MENU_HEADER_COLOR = Colors.BLUE_BOLD;
    private static final String MENU_OPTIONS_COLOR = Colors.CYAN;
    private static final String INPUT_COLOR = Colors.WHITE;
    private static final String PROMPT_COLOR = Colors.GREEN;
    private static final String INFORMATION_COLOR = Colors.PURPLE_BRIGHT;
    private static final String VALUE_COLOR = Colors.YELLOW;

    public void showMainMenu(String userName) {
        clearScreen();
        System.out.println(String.format(
                "%sHello %s ! Select what you want to do:\n", MENU_HEADER_COLOR, userName));
        System.out.println(String.format(
                "%s1 - Create new codecooler\n" +
                        "2 - Add new quest\n" +
                        "3 - Add new artifact\n" +
                        "4 - Edit existing quest\n" +
                        "5 - Edit existing artifact\n" +
                        "6 - Mark quest for a codecooler\n" +
                        "7 - Mark artifact for a codecooler\n" +
                        "8 - Check the wallets of codecoolers\n" +
                        "0 - Exit the program\n", MENU_OPTIONS_COLOR));
    }

    public void showWrongInput() {
        clearScreen();
        System.out.println(String.format(
                "%sYour input is wrong.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showCodecoolerCreated() {
        clearScreen();
        System.out.println(String.format(
                "%sYou created a new codecooler.\n", INFORMATION_COLOR));
        continuePrompt();
    }

    public void showCodecoolerCreationFailed() {
        clearScreen();
        System.out.println(String.format(
                "%sAn error occurred. The new codecooler wasn't created.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showOperationCancelled() {
        clearScreen();
        System.out.println(String.format(
                "%sOperation cancelled.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void askForCodecoolerName() {
        clearScreen();
        System.out.print(String.format(
                "%sEnter the name of codecooler's or 0 to cancel: %s", PROMPT_COLOR, INPUT_COLOR));
    }

    public void askForSecondName() {
        clearScreen();
        System.out.print(String.format(
                "%sEnter the second name of codecooler's or 0 to cancel: %s", PROMPT_COLOR, INPUT_COLOR));
    }

    public void askForEmail() {
        clearScreen();
        System.out.print(String.format("%sEnter the codecooler's email or 0 to cancel: %s", PROMPT_COLOR, INPUT_COLOR));
    }

    public void askForQuestTitle() {
        clearScreen();
        System.out.print(String.format("%sEnter the quest title or 0 to cancel: %s", PROMPT_COLOR, INPUT_COLOR));
    }

    public void askForQuestDescription() {
        clearScreen();
        System.out.print(String.format("%sEnter the description of the quest or 0 to cancel: %s", PROMPT_COLOR, INPUT_COLOR));
    }

    public void askForQuestReward() {
        clearScreen();
        System.out.print(String.format("%sEnter the reward for the quest or 0 to cancel: %s", PROMPT_COLOR, INPUT_COLOR));
    }

    public void askForQuestCategory() {
        clearScreen();
        System.out.println(String.format("%sSelect quest category:\n", MENU_HEADER_COLOR));
        System.out.println(String.format("%s1 - Basic\n2 - Extra\n0 - Cancel\n%s", MENU_OPTIONS_COLOR, INPUT_COLOR));
    }

    public void showWrongTitleInput() {
        clearScreen();
        System.out.println(String.format(
                "%sWrong input. You can use only letters, digits and whitespaces.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showWrongDescriptionInput() {
        clearScreen();
        System.out.println(String.format(
                "%sWrong input. You can use only letters, digits, whitespaces, commas, dots and exclamation marks\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showWrongDigitInput() {
        clearScreen();
        System.out.println(String.format("%sWrong input. You can use only digits.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showQuestCreated() {
        clearScreen();
        System.out.println(String.format("%sThe quest have been successfully created.\n", INFORMATION_COLOR));
        continuePrompt();
    }

    public void showWrongNameInput() {
        clearScreen();
        System.out.println(String.format("%sWrong input. You can only use letters.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showWrongEmailInput() {
        clearScreen();
        System.out.println(String.format("%sWrong input. This is not a valid email address.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void askForCodecoolClass(ArrayList<String> classesTitles) {
        clearScreen();
        ListIterator<String> iterator = classesTitles.listIterator();
        System.out.println(String.format("%sSelect to which class you want to add student, or 0 to cancel:\n", MENU_HEADER_COLOR));
        for (; iterator.hasNext(); ) {
            String title = iterator.next();
            System.out.println(String.format("%s%d. %s", MENU_OPTIONS_COLOR, iterator.nextIndex(), title));
        }
        System.out.print(String.format("\n%s", INPUT_COLOR));
    }

    public void showClassesDontExist() {
        clearScreen();
        System.out.println(String.format("%sThere aren't any classes. The student can't be created.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showInputMustBeHigherThanZero() {
        System.out.println(String.format("%sWrong input. The value must be higher than zero.\n", ERROR_COLOR));
    }

    public void askForArtifactTitle() {
        clearScreen();
        System.out.println(String.format("%sEnter the name of new artifact or 0 to cancel: %s", PROMPT_COLOR, INPUT_COLOR));
    }

    public void askForArtifactCategory() {
        clearScreen();
        System.out.println(String.format("%sSelect artifact category:\n", MENU_HEADER_COLOR));
        System.out.println(String.format("%s1 - Basic\n2 - Magic\n0 - Cancel\n%s", MENU_OPTIONS_COLOR, INPUT_COLOR));
    }

    public void showArtifactCreated() {
        clearScreen();
        System.out.println(String.format("%sNew artifact have been created.\n", INFORMATION_COLOR));
        continuePrompt();
    }

    public void askForArtifactDescription() {
        clearScreen();
        System.out.print(String.format(
                "%sEnter the description of your artifact or 0 to cancel: %s", PROMPT_COLOR, INPUT_COLOR));
    }

    public void askForArtifactPrice() {
        clearScreen();
        System.out.print(String.format(
                "%sEnter the price of your artifact or 0 to cancel: %s", PROMPT_COLOR, INPUT_COLOR));
    }

    public void showDuplicateWarning() {
        clearScreen();
        System.out.println(String.format(
                "%sYou can't add position with this value. It is already in the database.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showDatabaseError() {
        clearScreen();
        System.out.println(String.format("%sAn error in the database occurred.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showNoQuests() {
        clearScreen();
        System.out.println(String.format("%sThere are no quests to edit.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void selectQuest(ArrayList<String> questTitles) {
        clearScreen();
        ListIterator<String> iterator = questTitles.listIterator();

        System.out.println(String.format("%sSelect quest to edit or 0 to cancel:\n", MENU_HEADER_COLOR));
        for (; iterator.hasNext(); ) {
            String title = iterator.next();
            System.out.println(String.format("%s%d. %s", MENU_OPTIONS_COLOR, iterator.nextIndex(), title));
        }
    }

    public void selectAttributeOfQuestToEdit() {
        clearScreen();
        System.out.println(String.format("%sSelect what you want to edit, or 0 to cancel:\n", MENU_HEADER_COLOR));
        System.out.println(String.format("%s1 - Title\n2 - Description\n3 - Category\n4 - Reward\n", MENU_OPTIONS_COLOR));

    }

    public void showTitle(String title) {
        System.out.println(String.format(
                "%s\nThe current title: %s%s%s\n", INFORMATION_COLOR, VALUE_COLOR, title, INPUT_COLOR));
    }

    public void showCategory(String category) {
        System.out.println(String.format(
                "%s\nThe current category : %s%s%s\n", INFORMATION_COLOR, VALUE_COLOR, category, INPUT_COLOR));
    }

    public void showDescription(String description) {
        System.out.println(String.format(
                "%s\nThe current description: %s%s%s\n", INFORMATION_COLOR, VALUE_COLOR, description, INPUT_COLOR));
    }

    public void showReward(String reward) {
        System.out.println(String.format(
                "%s\nThe current reward is: %s%s%s\n", INFORMATION_COLOR, VALUE_COLOR, reward, INPUT_COLOR));
    }

    public void showPrice(String price) {
        System.out.println(String.format(
                "%s\nThe current price is: %s%s%s\n", INFORMATION_COLOR, VALUE_COLOR, price, INPUT_COLOR));
    }

    public void showNoArtifacts() {
        clearScreen();
        System.out.println(String.format("%sThere aren't any artifacts.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void selectArtifacts(ArrayList<String> artifactsTitles) {
        clearScreen();
        ListIterator<String> iterator = artifactsTitles.listIterator();

        System.out.println(String.format("%sSelect artifact to edit or 0 to cancel:\n", MENU_HEADER_COLOR));
        for (; iterator.hasNext(); ) {
            String title = iterator.next();
            System.out.println(String.format("%s%d. %s", MENU_OPTIONS_COLOR, iterator.nextIndex(), title));
        }
    }

    public void selectAttributeOfArtifactToEdit() {
        clearScreen();
        System.out.println(String.format("%sSelect what you want to edit, or 0 to cancel:\n", MENU_HEADER_COLOR));
        System.out.println(String.format("%s1 - Title\n2 - Description\n3 - Category\n4 - Price\n", MENU_OPTIONS_COLOR));
    }

    public void showNoUsers() {
        clearScreen();
        System.out.println(String.format("%sThere are no codecoolers\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showUsersAndWallets(ArrayList<String> userFullNames, ArrayList<String> coinsFromWallets) {
        clearScreen();
        System.out.println(String.format("%sSelect which user details you want to show or 0 to cancel:\n", MENU_HEADER_COLOR));
        int iterations = userFullNames.size();
        for (int i = 0; i < iterations; i++) {
            System.out.println(String.format(
                    "%s%d. %s %s", MENU_OPTIONS_COLOR, i + 1, userFullNames.get(i), coinsFromWallets.get(i)));
        }
    }

    public void printUserWallet(String fullName, String currentCoins, String allEarnings, ArrayList<String> ordersToPrint) {
        clearScreen();
        System.out.println(String.format(
                "%sThe wallet of %s contains %s%s %scoins.\nTotal earnings are equal to : %s%s.\n" +
                        "%sHistory of bought artifacts:\n",
                INFORMATION_COLOR, fullName, VALUE_COLOR, currentCoins, INFORMATION_COLOR, VALUE_COLOR,
                allEarnings, INFORMATION_COLOR
        ));
        if (!ordersToPrint.isEmpty()) {
            ListIterator<String> iterator = ordersToPrint.listIterator();
            for (; iterator.hasNext(); ) {
                String order = iterator.next();
                System.out.println(String.format("%s%d. %s", MENU_OPTIONS_COLOR, iterator.nextIndex(), order));
            }
        } else {
            System.out.println(String.format("%sThis codecooler didn't bought any artifacts.\n", ERROR_COLOR));
        }
        continuePrompt();
    }

    public void showMarkingNotPossible() {
        clearScreen();
        System.out.println(String.format("%sThere are no quests or codecoolers.\n", ERROR_COLOR));
        continuePrompt();
    }

    public void showUsers(ArrayList<String> usersFullNames) {
        clearScreen();
        System.out.println(String.format("%sSelect user to mark:\n", MENU_HEADER_COLOR));
        ListIterator<String> iterator = usersFullNames.listIterator();
        for (; iterator.hasNext(); ) {
            String fullName = iterator.next();
            System.out.println(String.format("%s%d. %s", MENU_OPTIONS_COLOR, iterator.nextIndex(), fullName));
        }
    }

    public void showCoinsAdded() {
        clearScreen();
        System.out.println(String.format("%sCoins for the completion of the quest added\n", INFORMATION_COLOR));
        continuePrompt();
    }

    public void showQuests(ArrayList<String> questsToPrint) {
        clearScreen();
        System.out.println(String.format("%sSelect quest to mark:\n", MENU_HEADER_COLOR));
        ListIterator<String> iterator = questsToPrint.listIterator();
        for (; iterator.hasNext(); ) {
            String quest = iterator.next();
            System.out.println(String.format("%s%d. %s", MENU_OPTIONS_COLOR, iterator.nextIndex(), quest));
        }
    }

    public void showArtifactsToMark(ArrayList<String> artifactsToPrint) {
        clearScreen();
        System.out.println(String.format("%sSelect artifact to activate:\n", MENU_HEADER_COLOR));
        ListIterator<String> iterator = artifactsToPrint.listIterator();
        for (; iterator.hasNext(); ) {
            String artifact = iterator.next();
            System.out.println(String.format("%s%d. %s", MENU_OPTIONS_COLOR, iterator.nextIndex(), artifact));
        }
    }

    public void showArtifactUsed() {
        clearScreen();
        System.out.println(String.format("%sThis artifact have been marked as used.\n", INFORMATION_COLOR));
        continuePrompt();
    }
}
