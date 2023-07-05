package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionInvite;
import org.ayple.hcfcore.core.faction.FactionInviteManager;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionInvite extends SubCommand {

    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "invite a player to the faction";
    }

    @Override
    public String getSyntax() {
        return "/f invite [player name]";
    }

    @Override
    public void perform(Player player, String[] args) {
        try {
            if (args.length > 2) {
                Player target_player = Bukkit.getPlayer(args[2]);
                Faction player_faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());

                if (player_faction == null) {
                    player.sendMessage("You are not in a faction!");
                    return;

                }

                FactionInviteManager.registerNewInvite(new FactionInvite(player_faction.getFactionID(),  target_player.getUniqueId()));
                if (target_player.isOnline()) {
                    target_player.sendMessage(player_faction.getFactionName() + " has invited you!");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR");
        }

    }
}
