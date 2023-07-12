package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;

public class CommandFactionKick extends SubCommand {
    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "kick a player from the faction";
    }

    @Override
    public String getSyntax() {
        return "/f kick [player name]";
    }

    @Override
    public boolean perform(Player player, String[] args) {
        try {
            if (args.length <= 1) return false;

            Faction player_faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (player_faction == null ) {
                player.sendMessage("You are not in a faction!");
                return true;
            }

            if (!(FactionManager.isPlayerLeader(player_faction, player.getUniqueId()) || FactionManager.isPlayerCoLeader(player_faction, player.getUniqueId()))) {
                player.sendMessage("You are not a leader or coleader!");
                return true;
            }

            OfflinePlayer target_player = Bukkit.getOfflinePlayer(args[1]);
            UUID target_player_id = target_player.getUniqueId();


            String sql = "DELETE FROM faction_members WHERE player_uuid=?";

            HcfSqlConnection conn = new HcfSqlConnection();
            PreparedStatement statement = conn.getConnection().prepareStatement(sql);
            statement.setString(1, target_player_id.toString());
            statement.executeUpdate();
            conn.closeConnection();

            player.sendMessage("Kicked " + target_player.getName());

            if (target_player.isOnline()) {
                target_player.getPlayer().sendMessage("You have been kicked from " + player_faction.getFactionName());
            }
        } catch (Exception e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR!");
        }

        return true;
    }

}
