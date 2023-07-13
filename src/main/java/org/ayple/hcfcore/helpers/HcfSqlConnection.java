package org.ayple.hcfcore.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HcfSqlConnection {

    Connection connection;

    public HcfSqlConnection() throws SQLException {
        String DB_HOST = ConfigHelper.getConfig().getString("DB_HOST");
        String DB_NAME = ConfigHelper.getConfig().getString("DB_NAME");
        String DB_PORT = ConfigHelper.getConfig().getString("DB_PORT");
        String DB_PASS = ConfigHelper.getConfig().getString("DB_PASS");
        String DB_USER = ConfigHelper.getConfig().getString("DB_USER");
        String url = "jdbc:mysql://" + DB_HOST + ":" + DB_PORT + "/" + DB_NAME + "?useSSL=false&characterEncoding=utf8";
        this.connection = DriverManager.getConnection(url, DB_USER, DB_PASS);
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public Connection getConnection() {
        return this.connection;
    }
}
