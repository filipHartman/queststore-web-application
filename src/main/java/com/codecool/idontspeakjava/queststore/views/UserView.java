package com.codecool.idontspeakjava.queststore.views;

import java.util.Scanner;

public abstract class UserView{

    private Scanner scanner;

    public String getUserInput(){
        scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    private void showUser(String user){

    }
}