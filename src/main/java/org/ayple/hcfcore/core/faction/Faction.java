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
import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Faction {

    public static final DecimalFormat DTR_FORMAT = new DecimalFormat("0.00");


    private final UUID factionID;
    public UUID getFactionID() { return this.factionID; }

    private final String factionName;
    public String getFactionName() { return this.factionName; }

    private final Location factionHQ;
    public Location getFactionHQ() { return this.factionHQ; }
    public String getFactionHQAsString() { // used to display in /f who
        if (this.factionHQ == null) {
            return "null";
        }

        String x = Double.toString(this.factionHQ.getX());
        String y = Double.toString(this.factionHQ.getY());
        String z = Double.toString(this.factionHQ.getZ());
        return "X: " + x + " Y: " + y + " Z: " + z;
    }

    private Claim claim;
    private float dtr;
    public float getFactionDTR() { return this.dtr; }

    private int bal;
    public Integer getFactionBal() { return this.bal; }

    private Hashtable<UUID, Integer> factionMembers = new Hashtable<UUID, Integer>();
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


        // rank has to have (`) otherwise it causes issues. apprently rank is an
        // sql statment.
        // TODO: replace rank with player_rank
        String sql = "SELECT player_uuid, `rank` FROM faction_members WHERE faction_id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, faction_id.toString());
        statement.executeQuery();

        ResultSet results = statement.getResultSet();

        System.out.println(statement.toString());

        while (results.next()) {
            factionMembers.put(
                UUID.fromString(results.getString("player_uuid")),
                results.getInt("rank")
            );
        }

        conn.closeConnection();

    }

    public int getFactionMembersSize() {
        return this.getFactionMembers().size();
    }

    public int getOnlineMembers() {
        int size = 0;

        for (UUID target_id : this.getFactionMembers().keySet()) {
            OfflinePlayer target_player = Bukkit.getOfflinePlayer(target_id);
            if (target_player.isOnline()) {
                size = size + 1;
            }
        }

        return size;
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
