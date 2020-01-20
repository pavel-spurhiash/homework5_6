package com.gmail.pashasimonpure;

import com.gmail.pashasimonpure.controller.HomeWorkController;
import com.gmail.pashasimonpure.controller.impl.HomeWorkControllerImpl;

public class App {

    public static void main(String[] args) {
        HomeWorkController homeWorkController = HomeWorkControllerImpl.getInstance();
        homeWorkController.runTask();
    }

}