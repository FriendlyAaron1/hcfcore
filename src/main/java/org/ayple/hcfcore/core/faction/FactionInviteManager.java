package org.ayple.hcfcore.core.faction;

import org.ayple.hcfcore.helpers.HcfSqlConnection;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

public class FactionInviteManager {
    // faction id, set of player ids
    public static final ArrayList<FactionInvite> invites = new ArrayList<FactionInvite>();

    public static void registerNewInvite(FactionInvite invite) {
        invites.add(invite);
    }

    public static void registerNewInvite(UUID faction_id, UUID target_player_id) {
        invites.add(new FactionInvite(faction_id, target_player_id));
        System.out.println("Registered new invite! " + faction_id + "[!:!] " + target_player_id);
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
        remove(invite);


    }

    private static void remove(FactionInvite invite) {
        for (int i = 0; i < invites.size(); i++) {
            if (invite.faction_id == invites.get(i).player_id && invite.faction_id == invites.get(i).faction_id) {
                invites.remove(i);
            }
        }
    }

    public static boolean lookForInvite(FactionInvite provided_invite) {
        for (FactionInvite invite : invites) {
            System.out.println(invite.player_id);
            System.out.println(provided_invite.player_id);
            System.out.println(invite.faction_id);
            System.out.println(provided_invite.faction_id);
            if (invite.player_id.equals(provided_invite.player_id) && invite.faction_id.equals(provided_invite.faction_id)) {
                System.out.println("GOT HERE HSGUHGSI)JGIS)UG");
                return true;
            }
        }

        return false;
    }

    public static boolean lookForInvite(UUID player_id, UUID faction_id) {
        for (FactionInvite invite : invites) {
            if (invite.faction_id == player_id && invite.faction_id == faction_id ) {
                return true;
            }
        }

        return false;
    }
}
