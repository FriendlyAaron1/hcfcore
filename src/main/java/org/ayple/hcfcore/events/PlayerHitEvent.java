package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

// e.getEntity() instanceof Player && e.getDamager() instanceof Player
public class PlayerHitEvent implements Listener {
    @EventHandler
    public void onPlayerAttacked(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player && event.getDamager() instanceof Player) {
            Player whoWasHit = (Player) event.getEntity();
            Player whoHit = (Player) event.getDamager();


            CooldownManager.registerCombatTimer(whoHit.getPlayer());
            CooldownManager.registerCombatTimer(whoWasHit.getPlayer());

            // cancel home timers
            if (CooldownManager.hasHomeTimer(whoWasHit.getUniqueId()))
                    CooldownManager.cancelHomeTimer(whoWasHit.getUniqueId());

            if (CooldownManager.hasHomeTimer(whoHit.getUniqueId()))
                CooldownManager.cancelHomeTimer(whoHit.getUniqueId());

            // cancel logout timers
            if (CooldownManager.hasLogoutTimer(whoWasHit.getUniqueId()))
                CooldownManager.cancelLogoutTimer(whoWasHit.getUniqueId());

            if (CooldownManager.hasLogoutTimer(whoHit.getUniqueId()))
                CooldownManager.cancelLogoutTimer(whoHit.getUniqueId());
        }
    }
}
