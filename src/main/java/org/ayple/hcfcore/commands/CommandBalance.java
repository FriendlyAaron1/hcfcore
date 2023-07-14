package org.ayple.hcfcore.commands;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.BalanceHandler;
import org.ayple.hcfcore.helpers.PlayerHelpers;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandBalance implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
            try {
                if (sender instanceof Player) {
                    Player player = (Player) sender;
                    int bal = BalanceHandler.getPlayerBalance(player);
                    player.sendMessage("Balance: " + Integer.toString(bal));
                }


            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQL ERROR GETTING COMMAND BALANCE. CONSULT DEVELOPER");
            }
        });

        return true;
    }
}
