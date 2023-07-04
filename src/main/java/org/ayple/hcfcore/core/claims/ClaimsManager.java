package org.ayple.hcfcore.core.claims;

import org.ayple.hcfcore.helpers.HcfSqlConnection;

import java.lang.reflect.Array;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;

public class ClaimsManager {
    private static HashSet<Claim> claims = new HashSet<Claim>();


    // this should be called if server is reset
    // gets all the stored claims data and turns it into claim objects
    public static void LoadClaims() throws SQLException {
        String sql = "SELECT corner_1_x, corner_1_z, corner_2_x, corner_2_z FROM factions";
        HcfSqlConnection conn = new HcfSqlConnection();
        Statement statement = conn.getConnection().createStatement();
        statement.executeQuery(sql);

        ResultSet results = statement.getResultSet();
        while (results.next()) {
            claims.add(new Claim(
                    results.getInt("corner_1_x"),
                    results.getInt("corner_1_z"),
                    results.getInt("corner_2_x"),
                    results.getInt("corner_2_z")
            ));
        }

        conn.closeConnection();

    }




}
