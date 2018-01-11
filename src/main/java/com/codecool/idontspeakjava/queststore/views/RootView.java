package com.codecool.idontspeakjava.queststore.views;

import java.util.Scanner;

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
}