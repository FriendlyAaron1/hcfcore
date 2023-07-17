package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.claims.serverclaim.SpawnClaim;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import java.sql.SQLException;

// e.getEntity() instanceof Player && e.getDamager() instanceof Player
public class PlayerHitEvent implements Listener {
    @EventHandler
    public void onPlayerAttacked(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player whoWasHit = (Player) event.getEntity();
            Player whoHit = (Player) event.getDamager();


            Faction faction = ClaimsManager.playerInClaim(whoWasHit.getPlayer());
            if (faction != null && faction.getFactionID() == SpawnClaim.SPAWN_UUID) {
                whoHit.sendMessage(ChatColor.GREEN + "Cannot hurt other players in spawn!");
                event.setCancelled(true);
                return;
            }

            if (checkSameFaction(whoWasHit, whoHit)) {
                whoHit.sendMessage(ChatColor.RED + "Cannot hurt " + ChatColor.GREEN + whoWasHit.getDisplayName() + ChatColor.RED + "!");
                event.setCancelled(true);
                return;
            }


            CooldownManager.registerCombatTimer(whoHit.getPlayer());
            CooldownManager.registerCombatTimer(whoWasHit.getPlayer());

            // cancel home timers
            if (CooldownManager.hasHomeTimer(whoWasHit.getUniqueId()))
                    CooldownManager.cancelHomeTimer(whoWasHit);

            if (CooldownManager.hasHomeTimer(whoHit.getUniqueId()))
                CooldownManager.cancelHomeTimer(whoHit);

            // cancel logout timers
            if (CooldownManager.hasLogoutTimer(whoWasHit))
                CooldownManager.cancelLogoutTimer(whoWasHit);

            if (CooldownManager.hasLogoutTimer(whoHit))
                CooldownManager.cancelLogoutTimer(whoHit);
        }
    }

    private boolean checkSameFaction(Player whoWasHit, Player whoHit) {
        Faction who_was_hit_faction = NewFactionManager.getFactionFromPlayerID(whoWasHit.getUniqueId());
        Faction who_hit_faction = NewFactionManager.getFactionFromPlayerID(whoHit.getUniqueId());

        if (who_was_hit_faction == null || who_hit_faction == null) {
            return false;
        }

        return who_hit_faction.getFactionID() == who_was_hit_faction.getFactionID();
    }
}
