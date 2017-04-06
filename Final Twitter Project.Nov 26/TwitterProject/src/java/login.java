/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "login")
@SessionScoped
public class login implements Serializable {

    public static String loginG;
    private String loginID;
    private String password;
    private String msg = "";
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    

    private DefaultStreamedContent imgData;
    private UploadedFile uploadedFile;

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }

    public DefaultStreamedContent getImgData() {
        return imgData;
    }

    public void setImgData(DefaultStreamedContent imgData) {
        this.imgData = imgData;
    }

    public static String getLoginG() {
        return loginG;
    }

    public static void setLoginG(String loginG) {
        login.loginG = loginG;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String upload() {

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return ("internalError");
        }

        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = (Statement) conn.createStatement();
            if (uploadedFile != null) {

                InputStream fin2 = uploadedFile.getInputstream();

                PreparedStatement pre = conn.prepareStatement("insert into t_user (ProfileImage) values(?)");

                pre.setBinaryStream(1, fin2);
                pre.executeUpdate();
                pre.close();
                return ("mainpage");
            }

        } catch (Exception e) {
            e.printStackTrace();

        }
        return ("internalerror");
    }

    public String loginto() {
        loginG = loginID;
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return ("internalError");
        }

        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select err,ProfileImage,Name from t_user where LoginID='" + loginID + "' and password='" + password + "'");

            boolean find = false;
            if (rs.next()) {
                find = true;
                name = rs.getString(3);
                InputStream is = new ByteArrayInputStream((byte[]) rs.getBytes("ProfileImage"));
                imgData = new DefaultStreamedContent(is, "image/jpeg");
                if (rs.getInt(1) >= 3) {
                    //this.loginID = "";
                    msg = "Your account is Locked";
                    return ("login");
                }
                int t = stat.executeUpdate("update t_user set err=0 where LoginID='" + loginID + "'");

                // this.loginID = "";
                return ("mainpage");

            }

            if (find == false) {
                rs = stat.executeQuery("select err from t_user where LoginID='" + loginID + "'");
                if (rs.next()) {
                    if (rs.getInt(1) >= 3) {

                        // this.loginID = "";
                        msg = "Your account is Locked";
                        return ("login");
                    }

                    int t = stat.executeUpdate("update t_user set err=err+1 where LoginID='" + loginID + "'");

                    //  this.loginID = "";
                    msg = "Your password is incorrect";
                    return ("login");
                } else {
                    // this.loginID = "";
                    msg = "Your ID doesnt exist.Please register first";
                    return ("login");
                }
            }
            //  this.loginID = "";
            return ("login");
        } catch (SQLException e) {
            e.printStackTrace();
            return ("internalError");
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

    public String ValueNull() {
        this.loginID = "";
        login.loginG = "";
        this.msg = "";
        return "login";
    }

    public String ValueNull1() {
        this.loginID = "";
        login.loginG = "";

        return "login";
    }
}
