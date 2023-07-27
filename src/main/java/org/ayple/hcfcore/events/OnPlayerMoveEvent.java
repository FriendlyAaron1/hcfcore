package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.cooldowns.oldcooldowns.CooldownManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class OnPlayerMoveEvent implements Listener {
//    public Hashtable<UUID, Location> player_last_location = new Hashtable<UUID, Location>();

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = false)
    public void onPlayerMove(PlayerMoveEvent event) {
        if (event == null || event.getPlayer() == null) return;
        Player p = event.getPlayer();

//        Location current_loc = p.getLocation();

//        p.getWorld() != Bukkit.getWorld("world")
        if (event.getFrom().getX() == event.getTo().getX() && event.getFrom().getZ() == event.getTo().getZ()) return;

//        if (player_last_location.containsKey(p.getUniqueId())) {
//            Location last_loc = player_last_location.get(p.getUniqueId());
//            if (last_loc.getX() == current_loc.getX() && last_loc.getZ() == current_loc.getZ()) {
//                return;
//            }
//        }

//        player_last_location.put(p.getUniqueId(), p.getLocation());


        // cancel home timer if they move
        if (CooldownManager.hasHomeTimer(p.getUniqueId())) {
            CooldownManager.cancelHomeTimer(p);
        }

        if (CooldownManager.hasLogoutTimer(p)) {
            CooldownManager.cancelLogoutTimer(p);
        }




        ClaimsManager.relayPlayerInClaim(p);

    }

}
