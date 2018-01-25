package com.codecool.idontspeakjava.queststore.views;

import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;
import com.codecool.idontspeakjava.queststore.models.Permissions;
import com.codecool.idontspeakjava.queststore.database.UserDAO;
import com.codecool.idontspeakjava.queststore.database.CodecoolClassDAO;

import java.awt.Color;
import java.lang.NullPointerException;

public class RootView extends UserView{

    // String kolor = new Colors().CYAN;

    public void askForClassName(){
        clearScreen();
        System.out.println(String.format("%sEnter class name or type 0 to exit.\n", Colors.CYAN));
    }
    
    public void askForMentorName(){
        clearScreen();
        System.out.println(String.format("%sEnter mentor first name or type 0 to exit.\n", Colors.BLUE_BOLD));
    }

    public void askForMentorEmail(){
        clearScreen();
        System.out.println(String.format("%sEnter mentor email or type 0 to exit.\n", Colors.GREEN));
    }

    public void askForMentorLastName(){
        clearScreen();
        System.out.println(String.format("%sEnter mentor last name or type 0 to exit.\n", Colors.PURPLE));
    }

    public void inputInfoNewMentorEmail(){
        System.out.println(String.format("%sEnter mentor new email.\n", Colors.YELLOW_BOLD));
    }

    public void showMentorInfo(User selectedMentor, CodecoolClass mentorClass){
        clearScreen();
        try{
            System.out.println(String.format("%sMentor id: " + selectedMentor.getId(), Colors.CYAN));
            System.out.println(String.format("%sMentor first name: " + selectedMentor.getFirstName(), Colors.CYAN));
            System.out.println(String.format("%sMentor last name: " + selectedMentor.getLastName(), Colors.CYAN));
            System.out.println(String.format("%sMentor email: " + selectedMentor.getEmail(), Colors.CYAN));
            System.out.println(String.format("%sMentor class: " + mentorClass.getName() + "\n", Color.CYAN));
        }catch(NullPointerException e){
            showMentorNotAssignToClass();
        }
        continuePrompt();
    }

    public void showMainMenu(){
        System.out.println(String.format("%sSelect what to do:\n" +
                    "1. Create mentor\n" +
                    "2. Create Codecool Class\n" +
                    "3. Assign Mentor To Class\n" +
                    "4. Edit Mentor\n" +
                    "5. Show Mentor\n" +
                    "6. Create Experience Level\n" +
                    "0. Exit the program\n", Colors.GREEN_BOLD));

    }

    public void showWrongInput(){
        System.out.println(String.format("%sChoose only available numbers.", Colors.BLUE));
    }

    public void showWrongNameInput() {
        clearScreen();
        System.out.println(String.format("%sWrong input. You can only use letters.\n", Colors.PURPLE_BOLD));
        continuePrompt();
    }

    public void showExistingValueWarning() {
        clearScreen();
        System.out.println(String.format("%sYou can't add position with this value. It is already in the database.\n", Colors.GREEN_BOLD));
        continuePrompt();
    }

    public void showWrongEmailInput() {
        clearScreen();
        System.out.println(String.format("%sWrong input. Invalid email address.\n", Colors.RED_BOLD));
        continuePrompt();
    }

    public void showDatabaseError() {
        clearScreen();
        System.out.println(String.format("%sAn error in the database occurred.\n", Colors.BLUE_BRIGHT));
        continuePrompt();
    }

    public void showMentorCreated() {
        clearScreen();
        System.out.println(String.format("%sYou created a mentor.\n", Colors.RED));
        continuePrompt();
    }

    public void showMentorCreationFailed() {
        clearScreen();
        System.out.println(String.format("%sAn error occured. The new mentor wasn't created.\n", Colors.YELLOW_BOLD));
        continuePrompt();
    }

    public void showOperationCancelled() {
        clearScreen();
        System.out.println(String.format("%sOperation cancelled.\n", Colors.CYAN));
        continuePrompt();
    }

    public void showWrongClassNameInput() {
        clearScreen();
        System.out.println(String.format("%sWrong input. You can only use letters, numbers, commas and dots.\n", Colors.BLUE));
        continuePrompt();
    }

