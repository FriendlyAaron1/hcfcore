package org.ayple.hcfcore.events;

import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerJoinedServerEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();

        try {
            if (!PlayerDataHandler.playerLoggedInBefore(player.getUniqueId())) {
                PlayerDataHandler.onLoginFirstTime(player.getUniqueId());
                giveStarterItems(player);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage(ChatColor.RED + "SQL ERROR!");
        }


    }

    private void giveStarterItems(Player player) {
        ItemStack steak = new ItemStack(Material.COOKED_BEEF);
        ItemStack fishing_rod = new ItemStack(Material.FISHING_ROD);
        fishing_rod.addEnchantment(Enchantment.LURE, 2);
        steak.setAmount(32);
        player.getInventory().addItem(steak);
        player.getInventory().addItem(fishing_rod);
        player.sendMessage("Welcome to the server for the first time!");
    }


}
