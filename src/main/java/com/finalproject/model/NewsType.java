package com.finalproject.model;

/**
 * @author arish
 * @version 05/08/2022
 *
 * NewsType is an enum type that stores news type and
 * their RSS Urls.
 * */
public enum NewsType {

    //NewsTypes
    TOP_STORIES("Top Stories", "http://rss.cnn.com/rss/cnn_topstories.rss"),
    ALL_POLITICS("All Politics", "http://rss.cnn.com/rss/cnn_allpolitics.rss"),
    BUSINESS("Business", "http://rss.cnn.com/rss/edition_business.rss"),
    HEALTH("Health", "http://rss.cnn.com/rss/cnn_health.rss"),
    TECH("Tech", "http://rss.cnn.com/rss/cnn_tech.rss"),
    SHOWBIZ("Showbiz", "http://rss.cnn.com/rss/cnn_showbiz.rss");

    //variables
    private String title, url;
    //constrcutor
    NewsType(String title, String url) {
        this.title = title;
        this.url = url;
    }

    public String getTitle(){return title;}
    public String getUrl(){return url;}

}
