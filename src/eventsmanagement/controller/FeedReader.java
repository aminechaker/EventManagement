/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eventsmanagement.controller;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.fetcher.FeedFetcher;
import com.sun.syndication.fetcher.FetcherException;
import com.sun.syndication.fetcher.impl.HttpURLFeedFetcher;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Amine CHAKER
 */
public class FeedReader implements Runnable {

    public static List<String> titles =  new ArrayList<>() ;
    public static List<String> links =  new ArrayList<>() ;
    
    @Override
    public void run() {
        try {
            FeedFetcher fetcher = new HttpURLFeedFetcher();
            SyndFeed feed = fetcher.retrieveFeed(new URL("http://indianexpress.com/section/technology/feed/"));
            for (Object object : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) object;
                System.out.println("--Title--> "+entry.getTitle());
                System.out.println("--Link--> "+entry.getLink());
                titles.add(entry.getTitle());
                links.add(entry.getLink());
            }
        } catch (IllegalArgumentException | IOException | FeedException | FetcherException ex) {
            Logger.getLogger(FeedReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<String> getTitles(){
        try {
            FeedFetcher fetcher = new HttpURLFeedFetcher();
            SyndFeed feed = fetcher.retrieveFeed(new URL("http://indianexpress.com/section/technology/feed/"));
            for (Object object : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) object;
                titles.add(entry.getTitle());
            }
        } catch (IllegalArgumentException | IOException | FeedException | FetcherException ex) {
            Logger.getLogger(FeedReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return titles;
    }
    public List<String> getLinks(){
        try {
            FeedFetcher fetcher = new HttpURLFeedFetcher();
            SyndFeed feed = fetcher.retrieveFeed(new URL("http://indianexpress.com/section/technology/feed/"));
            for (Object object : feed.getEntries()) {
                SyndEntry entry = (SyndEntry) object;
                links.add(entry.getLink());
            }
        } catch (IllegalArgumentException | IOException | FeedException | FetcherException ex) {
            Logger.getLogger(FeedReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return links;
    }
    
    
}
