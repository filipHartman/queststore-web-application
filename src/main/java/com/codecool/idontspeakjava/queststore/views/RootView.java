package com.codecool.idontspeakjava.queststore.views;

import java.util.Scanner;
import java.util.Spliterator.OfPrimitive;

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
}