package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

public class PlayerUseChatEvent implements Listener {
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        final Player p = event.getPlayer();
        final String msg = event.getMessage();

        Faction faction = NewFactionManager.getFactionFromPlayerID(p.getUniqueId());
        if (faction == null) {
            Bukkit.broadcastMessage(p.getDisplayName() + " " + msg);
            return;
        }


        Bukkit.broadcastMessage(ChatColor.YELLOW + "[" + faction.getFactionName() + "] " + ChatColor.WHITE + p.getDisplayName() + " " + msg);
        event.setCancelled(true);
    }

}
