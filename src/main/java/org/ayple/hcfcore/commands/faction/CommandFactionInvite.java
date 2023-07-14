package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionInviteManager;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
        if (args.length > 1) {
            Player target_player = Bukkit.getPlayer(args[1]);
            if (target_player == null) {
                player.sendMessage("That player is not online!");
                return;
            }

            Faction player_faction = NewFactionManager.getFactionFromPlayerID(player.getUniqueId());

            if (player_faction == null) {
                player.sendMessage("You are not in a faction!");
                return;
            }

            if (FactionInviteManager.registerNewInvite(player_faction.getFactionID(),  target_player.getUniqueId())) {
                if (target_player.isOnline()) {
                    target_player.sendMessage(player_faction.getFactionName() + " has invited you!");
                }

                player.sendMessage("Invited " + target_player.getDisplayName() + " to the faction!");
                return;
            }

            player.sendMessage(ChatColor.RED + "Failed to invite! consult developer.");

        }
    }
}

