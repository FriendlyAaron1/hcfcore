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
    public void perform(Player player, String[] args) {
        try {
            if (args.length > 2) {
                if (FactionManager.newFaction(args[2], player)) {
                    player.sendMessage("Created faction!");
                    return;
                }

                player.sendMessage("Faction name is already taken!");
            }
        } catch (SQLException e) {
            player.sendMessage("MYSQL error in faction");
            e.printStackTrace();
        }

    }
}
