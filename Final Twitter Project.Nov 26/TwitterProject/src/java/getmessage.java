
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import javax.faces.context.FacesContext;

/**
 *
 * @author Admin
 */
@ManagedBean
@SessionScoped
public class getmessage {

    private String loginId;
    private static String result;
    private ArrayList<Message> aray;
    private String msg;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    
    public ArrayList<Message> getAray() {
        return aray;
    }

    public void setAray(ArrayList<Message> aray) {
        this.aray = aray;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getmessages() {
        aray = new ArrayList<>();
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            return "internalError";
        }

        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        ResultSetMetaData rmd = null;
        

        try {
            loginId = login.loginG;
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from t_message where ReceiverID='" + loginId + "' order by time desc ");
            rmd = rs.getMetaData();
            int columncount = rmd.getColumnCount();
            boolean find = false;
            String messages = "";
            while (rs.next()) /* if(find==false)
            {
            messages="you have 0 message";
            }   */
            {
                
                 
                aray.add(new Message(rs.getString(2), rs.getString(4), rs.getTimestamp(5)));
                
            }
            if(aray.isEmpty())
            {
                msg = "You have no messages";
                return "showmessage";
            }
            else
            {
                 return "showmessage";
            }
           
            //result=messages;

        } catch (SQLException e) {
            return "internalError";

        } finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (Exception e) {
                return "internalError";
            }
        }
    }
}
