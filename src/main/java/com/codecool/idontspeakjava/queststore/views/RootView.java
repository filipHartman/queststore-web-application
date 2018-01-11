package com.codecool.idontspeakjava.queststore.views;

import java.util.Scanner;
import com.codecool.idontspeakjava.queststore.models.User;
import com.codecool.idontspeakjava.queststore.models.CodecoolClass;

public class RootView extends UserView{

    private Scanner scanner;

    public String getUserInput(){
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void inputInfoClassName(){
        System.out.println("Enter class name");
    }
    
    public void inputInfoMentorName(){
        System.out.println("Enter mentor first name");
    }

    public void inputInfoMentorEmail(){
        System.out.println("Enter mentor email");
    }

    public void inputInfoMentorLastName(){
        System.out.println("Enter mentor last name");
    }

    public void inputInfoMentorPassword(){
        System.out.println("Enter mentor password");
    }

    public void inputInfoNewMentorEmail(){
        System.out.println("Enter mentor new email");
    }

    public boolean editMentorOptionAsk(String option){
        System.out.println("Do you want to change mentor " + option + " [Y/N]");
        String decision = getUserInput().toUpperCase();
        if(decision == "Y")
            return true;
        return false;
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
}