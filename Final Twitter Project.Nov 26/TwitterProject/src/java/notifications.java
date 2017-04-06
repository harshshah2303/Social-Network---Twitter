
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.*;
import javax.faces.context.FacesContext;
import javax.inject.Named;


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author vihar
 */
@ManagedBean(name="notifications")
@SessionScoped
public class notifications implements Serializable{


    public ArrayList<String> fields;

    public ArrayList<String> getFields() {
        return fields;
    }

    public void setFields(ArrayList<String> fields) {
        this.fields = fields;
    }
    
    

    public  void notice() {
        fields = new ArrayList<>();
         try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
        }

        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;   
         
        try {
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select SenderID, time from t_message where ReceiverID='" + login.loginG + "'order by time desc");

            while (rs.next()) {
                fields.add(rs.getString(1)+" "+rs.getString(2));
            }

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
}
