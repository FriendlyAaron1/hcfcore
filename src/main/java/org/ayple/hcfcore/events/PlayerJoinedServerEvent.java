package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.scoreboard.PlayerBoard;
import org.ayple.hcfcore.core.scoreboard.ServerScoreboard;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerJoinedServerEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();





        if (!PlayerDataHandler.playerLoggedInBefore(player.getUniqueId())) {
            PlayerDataHandler.onLoginFirstTime(player.getUniqueId());
            giveStarterItems(player);
            CooldownManager.registerPvpTimer(player);
        }

//        if (CooldownManager.playerHasPvpTimer(player)) {
//            player.sendMessage(ChatColor.GREEN + "Detected pvp timer, adding it to scoreboard!");
//            CooldownManager.showPvpTimer(player);
//        }

        Hcfcore.getInstance().getScoreboardHandler().onPlayerJoinServer(player);
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
