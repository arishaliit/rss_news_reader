package com.finalproject;

//Importing required classes
import com.finalproject.controller.SplashController;
import javafx.animation.*;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

/**
 * @author arish
 * @version 05/08/2022
 *
 * MainApplication class extends Application class to implement JavaFX GUI
 * this class contains title, description, url link, image URL and publish date of news
 * */
public class MainApplication extends Application {

    //Logger instance to log activities
    private static Logger log = LogManager.getLogger(MainApplication.class);

    //Overriding start() method of Application class to implement
    //JavaFX GUI
    @Override
    public void start(Stage stage) throws IOException {

        //logging message
        log.info("Starting Splash screen animation");

        //Splash class to create splash screen
        Splash splash = new Splash();
        splash.show();//displaying splash
        //setting scene
        stage.setScene(splash.getScene());

        //on completing splash progress
        splash.getTask().setOnSucceeded(e -> {

            // timeline instance
            Timeline timeline = new Timeline();

            //keyframe to show fading animation
            KeyFrame key = new KeyFrame(Duration.millis(800),
                    new KeyValue(splash.getScene().getRoot().opacityProperty(), 0));
            timeline.getKeyFrames().add(key);

            // on finishing timeline
            timeline.setOnFinished((event) -> {
                try {

                    //logging message
                    log.info("displaying news feed items list");

                    //reading rss_news_feed.fxml file to load fxml
                    Parent root = FXMLLoader.load(MainApplication.class.getResource("view/rss_news_feed.fxml"));
                    Scene scene = new Scene(root, 1200, 650);//setting scene and size of window

                    //getting screen size to display window at the center.
                    Rectangle2D ScreenBounds = Screen.getPrimary().getVisualBounds();
                    double x = (ScreenBounds.getWidth()/2) - 600;
                    double y = (ScreenBounds.getHeight()/2) - 325;

                    //setting x and y position
                    stage.setX(x);
                    stage.setY(y);

                    //setting scene
                    stage.setScene(scene);
                }
                catch (IOException ex) {
                    //logging error message
                    log.error(ex.getMessage());
                }
            });
            //showing  animation
            timeline.play();
        });

        //displaying stage
        stage.show();
    }

    //main method
    public static void main(String[] args) {
        launch();// launching GUI
    }

    //Reference from stackoverflow
    //https://stackoverflow.com/questions/52382778/how-do-i-add-a-splash-screen-to-a-main-program-in-javafx
    //Inner class Splash
    public class Splash{

        // Scene instance
        static Scene scene;

        //pane
        private Pane pane;
        private ProgressBar progressBar;
        private Task task;

        //splash screen constructor
        public Splash(){
            try {

                //reading FXML from "splash.fxml" file
                FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/splash.fxml"));
                fxmlLoader.load();//loading FXML
                SplashController controller = fxmlLoader.getController();//getting controller of splash.fxml
                pane = controller.getPane();//getting pane
                progressBar = controller.getProgressBar();//getting progress bar
                scene = new Scene(pane, 565, 450);// instantiating scene

            }catch (Exception e){
                //logging error message
                log.error(e.getMessage());
            }
        }

        //show method to display animation of splash window
        public void show(){

            task = new Task<Void>() {
                @Override public Void call() {
                    final int max = 100000000;
                    for (int i=1; i<=max; i++) {
                        if (isCancelled()) break;

                        updateProgress(i, max);
                    }
                    return null;
                }
            };
            // binding thread progress property to progressbar
            // to synchronize progress bar with the thread execution.
            progressBar.progressProperty().bind(task.progressProperty());
            new Thread(task).start();// start progressbar timer
        }

        //getting scene
        public Scene getScene(){
            return scene;
        }
        //getting task instance
        public Task getTask(){
            return task;
        }
    }
}