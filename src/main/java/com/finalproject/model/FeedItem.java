package com.finalproject.model;

//Importing required classes
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author arish
 * @version 05/08/2022
 *
 * FeedItem class is a model class for news feed item
 * this class contains title, description, url link, image URL and publish date of news
 * */
public class FeedItem {

    //Logger instance to log activities
    private static Logger log = LogManager.getLogger(FeedItem.class);

    //instance variables for news feed item
    private String title;
    private String description;
    private String link;
    private String imageURL;
    private String publishDate;

    //parameterized constructor
    public FeedItem(String title, String description, String link, String imageURL, String publishDate) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.imageURL = imageURL;
        this.publishDate = publishDate;

        //logging creating of new News feed Item
        log.info("creating news feed item : "+toString());
    }

    //getters and setters
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    // overriding to string method
    @Override
    public String toString() {
        return "FeedItem{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", link='" + link + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", publishDate='" + publishDate + '\'' +
                '}';
    }
}
