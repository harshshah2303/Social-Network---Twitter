
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author harshshah2303
 */
public class DatabaseFunction {

    static final String DB_URL = "jdbc:mysql://mis-sql.uhcl.edu/juturus7543";

    public static void insertUpdate(String query) {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection(DB_URL, "juturus7543", "1445693");
            stat = conn.createStatement();
            stat.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
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
    }

    public static int getIntValue(String query) {
        Connection conn = null;
        Statement stat = null;
        ResultSet rs = null;
        int temp = 0;
        try {
            conn = DriverManager.getConnection(DB_URL, "juturus7543", "1445693");
            stat = conn.createStatement();
            rs = stat.executeQuery(query);

            if (rs.next()) {
                temp = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();

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
        return temp;

    }

}
