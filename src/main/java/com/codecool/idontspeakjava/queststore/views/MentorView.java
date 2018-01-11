package com.codecool.idontspeakjava.queststore.views;


import java.util.ArrayList;
import java.util.ListIterator;

public class MentorView extends UserView {

    public void showMainMenu(String userName) {
        System.out.println(String.format(
                "Hello %s ! Select what you want to do:\n" +
                        "1 - Create new codecooler\n" +
                        "2 - Add new quest\n" +
                        "3 - Add new artifact\n" +
                        "4 - Edit existing quest\n" +
                        "5 - Edit existing artifact\n" +
                        "6 - Mark quest for a codecooler\n" +
                        "7 - Mark artifact for a codecooler\n" +
                        "8 - Check the wallets of codecoolers\n" +
                        "0 - Exit the program\n", userName));
    }

    public void showWrongInput() {
        System.out.println("Your input is wrong.\n");
    }

    public void showCodecoolerCreated() {
        System.out.println("You created a new codecooler.\n");
    }

    public void showCodecoolerCreationFailed() {
        System.out.println("An error occured. The new codecooler wasn't created.\n");
    }

    public void showOperationCancelled() {
        System.out.println("Operation cancelled.\n");
    }

    public void askForCodecoolerName() {
        System.out.println("Enter the name of codecooler's or 0 to cancel.\n");
    }

    public void askForSecondName() {
        System.out.println("Enter the second name of codecooler's or 0 to cancel.\n");
    }

    public void askForEmail() {
        System.out.println("Enter the codecooler's email or 0 to cancel.\n");
    }

    public void askForQuestTitle() {
        System.out.println("Enter the quest title or 0 to cancel.\n");
    }

    public void askForQuestDescription() {
        System.out.println("Enter the description of the quest or 0 to cancel.\n");
    }

    public void askForQuestReward() {
        System.out.println("Enter the reward for the quest or 0 to cancel.\n");
    }

    public void askForQuestCategory() {
        System.out.println("Select quest category:\n" +
                "1 - Basic\n" +
                "2 - Extra\n" +
                "0 - Cancel\n");
    }

    public void showWrongTitleInput() {
        System.out.println("Wrong input. You can use only letters, digits and whitespaces.\n");
    }

    public void showWrongDescriptionInput() {
        System.out.println("Wrong input. You can use only letters, digits, whitespaces, commas, dots and exclamation marks\n");
    }

    public void showWrongDigitInput() {
        System.out.println("Wrong input. You can use only digits.\n");
    }

    public void showQuestCreated() {
        System.out.println("The quest have been successfully created.\n");
    }

    public void showWrongNameInput() {
        System.out.println("Wrong input. You can only use letters.\n");
    }

    public void showWrongEmailInput() {
        System.out.println("Wrong input. This is not a valid email address.\n");
    }

    public void askForCodecoolClass(ArrayList<String> classesTitles) {
        ListIterator<String> iterator = classesTitles.listIterator();
        System.out.println("Select to which class you want to add student, or 0 to cancel.\n");
        for (; iterator.hasNext(); ) {
            String title = iterator.next();
            System.out.println(String.format("%d. %s", iterator.nextIndex(), title));
        }
    }

    public void showClassesDontExist() {
        System.out.println("There aren't any classes. The student can't be created.\n");
    }

    public void showInputMustBeHigherThanZero() {
        System.out.println("Wrong input. The value must be higher than zero.\n");
    }

    public void askForArtifactTitle() {
        System.out.println("Enter the name of new artifact or 0 to cancel.\n");
    }

    public void askForArtifactCategory() {
        System.out.println("Select artifact category:\n" +
                "1 - Basic\n" +
                "2 - Magic\n" +
                "0 - Cancel\n");
    }

    public void showArtifactCreated() {
        System.out.println("New artifact have been created.\n");
    }

    public void askForArtifactDescription() {
        System.out.println("Enter the description of your artifact or 0 to cancel.\n");
    }

    public void askForArtifactPrice() {
        System.out.println("Enter the price of your artifact or 0 to cancel.\n");
    }

    public void showDuplicateWarning() {
        System.out.println("You can't add position with this value. It is already in the database.\n");
    }

    public void showDatabaseError() {
        System.out.println("An error in the database occurred.\n");
    }

    public void showNoQuests() {
        System.out.println("There are no quests to edit.\n");
    }

    public void selectQuest(ArrayList<String> questTitles) {
        ListIterator<String> iterator = questTitles.listIterator();

        System.out.println("Select quest to edit or 0 to cancel.\n");
        for (; iterator.hasNext(); ) {
            String title = iterator.next();
            System.out.println(String.format("%d. %s", iterator.nextIndex(), title));
        }
    }

    public void selectAttributeOfQuestToEdit() {
        System.out.println("Select what you want to edit, or 0 to cancel.\n" +
                "1 - Title\n" +
                "2 - Description\n" +
                "3 - Category\n" +
                "4 - Reward\n");
    }

    public void showTitle(String title) {
        System.out.println(String.format("The current title: %s\n", title));
    }

    public void showCategory(String category) {
        System.out.println(String.format("The current category : %s\n", category));
    }

    public void showDescription(String description) {
        System.out.println(String.format("The current description: %s\n", description));
    }

    public void showReward(String reward) {
        System.out.println(String.format("The current reward is: %s\n", reward));
    }

    public void showPrice(String price) {
        System.out.println(String.format("The current price is: %s\n", price));
    }

    public void showNoArtifacts() {
        System.out.println("There aren't any artifacts.\n");
    }

    public void selectArtifacts(ArrayList<String> artifactsTitles) {
        ListIterator<String> iterator = artifactsTitles.listIterator();

        System.out.println("Select artifact to edit or 0 to cancel.\n");
        for (; iterator.hasNext(); ) {
            String title = iterator.next();
            System.out.println(String.format("%d. %s", iterator.nextIndex(), title));
        }
    }

    public void selectAttributeOfArtifactToEdit() {
        System.out.println("Select what you want to edit, or 0 to cancel.\n" +
                "1 - Title\n" +
                "2 - Description\n" +
                "3 - Category\n" +
                "4 - Price\n");
    }

    public void showNoUsers() {
        System.out.println("There are no codecoolers\n");
    }

    public void showUsersAndWallets(ArrayList<String> userFullNames, ArrayList<String> coinsFromWallets) {
        System.out.println("Select which user details you want to show or 0 to cancel.\n");
        int iterations = userFullNames.size();
        for (int i = 0; i < iterations; i++) {
            System.out.println(String.format("%d. %s %s", i + 1, userFullNames.get(i), coinsFromWallets.get(i)));
        }
    }

    public void printUserWallet(String fullName, String currentCoins, String allEarnings, ArrayList<String> ordersToPrint) {
        System.out.println(String.format("The wallet of %s contains %s coins.\nTotal earnings are equal to : %s.\n" +
                "History of bought artifacts:\n", fullName, currentCoins, allEarnings));
        if (!ordersToPrint.isEmpty()) {
            ListIterator<String> iterator = ordersToPrint.listIterator();
            for (; iterator.hasNext(); ) {
                String order = iterator.next();
                System.out.println(String.format("%d. %s\n", iterator.nextIndex(), order));
            }
        } else {
            System.out.println("This codecooler didn't bought any artifacts.\n");
        }
    }
}
