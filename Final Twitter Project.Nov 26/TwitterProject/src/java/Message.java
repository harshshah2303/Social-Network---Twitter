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
import java.util.ArrayList;

public class Message {

    private String senderID;
    private String msg;
    private Timestamp time;

    public Message(String s, String m, Timestamp tm) {
        senderID = s;
        msg = m;
        time = tm;
    }

    public String getSenderID() {
        return senderID;
    }

    public void setSenderID(String senderID) {
        this.senderID = senderID;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }
}
