package com.finalproject.controller;
//Importing required classes
import com.finalproject.MainApplication;
import com.finalproject.model.FeedItem;
import javafx.application.Platform;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
/**
 * @author arish
 * @version 05/08/2022
 *
 * FeedItemController class handles events of feed_item.fxml
 * this class implements Initializable interface and its method initialize.
 * this class has a method setNewsFeed() which takes NewsFeed instance as an
 * input parameter, and set news item details in the fields.
 *
 * */
public class FeedItemController implements Initializable {

    //Logger instance to log activities
    private static Logger log = LogManager.getLogger(FeedItem.class);

    //creating instance of Hyperlink urlText, with the same name as FXML id
    @FXML
    private Hyperlink urlText;

    //creating instance of Label titleText, with the same name as FXML id
    @FXML
    private Label titleText;

    //creating instance of Label titleText, with the same name as FXML id
    @FXML
    private Label dateText;

    //creating instance of TextArea descText, with the same name as FXML id
    @FXML
    private TextArea descText;

    //creating instance of ImageView imageView, with the same name as FXML id
    @FXML
    private ImageView imageView;

    //creating instances of VBox vBox, vBox2, with the same name as FXML id
    @FXML
    private VBox vBox, vBox2;

    //Overriding initialize() method to set width of the pane relative to the window
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        vBox.setFillWidth(true);
        vBox2.setFillWidth(true);
    }

    /**
     * @param feedItem
     * method name:setNewsFeed()
     * this method takes FeedItem instance as an input and set news details
     * in the textFields and Image View.
     * */
    public void setNewsFeed(FeedItem feedItem) {

        log.info("Setting FeedItem data (title, image, url, etc...) to the FeedItem Pane");

        //setting feed URL
        urlText.setText(
                feedItem.getLink()
        );

        //setting feed Description
        descText.setText(
                feedItem.getDescription()
        );

        //setting feed Title
        titleText.setText(
                feedItem.getTitle()
        );

        //setting publish date
        dateText.setText(
                feedItem.getPublishDate()
        );
        //condition to check if image url is null or not
        if(!feedItem.getImageURL().isEmpty()) {
            //service instance to load image of news feed as a thread
            new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {
                            final CountDownLatch latch = new CountDownLatch(1);
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        //retrieving image from url
                                        Image image = new Image(feedItem.getImageURL());
                                        imageView.setImage(image);//setting image in the image view
                                    } catch (Exception e) {
                                        //logging error message
                                        log.error(e.getMessage());
                                    } finally {
                                        latch.countDown();
                                    }
                                }
                            });
                            latch.await();
                            return null;
                        }
                    };
                }
            }.start();
        }
    }

    //method returns vBox
    public VBox getVBox() {
        return this.vBox;
    }
    /**
     * @param actionEvent
     * method name: openUrl()
     * This method is onclick eventhandler of "urlText" hyperlink.
     * on click, this method takes url from "urlText" and opens in the browser.
     * */
    public void openUrl(ActionEvent actionEvent) {
        log.info("'"+urlText.getText()+"' clicked!");
        try {
            //reading FXML from "browser.fxml" file
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/browser.fxml"));
            Parent root = (Parent) fxmlLoader.load();//loading FXML
            BrowserController controller = fxmlLoader.getController();//getting controller of browser.fxml
            controller.openUrl(urlText.getText());//passing url to the controller
            Stage stage = new Stage();//creating new Stage object
            stage.setTitle("Browser");//setting title of stage as Browser
            // creating a scene object, passing loaded fxml to the scene and finally
            // setting scene inside the stage using setScene() method.
            stage.setScene(new Scene(root));
            //displaying the stage.
            stage.show();
        } catch (IOException e) {
            //logging error message
            log.error(e.getMessage());
        }

    }
}
