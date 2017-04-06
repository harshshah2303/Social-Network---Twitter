/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author harshshah2303
 */
import java.sql.*;
public class showtweets {
    
    private String ID;
    private String tweet;
    private Timestamp time;
    
    public showtweets(String name, String tw, Timestamp t)
    {
        ID = name;
        tweet = tw;
        time = t;
    }
    
    public showtweets(String tw,Timestamp t)
    {
        tweet = tw;
        time = t;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTweet() {
        return tweet;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public void setTweet(String tweet) {
        this.tweet = tweet;
    }

    
    
    
}
