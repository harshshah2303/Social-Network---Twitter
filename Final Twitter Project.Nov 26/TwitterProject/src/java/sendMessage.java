/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
import java.util.ArrayList;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
@ManagedBean
@SessionScoped
public class sendMessage {

    private String receiverID;
    private String message;
    private ArrayList<String> user=new ArrayList<>();
    FacesContext fc = FacesContext.getCurrentInstance();
    String senderId = fc.getExternalContext().getRequestParameterMap().get("loginID");
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public ArrayList<String> getUser() {
        return user;
    }

    public void setUser(ArrayList<String> user) {
        this.user = user;
    }
   

    public String getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(String receiverID) {
        this.receiverID = receiverID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public FacesContext getFc() {
        return fc;
    }

    public void setFc(FacesContext fc) {
        this.fc = fc;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }
    
    public String send()
    {
    try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return ("internaError");
        }
        
        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = conn.createStatement();
            int t=stat.executeUpdate("insert into t_message value(null,'"+senderId+"','"+receiverID+"','"+message+"',null)");
            msg = "Your Message has been send";
            return ("sendMessage");
        } catch (SQLException e) {
            e.printStackTrace();
            return("internalError");
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void takeID() {
        
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
           e.printStackTrace();
        }
        
        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = conn.createStatement();
            rs = stat.executeQuery("select LoginID from t_user");
            boolean find=false;
            while(rs.next())
            {
                find=true;
             user.add(rs.getString(1));
            }
            if(find==false){user.add("no people");}
           
        } catch (SQLException e) {
            e.printStackTrace();
         
        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public String Valuenull()
    {
        this.message = "";
        this.msg = "";
        return "sendMessage";
    }
    
    
}
    

