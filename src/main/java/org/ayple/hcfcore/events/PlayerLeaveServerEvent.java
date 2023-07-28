package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveServerEvent implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Hcfcore.getInstance().getScoreboardHandler().onPlayerQuitServer(event.getPlayer());

       // SelectionsManager.clearAnySelectionPlayerHas(p);

    }
}
