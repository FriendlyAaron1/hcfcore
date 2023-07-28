package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;


// TODO: create a proper combat logger
public class CombatLoggerEvent implements Listener {
    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {
        if (CooldownManager.hasCombatTimer(event.getPlayer().getUniqueId())) {
            event.getPlayer().setHealth(0);
        }
    }
}
