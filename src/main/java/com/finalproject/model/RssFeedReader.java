package com.finalproject.model;

//Importing required classes
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author arish
 * @version 05/08/2022
 *
 * RssFeedReader class is a model class tp get news details from RSS api
 * */
public class RssFeedReader {

    //Logger instance to log activities
    private static Logger log = LogManager.getLogger(RssFeedReader.class);

    /**
     * @param urlAddress
     * @return ArrayList<FeedItem>
     * method name: readRSSFeed()
     *
     * this method takes urlAddress as a parameter, retrieve newsfeed xml from
     * RSS api and returns Array List of FeedItems
     *
     * */
    public static ArrayList<FeedItem> readRSSFeed(String urlAddress){

        //ArrayList to store and return Feed Item
        ArrayList<FeedItem> feedItems = new ArrayList<>();

        try{
            //creating URL object and passing url as a parameter
            URL rssUrl = new URL (urlAddress);

            //opening url connection to read rss feed
            HttpURLConnection con = (HttpURLConnection) rssUrl.openConnection();

            // creating buffered reader to read xml data line by line.
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));

            String inputLine;
            StringBuffer response = new StringBuffer();

            //reading xml data line by line
            while ((inputLine = br.readLine()) != null) {
                response.append(inputLine);//appending lines to string buffer
            }
            br.close();//closing buffered reader

            //creating document object using DocumentBuilderFactory class and parse xml
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
                    .parse(new InputSource(new StringReader(response.toString())));

            //reading item tags from xml document
            NodeList nodeList = doc.getElementsByTagName("item");

            // loop to iterate over item node list
            for(int i = 0; i< nodeList.getLength(); i++) {

                //reading Node Element and creating feed item
                Element element = (Element) nodeList.item(i);

                Node titleNode = element.getElementsByTagName("title").item(0);
                Node descNode = element.getElementsByTagName("description").item(0);
                Node linkNode = element.getElementsByTagName("link").item(0);
                Node pubDateNode = element.getElementsByTagName("pubDate").item(0);
                Node imageURLNode = element.getElementsByTagName("media:content").item(0);

                String title = "", desc = "", imageURL = "", link = "", pubDate = "";

                //condition to check if titleNode isn't null
                if(titleNode != null)
                    title = titleNode.getTextContent();

                //condition to check if descNode isn't null
                if(descNode != null)
                    desc = descNode.getTextContent().replaceAll("\\<.*?\\>", "");

                //condition to check if linkNode isn't null
                if(linkNode != null)
                    link = linkNode.getTextContent();

                //condition to check if pubDateNode isn't null
                if(pubDateNode != null)
                    pubDate = pubDateNode.getTextContent();

                //condition to check if imageURLNode isn't null
                if(imageURLNode != null)
                    imageURL = imageURLNode.getAttributes().getNamedItem("url").getTextContent();

                //creating FeedItem
                FeedItem item = new FeedItem(title, desc, link, imageURL, pubDate);

                //adding above created item in the arraylist
                feedItems.add(item);
            }
        } catch (Exception e) {
            //logging error message
            log.error(e.getMessage());
        }
        //return feedItems list
        return feedItems;
    }
}
