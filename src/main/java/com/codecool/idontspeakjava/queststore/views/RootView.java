package com.codecool.idontspeakjava.queststore.views;

import java.util.Scanner;

public class RootView extends UserView{

    private Scanner scanner;

    public String getUserInput(){
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
    
    public void showCreateCodecoolClassMenu(String whichOne){
        if(whichOne == "firstName")
            System.out.println("Enter mentor first name");
        else if(whichOne == "lastName")
            System.out.println("Enter mentor last name");
        else if(whichOne == "password")
            System.out.println("Enter mentor passwordHash");
        else
            System.out.println("Enter mentor email");
    }
}