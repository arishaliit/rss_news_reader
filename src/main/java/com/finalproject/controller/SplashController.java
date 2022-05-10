package com.finalproject.controller;

//Importing required classes
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

/**
 * @author arish
 * @version 05/08/2022
 *
 * SplashController class handles events of splash.fxml
 * the purpose of this class is to access progress bar to show animation
 * on application start.
 * */
public class SplashController {

    //creating instance of Pane pane, with the same name as FXML id
    @FXML
    private Pane pane;

    //creating instance of ProgressBar progressBar, with the same name as FXML id
    @FXML
    private ProgressBar progressBar;

    //methods return pane
    public Pane getPane(){return pane;}

    //methods return progressBar
    public ProgressBar getProgressBar(){return progressBar;}
}
