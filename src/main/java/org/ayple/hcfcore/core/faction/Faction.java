package org.ayple.hcfcore.core.faction;

import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Faction {
    private final UUID factionID;
    public UUID getFactionID() { return this.factionID; }

    private final String factionName;
    public String getFactionName() { return this.factionName; }

    private final Location factionHQ;
    public Location getFactionHQ() { return this.factionHQ; }

    private Claim claim;
    private float dtr;
    public float getFactionDTR() { return this.dtr; }

    private int bal;
    public Integer getFactionBal() { return this.bal; }

    private Hashtable<UUID, Integer> factionMembers;
    public Hashtable<UUID, Integer> getFactionMembers() { return this.factionMembers; }


    private HashSet<UUID> factionInvites;
    public HashSet<UUID> getFactionInvites() { return this.factionInvites; }


    public Faction(UUID faction_id, String faction_name, float dtr, int bal, Location faction_hq, Claim claim) throws SQLException {
        this.factionID = faction_id;
        this.factionName = faction_name;
        this.factionHQ = faction_hq;
        this.claim = claim;
        this.dtr = dtr;
        this.bal = bal;

        String sql = "SELECT player_uuid, rank FROM faction_members WHERE faction_id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_id.toString());
        statement.executeUpdate();

        ResultSet results = statement.getResultSet();

        while (results.next()) {
            factionMembers.put(
                UUID.fromString(results.getString("player_uuid")),
                results.getInt("rank")
            );
        }

        conn.closeConnection();

    }

//    public int getOnlineSize() {
//        return getOnlinePlayers().size();
//    }
//
//    public Set<Player> getOnlinePlayers() {
//        return factionMembers.stream()
//                .filter(OfflinePlayer::isOnline)
//                .map(p -> Bukkit.getPlayer(p.getUniqueId()))
//                .collect(Collectors.toSet());
//    }

}
