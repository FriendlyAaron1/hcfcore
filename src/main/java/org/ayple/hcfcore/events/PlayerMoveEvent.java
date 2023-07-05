package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerMoveEvent implements Listener {
    @EventHandler
    public void onPlayerMove(org.bukkit.event.player.PlayerMoveEvent event) {
        Player p = event.getPlayer();

        // cancel home timer if they move
        if (CooldownManager.hasHomeTimer(p.getUniqueId())) {
            CooldownManager.cancelHomeTimer(p.getUniqueId());
        }

        if (CooldownManager.hasLogoutTimer(p.getUniqueId())) {
            CooldownManager.cancelLogoutTimer(p.getUniqueId());
        }




        Faction faction_claim = ClaimsManager.playerInClaim(p);
        if (faction_claim == null) return;
        onPlayerEnterClaim(p, faction_claim);


    }

    public static void onPlayerEnterClaim(Player player, Faction faction) {
        player.sendMessage("Entered " + faction.getFactionName() + " claim!");
    }
}
