package com.finalproject.controller;
//Importing required classes
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author arish
 * @version 05/08/2022
 *
 * BrowserController class handles events of browser.fxml
 * this class has a method openUrl() which takes url as an
 * input parameter, set it in the urlBarText Textfield and open it
 * in the webview.
 *
 * */
public class BrowserController {

    //Logger instance to log activities
    private static Logger log = LogManager.getLogger(BrowserController.class);

    //creating instance of webview, with the same name as FXML id
    @FXML
    private WebView webView;

    //creating instance of urlBarText, with the same name as FXML id
    @FXML
    private TextField urlBarText;

    /**
     * @param url
     * This method takes url as an input parameter, set it in the urlBarText Textfield
     * and open it in the webview.
     *
     * */
    public void openUrl(String url){

        //setting url in the textfield
        urlBarText.setText(url);

        log.info("Opening url: \""+url+"\" in the browser.");

        //getting web engine from webview
        WebEngine webEngine = webView.getEngine();
        webEngine.load(url);//loading webpage
    }
}
