package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.kits.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandKit implements CommandExecutor {



    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) return true;
        Player player = (Player) sender;

        if (Hcfcore.getInstance().KITMAP_MODE) {
            player.sendMessage("Get kits from spawn!");
            return true;
        }

        if (args.length == 0) {
            player.sendMessage("Kit gui coming oon!");
            return true;
        }

        switch (args[0]) {
            case "diamond":
                if (player.hasPermission("hcf.kit.diamond")) {
                    KitManager.diamondkit.givePlayerKit(player);
                    break;
                }

                player.sendMessage(ChatColor.RED + "You don't have permission!");
                break;
            case "miner":
                if (player.hasPermission("hcf.kit.miner")) {
                    KitManager.minerkit.givePlayerKit(player);
                    break;
                }

                player.sendMessage(ChatColor.RED + "You don't have permission!");
                break;
        }



        return true;
    }


}