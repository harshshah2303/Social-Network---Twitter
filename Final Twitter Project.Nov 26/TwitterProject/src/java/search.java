import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Admin
 */
@ManagedBean
@RequestScoped
public class search {
    
    public static String keyID1;
    private String key;
    private String result;
    private String keyId;
    private String msg ;
    private String msg1;

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }
    
    

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    

    private ArrayList<showtweets> aray = new ArrayList<>();
    
    private ArrayList<showtweets> aray1 = new ArrayList<>();

    public ArrayList<showtweets> getAray1() {
        return aray1;
    }

    public void setAray1(ArrayList<showtweets> aray1) {
        this.aray1 = aray1;
    }

    public ArrayList<showtweets> getAray() {
        return aray;
    }

    public void setAray(ArrayList<showtweets> aray) {
        this.aray = aray;
    }
    
    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }
    
    

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public static String getKeyID1() {
        return keyID1;
    }

    public static void setKeyID1(String keyID1) {
        search.keyID1 = keyID1;
    }

   
    

    public String MySearch()
    {
        try 
        {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) 
        {
            return "internalError";
        }
        
        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        ResultSetMetaData rmd = null;

        try 
        {
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from t_tweet where Tweet like '%"+key+"%'");
            rmd=rs.getMetaData();
            int columncount=rmd.getColumnCount();
            boolean find=false;    
            String messages="";
            
           /* if(find==false)
            {
                return "notweet";
            }*/
           
            while (rs.next()) 
            {
               /*find=true;
               for(int i=1;i<=columncount;i++)
               {
                messages+=(rs.getString(i)+"  ");
               }
               messages=String.format("%s %n", messages);*/
                
                aray1.add(new showtweets(rs.getString(1),rs.getString(2),rs.getTimestamp(3)));
                
            }
            if(aray1.isEmpty())
            {
                msg1 = "Sorry!!!No tweets match your search";
                return "search";
            }
            else
            {
                return "searchtweet";
            }
           
            
            
           // return "";
            
           //result=messages;
        } 
        catch (SQLException e) 
        {
            return "internalError";
        } 
        finally 
        {
            try 
            {
                rs.close();
                stat.close();
                conn.close();
            } catch (Exception e) 
            {
                return "internalError";
            }
        }
    }
    
    public String searchID()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            return "internalError";
        }
        
        
        String LoginID = login.loginG;
        keyID1 = keyId;
        
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        Statement stat2 = null;
        ResultSet rs2 = null;
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        try
        {
            conn = DriverManager.getConnection(DB_URL,"juturus7543","1445693");
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from t_following where LoginID = '" + LoginID +"' && following = '" + keyId + "'");
            stat2 = conn.createStatement();
            rs2 = stat2.executeQuery("select * from t_tweet where LoginID = '" + keyId + "'");
            if(rs.next())
            {
                //you are following him
                while(rs2.next())
                {
                    aray.add(new showtweets(rs2.getString(2), rs2.getTimestamp(3)));
                    
                }
                return "followtweets";
            }
            else
            {
                //send to follow page
                return "searchfollow";
            }
        }
        catch(SQLException e)
        {
            return "internalError";
        }
        finally
        {
            try
            {
                if(conn!= null)
                   conn.close();
               if(stat!= null)
                   stat.close();
               if(rs!=null)
                   rs.close();
            }
            catch(Exception e)
            {
                return "internalError";
            }
        }
    }
    
    public String followsearch()
    {
        String LoginID = login.loginG;
        String keyID1 = search.keyID1;
        int followers_count = 0;
        int following_count = 0;
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch(Exception e)
        {
            return "internalError";
        }
        
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        Statement stat2 = null;
        ResultSet rs2 = null;
        final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        
        try
        {
            conn = DriverManager.getConnection(DB_URL,"juturus7543","1445693");
            stat = conn.createStatement();
            rs = stat.executeQuery("select * from t_account where LoginID = '" + keyID1 + "'");
            stat2 = conn.createStatement();
            rs2 = stat2.executeQuery("select * from t_account where LoginID = '" + LoginID + "'");
            int nxt_followers_count  = 0;
            int nxt_following_count = 0;
            if(rs.next())
            {
                if(rs2.next())
                {    
                    //has account
                    followers_count = rs.getInt(2);
                    following_count = rs2.getInt(3);
                    nxt_followers_count = rs.getInt(2) + 1;
                    nxt_following_count = rs2.getInt(3) + 1;

                    //add into following table
                    DatabaseFunction.insertUpdate("insert into t_following values ('" + LoginID + "', '" + keyID1 + "')");

                    //add into followers table
                    DatabaseFunction.insertUpdate("insert into t_followers values ('" + keyID1 + "', '" + LoginID + "')");

                    //update followers and following count in the account table
                    DatabaseFunction.insertUpdate("update t_account set followers = " + nxt_followers_count + " where LoginID = '" + keyID1 + "'");

                    DatabaseFunction.insertUpdate("update t_account set following = " + nxt_following_count + " where LoginID = '" + LoginID + "'");
                    
                    msg = "You have started following " + keyID1;
                    return "searchfollow";
                }
                return "";
            }
            else
            {
                //person you want to follow is not on twitter
                msg = "Sorry " + keyID1 + " have not yet registered on twitter";
                    return "searchfollow";
            }
        }
        catch(SQLException e)
        {
            return "internalError";
        }
        finally
        {
            try
            {
                if(conn!=null) 
                    conn.close();
                if(stat!=null)
                    stat.close();
                if(rs!=null)
                    rs.close();
            }
            catch(Exception e)
            {
                return "internalError";
            }
        }
    }
    
    public String ValueNull()
    {
        this.msg = "";
        return "searchfollow";
    }
    
}
