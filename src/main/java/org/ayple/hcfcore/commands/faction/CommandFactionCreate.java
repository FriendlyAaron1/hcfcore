package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionCreate extends SubCommand {

    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "creates a faction";
    }

    @Override
    public String getSyntax() {
        return "/f create [name]";
    }

    @Override
    public boolean perform(Player player, String[] args) {
        try {
            if (args.length > 1) {
                if (args[1].length() > 20) {
                    player.sendMessage("Name is too long. Must be shorter than 20 characters!");
                    return false;
                }

                if (FactionManager.playerInFaction(player.getUniqueId())) {
                    player.sendMessage("Already in a faction!");
                    return false;
                }

                if (FactionManager.newFaction(args[1], player)) {
                    player.sendMessage("Created faction!");
                    return true;
                }

                player.sendMessage("Faction name is already taken!");
                return false;
            }
        } catch (SQLException e) {
            player.sendMessage("MYSQL error in faction");
            e.printStackTrace();
        }

        return false;

    }
}
