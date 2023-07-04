package org.ayple.hcfcore.helpers;

import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerHelpers {

    public static Player GetPlayerInstanceFromCommand(CommandSender sender) {
        if (sender instanceof Player) {
            return (Player) sender;
        }

        return null;
    }


    public static boolean playerInFaction(Player player) {
        return true;
    }
}
