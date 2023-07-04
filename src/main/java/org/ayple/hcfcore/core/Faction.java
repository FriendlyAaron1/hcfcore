package org.ayple.hcfcore.core;

import org.ayple.hcfcore.Logger;
import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.helpers.SQLHelper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Faction {
    private final String faction_id;
    private final String faction_name;

    private final Location faction_hq;

    private final Claim claim;

    private final float dtr;

    public String getFactionName() { return this.faction_name; }
    public Location getFactionHQ() { return this.faction_hq; }

    public String getFactionID() { return this.faction_id; }

    public Faction(String faction_id, String faction_name, float dtr, Location faction_hq, Claim claim) {
        this.faction_id = faction_id;
        this.faction_name = faction_name;
        this.faction_hq = faction_hq;
        this.claim = claim;
        this.dtr = dtr;
    }

    public static Faction getFactionFromID(String faction_id) {
        Faction faction;
        try {
            ResultSet result = SQLHelper.querydb("SELECT * FROM factions WHERE id='" + faction_id + "'");
            if (result.isBeforeFirst()) {
                return null;
            }

            String name = result.getString("faction_name");
            float dtr = result.getFloat("dtr");
            String ally_id = result.getString("ally_faction_id");
            int x_hq = result.getInt("faction_hq_x");
            int y_hq = result.getInt("faction_hq_y");
            int z_hq = result.getInt("faction_hq_z");
            int x_corner_1 = result.getInt("corner_1_x");
            int z_corner_1 = result.getInt("corner_1_z");
            int x_corner_2 = result.getInt("corner_2_x");
            int z_corner_2 = result.getInt("corner_2_z");
            Claim faction_claim = new Claim(faction_id, x_corner_1, z_corner_1, x_corner_2, z_corner_2);
            Location faction_hq = new Location(Bukkit.getWorld("world"), x_hq, y_hq, z_hq);

            return new Faction(faction_id, name, dtr, faction_hq, faction_claim);



        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Faction getFactionFromName(String faction_name) {
        try {
            ResultSet result = SQLHelper.querydb("SELECT * FROM factions WHERE name='" + faction_name + "'");
            if (result.isBeforeFirst()) {
                return null;
            }

            String faction_id = result.getString("id");
            String name = result.getString("faction_name");
            float dtr = result.getFloat("dtr");
            String ally_id = result.getString("ally_faction_id");
            int x_hq = result.getInt("faction_hq_x");
            int y_hq = result.getInt("faction_hq_y");
            int z_hq = result.getInt("faction_hq_z");
            int x_corner_1 = result.getInt("corner_1_x");
            int z_corner_1 = result.getInt("corner_1_z");
            int x_corner_2 = result.getInt("corner_2_x");
            int z_corner_2 = result.getInt("corner_2_z");
            Claim faction_claim = new Claim(faction_id, x_corner_1, z_corner_1, x_corner_2, z_corner_2);
            Location faction_hq = new Location(Bukkit.getWorld("world"), x_hq, y_hq, z_hq);

            return new Faction(faction_id, name, dtr, faction_hq, faction_claim);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Faction getFactionFromPlayer(Player player) {
        try {
            String playerID = player.getUniqueId().toString();
            ResultSet result = SQLHelper.querydb("SELECT * FROM members WHERE player_uuid='" + playerID + "'");
            if (result.isBeforeFirst()) {
                return null;
            }

            String factionID = result.getString("faction_id");
            return getFactionFromID(factionID);


        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }


}
