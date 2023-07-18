package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.core.scoreboard.ServerScoreboard;
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
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        player.setScoreboard(ServerScoreboard.newScoreboard());

        if (!PlayerDataHandler.playerLoggedInBefore(player.getUniqueId())) {
            PlayerDataHandler.onLoginFirstTime(player.getUniqueId());
            giveStarterItems(player);
            CooldownManager.registerPvpTimer(player);
        }

    }

    private void giveStarterItems(Player player) {
        ItemStack steak = new ItemStack(Material.COOKED_BEEF);
        ItemStack fishing_rod = new ItemStack(Material.FISHING_ROD);
        fishing_rod.addEnchantment(Enchantment.LURE, 2);
        steak.setAmount(32);
        player.getInventory().addItem(steak);
        player.getInventory().addItem(fishing_rod);
        player.sendMessage(ChatColor.GOLD + "Welcome to the server for the first time!");
    }


}
