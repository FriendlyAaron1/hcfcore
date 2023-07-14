package org.ayple.hcfcore.core.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

public class NewFactionManager {

    // faction id to faction object
    // i decided to map it to the faction ID
    // since it'll be easier to work with
    private final static Hashtable<UUID,  Faction> factions = new Hashtable<UUID,  Faction>();
    public static ArrayList<Faction> getFactions() {
        return new ArrayList<Faction>(factions.values());
    }

    public static void loadFactions() throws SQLException {
        factions.clear();

        String sql = "SELECT * FROM factions";
        HcfSqlConnection conn = new HcfSqlConnection();
        ResultSet results = conn.getConnection().prepareStatement(sql).executeQuery();

        while (results.next()) {
            UUID id = UUID.fromString(results.getString("id"));
            String faction_name = results.getString("faction_name");
            float dtr = results.getFloat("dtr");
            String ally_faction_id = results.getString("ally_faction_id");
            int faction_hq_x = results.getInt("faction_hq_x");
            int faction_hq_y = results.getInt("faction_hq_y");
            int faction_hq_z = results.getInt("faction_hq_z");
            int corner_1_x = results.getInt("corner_1_x");
            int corner_1_z = results.getInt("corner_1_z");
            int corner_2_x = results.getInt("corner_2_x");
            int corner_2_z = results.getInt("corner_2_z");
            int bal = results.getInt("faction_bal");
            Claim claim = null;
            Location hq = null;

            if (faction_hq_x != 0 && faction_hq_y != 0 && faction_hq_z != 0) {
                hq = new Location(Bukkit.getWorld("world"), faction_hq_x, faction_hq_y, faction_hq_z);
            }

            if (corner_1_x != 0 && corner_1_z != 0 && corner_2_x != 0 && corner_2_z != 0) {
                claim = new Claim(id, faction_name, corner_1_x, corner_1_z, corner_2_x, corner_2_z);
            }

            factions.put(id, new Faction(id, faction_name, dtr, bal, hq, claim));
        }




        conn.closeConnection();
    }

    public static boolean playerInFaction(UUID player_id) {
        for (Faction faction : factions.values()) {
            if (faction.getFactionMembers().containsKey(player_id)) {
                return true;
            }
        }

        return false;
    }

    public static Faction getFactionFromPlayerID(UUID player_id) {
        for (Faction faction : factions.values()) {
            if (faction.getFactionMembers().containsKey(player_id)) {
                return faction;
            }
        }

        return null;
    }

    public static Faction getFaction(UUID faction_id) {
        for (Faction faction : factions.values()) {
            if (faction.getFactionID() == faction_id) {
                return faction;
            }
        }

        return null;
    }

    public static Faction getFaction(String faction_name) {
        for (Faction faction : factions.values()) {
            if (faction.getFactionName() == faction_name) {
                return faction;
            }
        }

        return null;
    }


    // honestly this is completely irrelevant we would
    // need to check if its null afterward anyways lmao
    public static UUID getFactionIDFromName(String faction_name) {
        Faction faction = getFaction(faction_name);

        if (faction == null) {
            return null;
        }

        return faction.getFactionID();
    }

    public static boolean createNewFaction(String faction_name, Player leader) {
        AtomicBoolean sql_had_error = new AtomicBoolean(false);
        if (getFaction(faction_name) != null) {
            return false;
        }

        UUID faction_id = UUID.randomUUID();

        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {

            try {
                String sql = "INSERT INTO factions (faction_id, faction_name) VALUES (?, ?)";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, faction_name);
                statement.executeUpdate();
                System.out.println(statement.toString());
                conn.closeConnection();

                sql = "INSERT INTO faction_members VALUES (?, ?, 3)";
                conn = new HcfSqlConnection();
                statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, faction_id.toString());
                statement.setString(2, leader.getUniqueId().toString());
                statement.executeUpdate();
                System.out.println(statement.toString());
                conn.closeConnection();

            } catch (SQLException e) {
                e.printStackTrace();
                sql_had_error.set(true);
            }
        });

        factions.put(faction_id, new Faction(faction_id, faction_name, 1.01f, 0, null, null));
        return !sql_had_error.get();
    }

    public static void disbandFaction(UUID faction_id) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "DROP FROM factions WHERE id=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, faction_id.toString());
                System.out.println(statement.toString());
                statement.executeUpdate();
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();

            }
        });

        factions.remove(faction_id);
        ClaimsManager.removeClaim(faction_id);
//        ClaimsManager.reloadClaims(); // reloads all claims
    }

//    public static UUID getOwnerIDOfFaction(UUID faction_id) {
//
//    }


    public static void decreaseDTR(UUID faction_id) {
        Faction target_faction = factions.get(faction_id);
        if (target_faction == null) {
            System.out.println("cant find faction? [decreaseDTR]");
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE factions SET dtr = dtr - 1 WHERE id=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, faction_id.toString());
                statement.executeUpdate();

                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        target_faction.setFactionDTR(target_faction.getFactionDTR() - 1f);
    }

    public static void kickPlayerFromFaction(Faction faction, OfflinePlayer player) {
        UUID target_player_id = player.getUniqueId();


        if (faction.removeFactionMember(target_player_id)) {
            Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
                try {
                    String sql = "DELETE FROM faction_members WHERE player_uuid=?";

                    HcfSqlConnection conn = new HcfSqlConnection();
                    PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                    statement.setString(1, target_player_id.toString());
                    statement.executeUpdate();
                    conn.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });
        }

    }

    public static void promotePlayer(Faction faction, UUID player_id) {

        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE faction_members SET rank= rank + 1 WHERE player_uuid=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, player_id.toString());
                statement.executeUpdate();
                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        int current_rank = faction.getFactionMembers().get(player_id);
        faction.changeFactionMemberRank(player_id, current_rank + 1);
    }


    public static boolean isPlayerLeader(UUID player_id) {
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

    public static boolean factionExists(UUID faction_id) {
        return factions.get(faction_id) != null;
    }

    public static boolean factionExists(String faction_name) {
        for (Faction faction : factions.values()) {
            if (faction.getFactionName() == faction_name) {
                return true;

            }
        }

        return false;
    }

    public static void renameFaction(String old_name, String new_name) {
        Faction faction = getFaction(old_name);
        if (faction == null) {
            System.out.println("Failed to find the faction? [renameFaction]");
            return;
        }

        faction.setFactionName(new_name);

        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE factions SET faction_name = ? WHERE id=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(0, new_name);
                statement.setString(1, faction.getFactionID().toString());
                statement.executeUpdate();

                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });


    }

    public static void setFactionHQ(UUID faction_id, Location location) {
        Faction faction = getFaction(faction_id);

        if (faction == null) {
            System.out.println("Failed to send message!");
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                String sql = "UPDATE factions SET faction_hq_x = ?, faction_hq_y, = ?, faction_hq_z = ? WHERE id=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setInt(0, (int) location.getX());
                statement.setInt(1, (int) location.getY());
                statement.setInt(2, (int) location.getZ());
                statement.setString(3, faction.getFactionID().toString());
                statement.executeUpdate();

                conn.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });

        faction.setFactionHQ(location);

    }

}

