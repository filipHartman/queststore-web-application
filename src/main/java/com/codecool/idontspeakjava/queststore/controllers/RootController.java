package com.codecool.idontspeakjava.queststore.controllers;

import com.codecool.idontspeakjava.queststore.views.RootView;
import com.codecool.idontspeakjava.queststore.models.User;

public class RootController{
    private RootView view;
    private User user;

    public RootController(User user){
        this.view = new RootView();
        this.user = user;
    }
}