/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author harshshah2303
 */
@Named(value = "tweet")
@ManagedBean
@SessionScoped
public class Tweet implements Serializable {

    public static String tweet;
    private String tweetID;

    private int twt_count;
    private int flw_count;
    private int flwing_count;
    private ArrayList<Message> arayf;
    private ArrayList<Message> aray;

    public ArrayList<Message> getArayf() {
        return arayf;
    }

    public void setArayf(ArrayList<Message> arayf) {
        this.arayf = arayf;
    }

    public int getTwt_count() {
        return twt_count;
    }

    public void setTwt_count(int twt_count) {
        this.twt_count = twt_count;
    }

    public int getFlw_count() {
        return flw_count;
    }

    public void setFlw_count(int flw_count) {
        this.flw_count = flw_count;
    }

    public int getFlwing_count() {
        return flwing_count;
    }

    public void setFlwing_count(int flwing_count) {
        this.flwing_count = flwing_count;
    }

    public ArrayList<Message> getAray() {
        return aray;
    }

    public void setAray(ArrayList<Message> aray) {
        this.aray = aray;
    }

    public static String getTweet() {
        return tweet;
    }

    public static void setTweet(String tweet) {
        Tweet.tweet = tweet;
    }

    public String getTweetID() {
        return tweetID;
    }

    public void setTweetID(String tweetID) {
        this.tweetID = tweetID;
    }

    public String postTweet() {
        String LoginID = login.loginG;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return "internalError";
        }

        // String LoginID = login.loginG;
        int tweet_count = 0;
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";

        try {
            conn = DriverManager.getConnection(DB_URL, "juturus7543", "1445693");
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from t_account where LoginID = '" + LoginID + "'");

            int nxt_count = 0;
            if (rs.next()) {
                tweet_count = rs.getInt(4);
                nxt_count = rs.getInt(4) + 1;

                DatabaseFunction.insertUpdate("update t_account set tweet_count = " + nxt_count + " where LoginID = '" + LoginID + "'");

                DatabaseFunction.insertUpdate("insert into t_tweet values ('" + LoginID + "', '" + tweetID + "',NOW())");

            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "internalError";
            //   return "internalError";
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
                if (stat != null) {
                    stat.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        aray = GetTweets();
        this.tweetID = "";
        return "mainpage";

    }

    public int Twt_count() {
        String LoginID = login.loginG;
        int temp = DatabaseFunction.getIntValue("select Tweet_Count from t_account where LoginID = '" + LoginID + "'  ");
        this.setTwt_count(temp);
        return temp;
    }

    public int follower_count() {
        String LoginID = login.loginG;
        int temp = DatabaseFunction.getIntValue("select Followers from t_account where LoginID = '" + LoginID + "'  ");
        this.setFlw_count(temp);
        return temp;
    }

    public int following_count() {
        String LoginID = login.loginG;
        int temp = DatabaseFunction.getIntValue("select Following from t_account where LoginID = '" + LoginID + "'  ");
        this.setFlwing_count(temp);
        return temp;
    }

    public static ArrayList<Message> GetTweets() {
        ArrayList<Message> obj = new ArrayList<>();
        // aray = new ArrayList<>();
        // loginG = loginID;
        String loginID = login.loginG;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return null;
            // return ("internalError");
        }

        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");

            stat = conn.createStatement();
            rs = stat.executeQuery("select * from t_tweet where LoginID = '" + loginID + "' order by Time desc ");
            while (rs.next()) {
                obj.add(new Message(rs.getString(1), rs.getString(2), rs.getTimestamp(3)));
            }
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (Exception e) {
                return null;
            }
        }
        return obj;
    }

    public static ArrayList<Message> GetFollowTweets() {
        ArrayList<Message> obj = new ArrayList<>();
        // aray = new ArrayList<>();
        // loginG = loginID;
        String loginID = login.loginG;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return null;
            // return ("internalError");
        }

        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = (Statement) conn.createStatement();
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from t_tweet where LoginID in (select following from t_following where LoginID = '" + loginID + "') order by Time desc ");
            while (rs.next()) {
                obj.add(new Message(rs.getString(1), rs.getString(2), rs.getTimestamp(3)));
            }
        } catch (SQLException e) {
            return null;
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (Exception e) {
                return null;
            }
        }
        return obj;
    }
}
