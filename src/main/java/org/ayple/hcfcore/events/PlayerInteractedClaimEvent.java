package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

//        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
//            Material item_player_holding = Objects.requireNonNull(event.getPlayer().getItemInUse().getType());
//            Material clicked_block = event.getClickedBlock().getType();
//
//            if (item_player_holding.equals(Material.ENDER_PEARL)) {
//                //if (event.getClickedBlock())
//            }
//        }

//            Material item_in_use = player.getItemInUse().getType();
//            if (item_in_use == null) {
//                return;
//            }
//
//            if (item_in_use.equals(Material.ENDER_PEARL)) {
//                if (!CooldownManager.hasEnderpearlCooldown(player.getUniqueId())) {
//                    CooldownManager.registerEnderpearlCooldown(player.getUniqueId());
//                } else {
//                    player.sendMessage("You currently have an enderpearl timer!");
//                }
//
//            }

public class PlayerInteractedClaimEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Faction player_faction = ClaimsManager.playerInClaim(player);
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null) {
                if (player_faction != null) { // if player in claim
                    if (!ClaimsManager.playerOwnsClaim(player) && player_faction.getFactionDTR() > 0) {
                        player.sendMessage("You can't interact in other peoples claims!");
                        event.setCancelled(true);
                    }
                }
            }
        }




    }

}
