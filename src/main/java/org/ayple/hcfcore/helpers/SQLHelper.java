package org.ayple.hcfcore.helpers;


import javax.annotation.Nullable;
import java.sql.*;

public class SQLHelper {

    public static String DB_USER = "user";
    public static String DB_PASS = "";
    public static String DB_NAME = "";

    public static ResultSet querydb(String sqlstatement) {
        ResultSet result = null;

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + DB_NAME, DB_USER, DB_PASS);

            Statement statement = conn.createStatement();
            result = statement.executeQuery(sqlstatement);

            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return result;
    }

}
