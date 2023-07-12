package org.ayple.hcfcore.core.faction;

import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.*;

public class FactionManager {
    public static ArrayList<Faction> getFactions() {
        return null;
    }


    // returns false meaning no faction was fount
    public static boolean playerInFaction(UUID player_id) throws SQLException {
        String sql = "SELECT * FROM faction_members WHERE player_uuid=?";
        HcfSqlConnection conn = new HcfSqlConnection();

        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, player_id.toString());
        statement.executeQuery();
        System.out.println(statement.toString());

        ResultSet result = statement.getResultSet();
        if (!result.next()) {
            return false;
        }

        conn.closeConnection();
        return true;
    }


    public static Faction getFactionFromPlayerID(UUID player_id) throws SQLException {
        String sql = "SELECT faction_id FROM faction_members WHERE player_uuid=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, player_id.toString());
        statement.executeQuery();
        System.out.println(statement.toString());

        ResultSet result = statement.getResultSet();
        if (!result.next()) {
            return null;
        }


        String faction_id  = result.getString("faction_id");
        conn.closeConnection();
        return getFaction(UUID.fromString(faction_id));
    }

    public static Faction getFaction(UUID id) throws SQLException {
        String sql = "SELECT * FROM factions WHERE id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, id.toString());
        statement.executeQuery();
        System.out.println(statement.toString());

        ResultSet result = statement.getResultSet();
        if (!result.next()) {
            return null;
        }

        String faction_name = result.getString("faction_name");
        float dtr = result.getFloat("dtr");
        String ally_faction_id = result.getString("ally_faction_id");
        int faction_hq_x = result.getInt("faction_hq_x");
        int faction_hq_y = result.getInt("faction_hq_y");
        int faction_hq_z = result.getInt("faction_hq_z");
        int corner_1_x = result.getInt("corner_1_x");
        int corner_1_z = result.getInt("corner_1_z");
        int corner_2_x = result.getInt("corner_2_x");
        int corner_2_z = result.getInt("corner_2_z");
        int bal = result.getInt("faction_bal");
        Claim claim = null;
        Location hq = null;

        if (faction_hq_x != 0 && faction_hq_y != 0 && faction_hq_z != 0) {
            hq = new Location(Bukkit.getWorld("world"), faction_hq_x, faction_hq_y, faction_hq_z);
        }
        if (corner_1_x != 0 && corner_1_z != 0 && corner_2_x != 0 && corner_2_z != 0) {
            claim = new Claim(id, faction_name, corner_1_x, corner_1_z, corner_2_x, corner_2_z);
        }

        conn.closeConnection();
        return new Faction(id, faction_name, dtr, bal, hq, claim);
    }


    public static Faction getFaction(String faction_name) throws SQLException {
        String sql = "SELECT * FROM factions WHERE faction_name=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_name);
        statement.executeQuery();
        System.out.println(statement.toString());

        ResultSet result = statement.getResultSet();
        if (!result.next()) {
            return null;
        }

        UUID id = UUID.fromString(result.getString("id"));
        float dtr = result.getFloat("dtr");
        String ally_faction_id = result.getString("ally_faction_id");
        int faction_hq_x = result.getInt("faction_hq_x");
        int faction_hq_y = result.getInt("faction_hq_y");
        int faction_hq_z = result.getInt("faction_hq_z");
        int corner_1_x = result.getInt("corner_1_x");
        int corner_1_z = result.getInt("corner_1_z");
        int corner_2_x = result.getInt("corner_2_x");
        int corner_2_z = result.getInt("corner_2_z");
        int bal = result.getInt("faction_bal");
        Claim claim = null;
        Location hq = null;

        if (faction_hq_x != 0 && faction_hq_y != 0 && faction_hq_z != 0) {
            hq = new Location(Bukkit.getWorld("world"), faction_hq_x, faction_hq_y, faction_hq_z);
        }

        if (corner_1_x != 0 && corner_1_z != 0 && corner_2_x != 0 && corner_2_z != 0) {
            claim = new Claim(id, faction_name, corner_1_x, corner_1_z, corner_2_x, corner_2_z);
        }

        conn.closeConnection();
        return new Faction(id, faction_name, dtr, bal, hq, claim);
    }

    public static UUID getFactionIDFromName(String faction_name) throws SQLException {
        String sql = "SELECT id FROM factions WHERE faction_name=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_name);
        statement.executeQuery();

        System.out.println(statement.toString());

        ResultSet result = statement.getResultSet();
        if (!result.next()) {
            return null;
        }

        System.out.println("Faction ID From Name: " + result.getString("id"));



        String id = result.getString("id");
        conn.closeConnection();
        return UUID.fromString(id);
    }


    // TODO: add a fail safe where it deletes any queries made
    // if an error arrises
    public static boolean newFaction(String faction_name, Player leader) throws SQLException {
        if (getFaction(faction_name) != null) {
            return false;
        }

        String sql = "INSERT INTO factions (faction_name) VALUES (?)";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_name);
        statement.executeUpdate();
        System.out.println(statement.toString());
        conn.closeConnection();

        String faction_id = getFactionIDFromName(faction_name).toString();
        if (faction_id == null) {
            return false;
        }

        System.out.println("RECEIVED RESULT FROM getFactionIDFromName: " + faction_id);



        sql = "INSERT INTO faction_members VALUES (?, ?, 3)";
        conn = new HcfSqlConnection();
        statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_id);
        statement.setString(2, leader.getUniqueId().toString());
        statement.executeUpdate();
        System.out.println(statement.toString());
        conn.closeConnection();

        return true;
    }

    public static boolean isPlayerLeader(UUID player_id) throws SQLException {
        Faction faction = getFactionFromPlayerID(player_id);
        if (faction == null) {
            return false;
        }

        return faction.getFactionMembers().get(player_id) == 3;
    }

    public static boolean isPlayerLeader(Faction faction, UUID player_id) {
        return faction.getFactionMembers().get(player_id) == 3;
    }

    public static boolean isPlayerCoLeader(Faction faction, UUID player_id) {
        return faction.getFactionMembers().get(player_id) == 2;
    }

    public static void disbandFaction(UUID faction_id) throws SQLException {
        String sql = "DROP FROM factions WHERE id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_id.toString());
        statement.executeUpdate();
        System.out.println(statement.toString());

        conn.closeConnection();

        ClaimsManager.loadClaims(); // reloads the claims
    }

    public static void setFactionHQ(UUID faction_id, Location location) throws SQLException {
        String sql = "UPDATE factions SET faction_hq_x=?, faction_hq_y=?, faction_hq_z=? WHERE id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setInt(1, (int) Math.round(location.getX()));
        statement.setInt(2, (int) Math.round(location.getY()));
        statement.setInt(3, (int) Math.round(location.getZ()));
        statement.setString(4, faction_id.toString());
        statement.executeUpdate();

        System.out.println(statement.toString());
        conn.closeConnection();
    }

    public static void renameFaction(String faction_name, String new_name) throws  SQLException {
        String sql = "UPDATE factions SET faction_name=? WHERE faction_name=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, new_name);
        statement.setString(2, faction_name);
        statement.executeUpdate();

        System.out.println(statement.toString());
        conn.closeConnection();
    }

    public static UUID getOwnerIDOfFaction(UUID faction_id) throws SQLException {
        String sql = "SELECT player_uuid FROM faction_members WHERE faction_id=? AND `rank` = 3";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_id.toString());
        statement.executeQuery();

        ResultSet result = statement.getResultSet();
        if (!result.next()) {
            return null;
        }

        System.out.println(statement.toString());
        String owner_uuid = result.getString("player_uuid");
        conn.closeConnection();
        return UUID.fromString(owner_uuid);
    }

    public static void decreaseDTR(UUID faction_id) throws SQLException {
        String sql = "UPDATE factions SET dtr = dtr - 1 WHERE id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_id.toString());
        statement.executeUpdate();

        conn.closeConnection();

    }
}
