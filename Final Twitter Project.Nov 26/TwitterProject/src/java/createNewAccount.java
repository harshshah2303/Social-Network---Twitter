import java.io.InputStream;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.sql.*;
import java.text.DecimalFormat;
import java.util.Scanner;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Admin
 */
@ManagedBean(name = "createNewAccount")
@RequestScoped
public class createNewAccount implements Serializable {
    
    private String loginId; 
    private String password;
    private String name;
    private String Email;
    private String msg = "";

    private UploadedFile uploadedFile;
//    private File uploadedFile;

    public UploadedFile getUploadedFile() {
        return uploadedFile;
    }

    public void setUploadedFile(UploadedFile uploadedFile) {
        this.uploadedFile = uploadedFile;
    }
    
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
    

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }
   
    
    public String createNewAccount() 
    {
        
       try
        {
            Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e)
        {
            return ("internalError");
        }
        
        final String url = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection(url, "juturus7543", "1445693");
            stat = (Statement) conn.createStatement();
            rs = stat.executeQuery("select LoginID,Email from t_user");
            boolean idUsed = false;
            boolean EmailUsed=false;
            while (rs.next()) {
                if (loginId.equals(rs.getString(1))) {
                    idUsed = true;
                    break;
                }
                if (Email.equals(rs.getString(2))) {
                    EmailUsed = true;
                    break;
                }
            }
            if (idUsed== false && EmailUsed == false) 
            {
//////           int r = stat.executeUpdate("insert into t_user values('" + loginId + "','"
//                        + password + "','" + name + "','" + Email+"',0,null)");
//                
//                stat.executeUpdate("insert into t_account values('" + loginId + "',0,0,0)");
                if (uploadedFile != null) 
                {

                    InputStream fin2 = uploadedFile.getInputstream();

                    PreparedStatement pre = conn.prepareStatement("insert into t_user (LoginID,Password,Name,Email,err,ProfileImage) values('" + loginId + "','"
                            + password + "','" + name + "','" + Email + "','0',?)");

                    pre.setBinaryStream(1, fin2);
                    pre.executeUpdate();
                    pre.close();
                    stat.executeUpdate("insert into t_account values('" + loginId + "',0,0,0)");

                    return ("login");
                } else 
                {
                   
                    msg = "Please select the image";
                    return("signup");
                }
                
                
            }
            else
            {
               msg = "Your Login Id or email has been used.Please use other Login id or email";
                return("signup");
            }
              
        } catch (SQLException e) 
        {
            e.printStackTrace();
            return ("internalError");
           
        } 
        catch (Exception e) {
            e.printStackTrace();
            return ("internalError");
        }
        finally {
            try {
                rs.close();
                stat.close();
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public String MsgNull() 
    {
        this.Email = "";
        this.loginId = "";
        this.name = "";
        this.password = "";
        this.msg = "";
        return "signup";
    }
    
    
}

