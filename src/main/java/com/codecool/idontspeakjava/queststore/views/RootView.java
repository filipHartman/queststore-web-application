package com.codecool.idontspeakjava.queststore.views;

import java.util.Scanner;

public class RootView extends UserView{

    private Scanner scanner;
    
    public String getUserInput(){
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    } 
}