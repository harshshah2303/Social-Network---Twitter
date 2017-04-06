
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Named;
import javax.faces.bean.SessionScoped;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author vihar
 */
@ManagedBean
@SessionScoped

public class passwordRetrieval {

    private String mail;
    private String password;
    private String msg = "";

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    public passwordRetrieval() {
    }

    public String Retrieval() {

        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return ("internalError");
        }
        try {

            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = conn.createStatement();
            rs = stat.executeQuery("select  Password from t_user where Email='" + mail + "'");
            if (rs.next()) {
                password = rs.getString(1);
                Properties props = new Properties();
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.socketFactory.port", "25");
                props.put("mail.smtp.socketFactory.class",
                        "javax.net.ssl.SSLSocketFactory");
                
                props.put("mail.smtp.starttls.enable", "true");
                props.put("mail.smtp.auth", "true");
                props.put("mail.debug", "true");
                
                props.put("mail.smtp.EnableSSL.enable","true");
                props.put("mail.smtps.**ssl.enable", "false");
                props.put("mail.smtp.port", "587");
                
                

                
                

                Session session = Session.getInstance(props,
                        new javax.mail.Authenticator() {
                     @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        
                        return new PasswordAuthentication("hawktwitterteam@gmail.com", "hawktwitter");
                    }
                });

                try {

                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress("hawktwitterteam@gmail.com"));
                    message.setRecipients(Message.RecipientType.TO,
                            InternetAddress.parse(mail));
                    message.setSubject("Important-Password of HawkTwitter Account");
                    message.setText("Dear Twitter user,"
                            + "\n Your password is as below!"
                            + "\n\n" + password + "");

                    Transport.send(message);
                    
                   // this.mail = "";
                   msg = "The Password is sent to your registered email";
                    return ("passwordRetrieval");

                } catch (MessagingException e) {
                    throw new RuntimeException(e);
                }

            } else 
            {
               // this.mail = "";
                msg = "Your email is not in our records of Hawk Twitter";
                return ("passwordRetrieval");

            }

        } catch (SQLException e) {

        }

        return ("internalError");
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }
    
    public String ValueNull()
    {
        this.msg = "";
        this.mail = "";
        return ("passwordRetrieval");
    }

}
