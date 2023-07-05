package org.ayple.hcfcore.core.claims;

import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.sql.*;
import java.util.HashSet;
import java.util.LinkedList;

public class ClaimsManager {
    private static HashSet<Claim> claims = new HashSet<Claim>();


    // this should be called if server is reset
    // gets all the stored claims data and turns it into claim objects
    public static void loadClaims() throws SQLException {
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

    public static void saveClaimsToDisk() {

    }

    public static void newClaim(Player player, Selection selection) throws SQLException {
        claims.add(new Claim(selection.getPos1(), selection.getPos2()));
    }

    public static Faction playerInClaim(Player player) {
        for (Claim claim : claims) {
            if (claim.getBoundingBox().contains(player.getBoundingBox())) {
                return FactionManager.getFaction(claim.getOwnerFactionID());
            }
        }

        return null;
    }

    public static boolean playerOwnsClaim(Player player) {
        Faction faction = playerInClaim(player);
        if (faction == null) {
            return false;
        }

//        for (Player member : faction.getFactionMembers()) {
//            if (member.getUniqueId().equals(player.getUniqueId())) {
//                return true;
//            }
//        }

        if (faction.getFactionMembers().get(player.getUniqueId()) != null) {
            return true;
        }

        return false;
    }


}
