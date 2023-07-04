package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.helpers.PlayerHelpers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTP implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (!commandSender.hasPermission("hcfcore.admin")) return true;

        Player player = PlayerHelpers.GetPlayerInstanceFromCommand(commandSender);
        if (player == null) return false;
        if (args.length == 1) return false;


        Player player1 = Bukkit.getPlayer(args[1]);
        Location player1_pos = player1.getLocation();

        if (args.length == 3) {
            Player player2 = Bukkit.getPlayer(args[2]);
            Location player2_pos = player2.getLocation();

            player1.teleport(player2_pos);
        }

        player.teleport(player1_pos);
        return true;
    }
}