    public void showClassCreateComplete(){
        clearScreen();
        System.out.println(String.format("%sYou created class.\n", Colors.GREEN));
        continuePrompt();
    }

    public void askForMentorEmailInput() {
        System.out.println(String.format("%sEnter selected mentor email or type 0 to exit.\n", Colors.YELLOW));
    }

    public void askForClassNameInput(){
        System.out.println(String.format("%sEnter class name or type 0 to exit.\n", Colors.CYAN_BOLD));
    }

    public void showWrongClassName(){
        clearScreen();
        System.out.println(String.format("%sWrong input. Invalid class name.\n", Colors.PURPLE_BOLD));
        continuePrompt();
    }

    public void showMentorAssign(){
        clearScreen();
        System.out.println(String.format("%sYou assign mentor to a class.\n", Colors.CYAN));
        continuePrompt();
    }

    public void showAllMentors(){
        clearScreen();
        System.out.println(String.format("%sName    Last name   email\n", Colors.BLUE_BOLD));
        System.out.println(String.format("%s--------------------------\n", Colors.CYAN_BOLD));
        for (User mentorUser : new UserDAO().getUsersByPermission(Permissions.Mentor)) {
            System.out.println(String.format("%s" + mentorUser.getFirstName() + "\t" + mentorUser.getLastName() + "\t" + mentorUser.getEmail()+"\n", Colors.BLUE_BOLD));
        }
    }

    public void showAllClasses(){
        clearScreen();
        for (CodecoolClass codecoolClass : new CodecoolClassDAO().getAllCodecoolClasses()) {
            System.out.println(String.format("%s" + codecoolClass.getName() + "\n", Colors.GREEN_BOLD));
        }
    }
    public void askForMentorEmailToEdit(){
        clearScreen();
        System.out.println(String.format("%sDo you want to change mentor email[Y/N]? or type 0 to exit\n", Colors.YELLOW_BOLD));
    }

    public void askForMentorNewEmail(){
        clearScreen();
        System.out.println(String.format("%sEnter mentor new email.\n", Colors.RED_BOLD));
    }

    public void showWrongAnswer(){
        clearScreen();
        System.out.println(String.format("%sPlease answer only Y or N.\n", Colors.PURPLE));
        continuePrompt();
    }

    public void askForMentorClassToEdit(){
        clearScreen();
        System.out.println(String.format("%sDo you want to change mentor class[Y/N]? or type 0 to exit\n", Colors.GREEN_BOLD));
    }

    public void askForMentorNewClass(){
        System.out.println(String.format("%sEnter mentor new class name.\n", Colors.YELLOW));
    }

    public void showMentorInClassInfo(){
        clearScreen();
        System.out.println(String.format("%sSelected mentor is already in this class.\n", Colors.BLUE));
    }

    public void showMentorUpdate(){
        clearScreen();
        System.out.println(String.format("%sYou updated mentor data.\n", Colors.RED));
    }

    public void showWrongExpLvlInput(){
        clearScreen();
        System.out.println(String.format("%sWrong input. You can use letters, numbers, coomas and dots.\n", Colors.PURPLE));
        continuePrompt();
    }

    public void askForExpLvlName(){
        clearScreen();
        System.out.println(String.format("%sEnter experience level name or type 0 to exit .\n", Colors.CYAN));
    }

    public void askForExpLvlThreshold(){
        clearScreen();
        System.out.println(String.format("%sEnter experience level threshold or type 0 to exit.\n", Colors.GREEN));
    }

    public void WrongThresholdInput(){
        clearScreen();
        System.out.println(String.format("%sWrong input. You can use only numbers.\n", Colors.RED));
        continuePrompt();
    }

    public void showExpLvlCreated(){
        clearScreen();
        System.out.println(String.format("%sYou created new experience level.\n", Colors.PURPLE));
    }

    public void showClassNotExist(){
        clearScreen();
        System.out.println(String.format("%sWrong class name. This class doesn't exist.\n", Colors.BLUE));
        continuePrompt();
    }

    public void showMentorNotAssignToClass(){
        System.out.println(String.format("%sThis mentor isn't assign to any class.\n", Colors.GREEN));
    }
}