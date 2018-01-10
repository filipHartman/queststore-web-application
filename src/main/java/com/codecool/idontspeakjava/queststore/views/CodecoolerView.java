package com.codecool.idontspeakjava.queststore.views;

import java.util.ArrayList;

public class CodecoolerView extends UserView{

    public void showWallet(int totalMoney, ArrayList<String> namesOfArtifacts){
        System.out.println("WALLET\n");
        System.out.println("Your coolcoins: " + totalMoney);
        System.out.println("Your artifacts: ")
        for (String artifact : artifacts) {
            System.out.println("- " + artifact);
        }
    }

    public void showBuyArtifactMenu(){

    }

    public void showBuyArtifactForTeamMenu(){

    }

    public void showMyLevel(){

    }
}