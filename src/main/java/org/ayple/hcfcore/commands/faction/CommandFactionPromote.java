package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CommandFactionPromote extends SubCommand {
    @Override
    public String getName() {
        return "promote";
    }

    @Override
    public String getDescription() {
        return "promote a faction member";
    }

    @Override
    public String getSyntax() {
        return "/f promote [player name]";
    }

    @Override
    public boolean perform(Player player, String[] args) {
        try {
            if (args.length < 2) return false;
            if (!FactionManager.isPlayerLeader(player.getUniqueId())) {
                player.sendMessage("You have to be leader to promote!");
                return true;
            }


            UUID target_player_id = Bukkit.getOfflinePlayer(args[1]).getUniqueId();
            Faction player_faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (player_faction == null) {
                player.sendMessage("You are not in a faction!");
                return true;
            }

            Faction target_player_faction = FactionManager.getFactionFromPlayerID(target_player_id);
            if (target_player_faction == null) {
                player.sendMessage("That player isn't in your faction!");
                return true;
            }

            if (player_faction.getFactionID() != target_player_faction.getFactionID()) {
                player.sendMessage("That player isn't in your faction!");
                return true;
            }

            if (player_faction.getFactionMembers().get(target_player_id) == 2) {
                player.sendMessage("Player cant be promoted any higher!");
                return true;
            }

            String sql = "UPDATE faction_members SET rank= rank + 1 WHERE player_uuid=?";
            HcfSqlConnection conn = new HcfSqlConnection();
            PreparedStatement statement = conn.getConnection().prepareStatement(sql);
            statement.setString(1, target_player_id.toString());
            statement.executeUpdate();
            conn.closeConnection();

            player.sendMessage("Promoted");

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR");
        }

        return false;
    }
}
