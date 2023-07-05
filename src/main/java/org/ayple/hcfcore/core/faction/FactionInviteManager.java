package org.ayple.hcfcore.core.faction;

import jdk.internal.util.xml.impl.Pair;
import org.ayple.hcfcore.helpers.HcfSqlConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;

public class FactionInviteManager {
    public static HashSet<FactionInvite> invites = new HashSet<FactionInvite>();

    public static void registerNewInvite(FactionInvite invite) {
        invites.add(invite);
    }


    public static void onPlayerJoinFaction(FactionInvite invite) throws SQLException {
        if (!lookForInvite(invite)) {
            System.out.println("Error looking for invite in 'onPlayerJoinFaction'");
            return;
        }


        String sql = "INSERT INTO FACTION_MEMBERS (faction_id, player_uuid) VALUES (?, ?)";
        HcfSqlConnection conn = new HcfSqlConnection();
        PreparedStatement statement = conn.getConnection().prepareStatement(sql);
        statement.setString(1, invite.faction_id.toString());
        statement.setString(2, invite.player_id.toString());
        statement.executeUpdate();
        conn.closeConnection();
        invites.remove(invite);


    }

    public static boolean lookForInvite(FactionInvite invite) {
        return invites.contains(invite);
    }
}
