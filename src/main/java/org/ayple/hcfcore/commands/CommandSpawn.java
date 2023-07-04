package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.helpers.PlayerHelpers;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class CommandSpawn implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (!(commandSender instanceof Player)) return false;
        if (!commandSender.hasPermission("hcfcore.admin")) return false;

        Player player = PlayerHelpers.GetPlayerInstanceFromCommand(commandSender);
        Location world_spawn = Objects.requireNonNull(Bukkit.getWorld("world")).getSpawnLocation();
        player.teleport(world_spawn);

        return false;
    }
}
