package com.finalproject.controller;

//Importing required classes
import com.finalproject.MainApplication;
import com.finalproject.model.FeedItem;
import com.finalproject.model.NewsType;
import com.finalproject.model.RssFeedReader;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * @author arish
 * @version 05/08/2022
 *
 * NewsController class handles events of rss_news_feed.fxml
 * this class implements Initializable interface and its method initialize.
 * this class has a method listNews() which takes String url as an
 * input parameter, and set news feed item details in the listview.
 *
 * */
public class NewsController implements Initializable {

    //Logger instance to log activities
    private static Logger log = LogManager.getLogger(NewsController.class);

    //creating instance of ListView listView, with the same name as FXML id
    @FXML
    private ListView listView;

    //creating instance of ScrollPane scrollPane, with the same name as FXML id
    @FXML
    private ScrollPane scrollPane;

    //creating instance of ComboBox newsTypeCombo, with the same name as FXML id
    @FXML
    private ComboBox newsTypeCombo;

    //creating instance of ProgressBar progressBar, with the same name as FXML id
    @FXML
    private ProgressBar progressBar;

    //Overriding initialize() method to set new feed before display window
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        log.info("opening news Feed Window");
        //creating arraylist
        ArrayList<String> list = new ArrayList<>();
        //getting newsType title from enum NewsType and adding to above
        // declared array list
        for(NewsType newsType:NewsType.values()){
            list.add(newsType.getTitle());
        }
        //set arraylist as a combobox newTypeCombo items
        newsTypeCombo.setItems(FXCollections.observableArrayList(list));
        //set first item of combobox selected
        newsTypeCombo.getSelectionModel().select(0);

        //set scrollpane expand relative to the window
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);

        //make non-transversable focus of listview
        listView.setFocusTraversable(false);

        //show "Top Stories" on start
        listNews(NewsType.TOP_STORIES.getUrl());

    }

    /**
     *
     * @param url
     * method name: listNews()
     * This method takes String url as an input, retrieve news data from RSS api
     * and display it in the listView
     * */
    public void listNews(String url){

        //retrieving news feed from the url
        ArrayList<FeedItem> rss = RssFeedReader.readRSSFeed(url);

        //making listview empty
        listView.getItems().clear();

        //read each news feed item using for each loop
        for(FeedItem feedItem: rss){

            //getting FXML from feed_item.fxml
            FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("view/feed_item.fxml"));
            try {
                //loading fxml
                fxmlLoader.load();
                //getting controller of feed_item.fxml
                FeedItemController controller = fxmlLoader.getController();
                //setting news feed item to the controller
                controller.setNewsFeed(feedItem);

                //getting Vertical Box pane from controller
                VBox pane = controller.getVBox();

                //adding above pane to the listView items of current window
                listView.getItems().add(pane);

            } catch (Exception e) {
                //logging error message
                log.error(e.getMessage());
            }
        }
    }

    /**
     * @param actionEvent
     * method name: newsTypeComboBoxChange()
     * This method halndles onclick method of newsTypecombo ComboBox
     * on item chnage, this method displays the news related to selected
     * item.
     * */
    public void newsTypeComboBoxChange(ActionEvent actionEvent) {

        //task thread for progress bar implementing using lambda function
        Task task = new Task<Void>() {
            @Override public Void call() {
                final int max = 100000000;// wait time for progress bar
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

        //get selected item from newTypeCombo ComboBox
        String selectedItem = (String) newsTypeCombo.getSelectionModel().getSelectedItem();

        //logging item change in combobox
        log.info("Item changed in combobox now selected item is :"+selectedItem);

        //finding url of the selected item and displaying news related to it.
        for(NewsType newsType:NewsType.values()){
            if(selectedItem.equals(newsType.getTitle())){
                listNews(newsType.getUrl());//calling listNews method to update newsFeed list
                break;
            }
        }

    }
}