package com.codecool.idontspeakjava.queststore.views;

import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;

public class RootView extends UserView{

    public void askForClassName(){
        clearScreen();
        System.out.println("Enter class name or type 0 to exit.\n");
    }
    
    public void askForMentorName(){
        clearScreen();
        System.out.println("Enter mentor first name or type 0 to exit.\n");
    }

    public void askForMentorEmail(){
        clearScreen();
        System.out.println("Enter mentor email or type 0 to exit.\n");
    }

    public void askForMentorLastName(){
        clearScreen();
        System.out.println("Enter mentor last name or type 0 to exit.\n");
    }

    public void inputInfoNewMentorEmail(){
        System.out.println("Enter mentor new email");
    }

    public void showMentorInfo(User selectedMentor, CodecoolClass mentorClass){
        System.out.println("Mentor id " + selectedMentor.getId());
        System.out.println("Mentor first name " + selectedMentor.getFirstName());
        System.out.println("Mentor last name " + selectedMentor.getLastName());
        System.out.println("Mentor email " + selectedMentor.getEmail());
        System.out.println("Mentor class " + mentorClass.getName());
    }

    public void showMainMenu(){
        System.out.println("Select what to do:\n" +
                    "1. Create mentor\n" +
                    "2. Create Codecool Class\n" +
                    "3. Assign Mentor To Class\n" +
                    "4. Edit Mentor\n" +
                    "5. Show Mentor\n" +
                    "6. Show Codecool Class Of Mentor\n" +
                    "7. Create Experience Level\n" +
                    "0. Exit the program\n");

    }

    public void showWrongInput(){
        System.out.println("Choose only available numbers.");
    }

    public void showWrongNameInput() {
        clearScreen();
        System.out.println("Wrong input. You can only use letters.\n");
        continuePrompt();
    }

    public void showExistingValueWarning() {
        clearScreen();
        System.out.println("You can't add position with this value. It is already in the database.\n");
        continuePrompt();
    }

    public void showWrongEmailInput() {
        clearScreen();
        System.out.println("Wrong input. Invalid email address.\n");
        continuePrompt();
    }

    public void showDatabaseError() {
        clearScreen();
        System.out.println("An error in the database occurred.\n");
        continuePrompt();
    }

    public void showMentorCreated() {
        clearScreen();
        System.out.println("You created a mentor.\n");
        continuePrompt();
    }

    public void showMentorCreationFailed() {
        clearScreen();
        System.out.println("An error occured. The new mentor wasn't created.\n");
        continuePrompt();
    }

    public void showOperationCancelled() {
        clearScreen();
        System.out.println("Operation cancelled.\n");
        continuePrompt();
    }

    public void showWrongClassNameInput() {
        clearScreen();
        System.out.println("Wrong input. You can only use letters, numbers, commas and dots.\n");
        continuePrompt();
    }

    public void askForMentorEmailInput() {
        System.out.println("Enter select mentor email or type 0 to exit.\n");
    }

    public void askForClassNameInput(){
        System.out.println("Enter class name or type 0 to exit.\n");
    }

    public void showWrongClassName(){
        clearScreen();
        System.out.println("Wrong input. Invalid class name.\n");
        continuePrompt();
    }

    public void showMentorAssign(){
        clearScreen();
        System.out.println("You assign mentor to a class.\n");
        continuePrompt();
    }

    public void showAllMentors(){
        clearScreen();
        for (User mentorUser : new UserDAO().getUsersByPermission(Permissions.Mentor)) {
            System.out.println(mentorUser.getFirstName() + " " + mentorUser.getLastName() + " " + mentorUser.getEmail()+"\n");
        }
    }

    public void showAllClasses(){
        clearScreen();
        for (CodecoolClass codecoolClass : new CodecoolClassDAO().getAllCodecoolClasses()) {
            System.out.println(codecoolClass.getName());
        }
    }
    public void askForMentorEmailToEdit(){
        clearScreen();
        System.out.println("Do you want to change mentor email[Y/N]? or type 0 to exit\n");
    }

    public void askForMentorNewEmail(){
        clearScreen();
        System.out.println("Enter mentor new email.\n");
    }

    public void showWrongAnswer(){
        clearScreen();
        System.out.println("Please answer only Y or N");
    }

    public void askForMentorClassToEdit(){
        clearScreen();
        System.out.println("Do you want to change mentor class[Y/N]? or type 0 to exit\n");
    }

    public void askForMentorNewClass(){
        System.out.println("Enter mentor new class name.\n");
    }

    public void showMentorInClassInfo(){
        clearScreen();
        System.out.println("Selected mentor is already in this class.\n");
    }

    public void showMentorUpdate(){
        clearScreen();
        System.out.println("You update mentor data.\n");
    }
}