package org.ayple.hcfcore.core.claims;

import org.ayple.hcfcore.core.Cuboid;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.reflect.Array;
import java.sql.*;
import java.util.*;

public class ClaimsManager {

    private static final String WILDERNESS = "wilderness";
    private static HashSet<Claim> claims = new HashSet<Claim>();

    // This is used to know what claims players go in and out of. This is done to
    // prevent spam of `playerInClaim`
    // first UUID is player id, second is claim id
    private static HashMap<UUID, UUID> player_location_claims = new HashMap<UUID, UUID>();

    // this should be called if server is reset
    // gets all the stored claims data and turns it into claim objects
    public static void loadClaims() throws SQLException {
        String sql = "SELECT id, faction_name, corner_1_x, corner_1_z, corner_2_x, corner_2_z FROM factions";
        HcfSqlConnection conn = new HcfSqlConnection();
        Statement statement = conn.getConnection().createStatement();
        statement.executeQuery(sql);

        ResultSet results = statement.getResultSet();
        while (results.next()) {
            UUID owner_uuid = UUID.fromString(results.getString("id"));
            Claim new_claim = new Claim(
                    owner_uuid,
                    results.getString("faction_name"),
                    results.getInt("corner_1_x"),
                    results.getInt("corner_1_z"),
                    results.getInt("corner_2_x"),
                    results.getInt("corner_2_z")
            );

            claims.add(new_claim);
        }

        conn.closeConnection();
        System.out.println(claims);

    }

    public static ArrayList<Cuboid> getAllClaimCuboids() {
        ArrayList<Cuboid> claims_cuboids = new ArrayList<Cuboid>();
        for (Claim claim : claims) {
            claims_cuboids.add(claim.getCuboid());
        }

        return claims_cuboids;

    }


    // unused function, this already happens lmao - AyPle
    public static void saveClaimsToDisk() {

    }

    public static void newClaim(Player player, Selection selection) throws SQLException {
        String sql = "UPDATE factions SET corner_1_x=?, corner_1_z=?, corner_2_x=?, corner_2_z=? WHERE id=?";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);

        Faction faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
        if (faction == null) {
            System.out.println("Failed to get faction from player id in newClaim!");
            conn.closeConnection();
            return;
        }

        UUID faction_id = faction.getFactionID();
        if (faction_id == null) {
            player.sendMessage("SQLERROR IN `ClaimsManager.newClaim`");
            conn.closeConnection();
            return;
        }

        statement.setInt(1, (int) Math.round(selection.getPos1().getX()));
        statement.setInt(2, (int) Math.round(selection.getPos1().getZ()));
        statement.setInt(3, (int) Math.round(selection.getPos2().getX()));
        statement.setInt(4, (int) Math.round(selection.getPos2().getZ()));
        statement.setString(5, faction_id.toString());

        statement.executeUpdate();


        conn.closeConnection();
        claims.add(new Claim(faction_id, faction.getFactionName(), selection.getPos1(), selection.getPos2()));
    }

    // TODO: optimize this jesus fucking christ almighty 10/07/23
    public static void relayPlayerInClaim(Player player) {
        UUID player_id = player.getUniqueId();

        // if the player hasn't been registered just put in the player and set it to null
        if (!player_location_claims.containsKey(player_id)) {
            // https://stackoverflow.com/questions/29059530/is-there-any-way-to-generate-the-same-uuid-from-a-string
            player_location_claims.put(player_id, UUID.nameUUIDFromBytes(WILDERNESS.getBytes()));
        }

        UUID stored_claim_id = player_location_claims.get(player_id);

        for (Claim claim : claims) {
            if (claim.getCuboid().contains(player.getLocation())) {
                UUID claim_id = claim.getOwnerFactionID();

                // players moved into new claim
                if (claim_id != stored_claim_id) {
                    player.sendMessage("Now entering " + claim.getFactionName() + "'s claim!");
                }

                player_location_claims.put(player_id, claim_id);
                return;
            }

            // set it to wilderness if not in claim
            player_location_claims.put(player_id, UUID.nameUUIDFromBytes(WILDERNESS.getBytes()));
        }

        if (!Objects.equals(stored_claim_id, UUID.nameUUIDFromBytes(WILDERNESS.getBytes()))) {
            player.sendMessage("Now entering Wilderness!");
        }


    }

    public static Faction playerInClaim(Player player) {
        try {
            for (Claim claim : claims) {
                if (claim.getCuboid().contains(player.getLocation())) {
                    return FactionManager.getFaction(claim.getOwnerFactionID());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR CHECKING CLAIMS! [playerInClaim] function");
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

        return faction.getFactionMembers().get(player.getUniqueId()) != null;
    }

    public static boolean blockInClaim(Location pos) {
        for (Cuboid claim : getAllClaimCuboids()) {
            if (claim.contains(pos)) return true;
        }

        return false;
    }


    public static boolean isClaimSizeLegal(Cuboid claim) {
        Location corner_1 = new Location(claim.getWorld(), claim.getLowerNE().getX(), 0, claim.getLowerNE().getZ());
        Location corner_2 = new Location(claim.getWorld(), claim.getUpperSW().getX(), 0, claim.getUpperSW().getZ());
        Cuboid flat_surface = new Cuboid(corner_1, corner_2);
        return flat_surface.getVolume() >= 25;

    }


}
