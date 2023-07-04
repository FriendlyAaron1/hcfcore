package org.ayple.hcfcore.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class HcfSqlConnection {

    Connection connection;

    public HcfSqlConnection() throws SQLException {
        this.connection = DriverManager.getConnection("jdbc:mysql");
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }

    public Connection getConnection() {
        return this.connection;
    }
}
