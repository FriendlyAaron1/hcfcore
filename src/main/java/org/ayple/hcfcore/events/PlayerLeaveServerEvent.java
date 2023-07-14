package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.claims.SelectionsManager;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveServerEvent implements Listener {
    @EventHandler
    public void onPlayerExit(PlayerQuitEvent event) {
        Player p = event.getPlayer();



       // SelectionsManager.clearAnySelectionPlayerHas(p);

    }
}
