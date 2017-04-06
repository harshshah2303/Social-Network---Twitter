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
@Named(value = "follow")
@SessionScoped
@ManagedBean
public class Follow implements Serializable {

    private String msg;
    private String FollowID;

    ArrayList<Follow> results;
    ArrayList<Follow> Following;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getFollowID() {
        return FollowID;
    }

    public void setFollowID(String FollowID) {
        this.FollowID = FollowID;
    }

    public ArrayList<Follow> getResults() {
        return results;
    }

    public void setResults(ArrayList<Follow> results) {
        this.results = results;
    }

    public ArrayList<Follow> getFollowing() {
        return Following;
    }

    public void setFollowing(ArrayList<Follow> Following) {
        this.Following = Following;
    }

    public Follow() {

    }

    public Follow(String follower) {
        FollowID = follower;
    }

    public String getFollowers() {
        results = new ArrayList<>();
        Following = new ArrayList<>();
        String LoginID = login.loginG;

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return "internalError";
        }

        Connection conn = null;
        Statement stat = null;
        Statement stat1 = null;
        ResultSet rs = null;
        ResultSet rs1 = null;

        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";

        try {
            conn = DriverManager.getConnection(DB_URL, "juturus7543", "1445693");
            stat = conn.createStatement();
            stat1 = conn.createStatement();
            rs = stat.executeQuery("select followers from t_followers where LoginID='" + LoginID + "'");
            rs1 = stat1.executeQuery("select following from t_following where LoginID='" + LoginID + "'");

            while (rs.next()) {
                results.add(new Follow(rs.getString(1)));

            }
            while (rs1.next()) {
                Following.add(new Follow(rs1.getString(1)));

            }

        } catch (SQLException e) {
            return "internalError";
        } finally {
            try {
                conn.close();
                stat.close();
                rs.close();
            } catch (Exception e) {
                return "internalError";
            }
        }

        return "";

    }

    public String followrqst() {
        String LoginID = login.loginG;
        int followers_count = 0;
        int following_count = 0;

        String loginf = "";
        String followf = "";

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return "internalError";
        }

        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        Statement stat2 = null;
        ResultSet rs2 = null;
        Statement stat3 = null;
        ResultSet rs3 = null;
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";

        try {
            conn = DriverManager.getConnection(DB_URL, "juturus7543", "1445693");
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from t_account where LoginID = '" + FollowID + "'");
            stat2 = conn.createStatement();
            rs2 = stat2.executeQuery("select * from t_account where LoginID = '" + LoginID + "'");
            stat3 = conn.createStatement();
            rs3 = stat3.executeQuery("select * from t_following where LoginID = '" + LoginID + "' and following = '" + FollowID + "'");

            int nxt_followers_count = 0;
            int nxt_following_count = 0;
            if (rs.next()) {
                if (rs2.next()) {
                    if (!rs3.next()) {
                        //has account
                        followers_count = rs.getInt(2);
                        following_count = rs2.getInt(3);
                        nxt_followers_count = rs.getInt(2) + 1;
                        nxt_following_count = rs2.getInt(3) + 1;

                        //add into following table
                        //  if(LoginID.equals(rs2.getString))
                        DatabaseFunction.insertUpdate("insert into t_following values ('" + LoginID + "', '" + FollowID + "')");

                        //add into followers table
                        DatabaseFunction.insertUpdate("insert into t_followers values ('" + FollowID + "', '" + LoginID + "')");

                        //update followers and following count in the account table
                        DatabaseFunction.insertUpdate("update t_account set followers = " + nxt_followers_count + " where LoginID = '" + FollowID + "'");

                        DatabaseFunction.insertUpdate("update t_account set following = " + nxt_following_count + " where LoginID = '" + LoginID + "'");

                        msg = "You have started following " + FollowID;
                        return "follow";
                    } else {
                        msg = "You are already following " + FollowID;
                        return "follow";
                    }

                }
                return "";
            } else {
                //person you want to follow is not on twitter
                msg = FollowID + " has not yet registered on Twitter ";
                return "follow";
            }

        } catch (SQLException e) {
            return "internalError";
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
                return "internalError";
            }
        }

    }

    public String setMsgNull() {
        this.msg = "";
        this.FollowID = "";
        return "follow";
    }

}
