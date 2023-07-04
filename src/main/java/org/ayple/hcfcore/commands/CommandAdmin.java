package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.helpers.PlayerHelpers;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

public class CommandAdmin implements CommandExecutor {
    private static Hashtable<UUID, Inventory> inventories;

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player player = PlayerHelpers.GetPlayerInstanceFromCommand(commandSender);
        if (player == null) return true;

        if (commandSender.hasPermission("hcfcore.admin")) {
            if (playerInAdminMode(player)) {
                disableAdminMode(player);
                return true;
            }

            enableAdminMode(player);
            return true;

        } else {
            player.sendMessage("No permission!");
        }

        return true;
    }

    private static void enableAdminMode(Player player) {
        Inventory normal_inventory = player.getInventory();
        inventories.put(player.getUniqueId(), normal_inventory);
        player.setInvulnerable(true);
        player.setGameMode(GameMode.CREATIVE);
    }

    private static void disableAdminMode(Player player) {
        if (inventories.containsKey(player.getUniqueId())) {
            Inventory normal_inventory = inventories.get(player.getUniqueId());
            player.getInventory().setContents(normal_inventory.getContents());
        }

        player.setInvulnerable(false);
        player.setGameMode(GameMode.SURVIVAL);
    }

    // TODO: do more here
    public static boolean playerInAdminMode(Player player) {
        return player.getGameMode() == GameMode.CREATIVE;
    }

}
