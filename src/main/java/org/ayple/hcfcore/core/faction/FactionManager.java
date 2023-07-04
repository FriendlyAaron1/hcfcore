package org.ayple.hcfcore.core.faction;

import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class FactionManager {
    private static FactionManager INSTANCE;

    public static FactionManager getInstance() {
        return INSTANCE;
    }

    public ArrayList<Faction> getFactions() {
        return null;
    }

    public Faction getFaction(UUID id) {
        return null;
    }


    public boolean playerInFaction(UUID player_id) throws SQLException {
        String sql = "SELECT * FROM members WHERE player_uuid=?";
        HcfSqlConnection conn = new HcfSqlConnection();

        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, player_id.toString());
        statement.executeUpdate();
        conn.closeConnection();

        ResultSet result = statement.getResultSet();
        if (!result.next()) {
            return false;
        }

        return true;
    }

    // TODO
    public Faction getFactionByPlayerID(UUID player_id) throws SQLException {
        String sql = "SELECT * FROM members WHERE player_uuid=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, player_id.toString());
        statement.executeUpdate();
        conn.closeConnection();

        ResultSet result = statement.getResultSet();
        if (!result.next()) {
            return null;
        }

        return null;

    }

    public Faction getFaction(String faction_name) throws SQLException {
        String sql = "SELECT * FROM factions WHERE faction_name=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_name);
        statement.executeUpdate();
        conn.closeConnection();

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
        int corner_2_z = result.getInt("corner_z_z");

        Location hq = new Location(Bukkit.getWorld("world"), faction_hq_x, faction_hq_y, faction_hq_z);
        Claim claim = new Claim(corner_1_x, corner_1_z, corner_2_x, corner_2_z);

        return new Faction(id, faction_name, dtr, hq, claim);
    }

    public UUID getFactionIDFromName(String faction_name) throws SQLException {
        String sql = "SELECT id FROM factions WHERE faction_name=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_name);
        statement.executeUpdate();
        conn.closeConnection();

        ResultSet result = statement.getResultSet();
        if (!result.next()) {
            return null;
        }


        return UUID.fromString(result.getString("id"));
    }


    public void newFaction(String faction_name, Player leader) throws SQLException {
        if (getFaction(faction_name) != null) {
            return;
        }

        String sql = "INSERT INTO factions (faction_name) VALUES (?)";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_name);
        statement.executeUpdate();
        conn.closeConnection();


        sql = "INSERT INTO faction_members (faction_id, player_uuid, rank) VALUES (?, ?, 5)";
        conn = new HcfSqlConnection();
        statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, getFactionIDFromName(faction_name).toString());
        statement.setString(2, leader.getUniqueId().toString());
        statement.executeUpdate();
        conn.closeConnection();

    }
}
