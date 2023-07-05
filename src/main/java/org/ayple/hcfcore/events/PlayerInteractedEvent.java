package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.Objects;

public class PlayerInteractedEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
//        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
//            Material item_player_holding = Objects.requireNonNull(event.getPlayer().getItemInUse().getType());
//            Material clicked_block = event.getClickedBlock().getType();
//
//            if (item_player_holding.equals(Material.ENDER_PEARL)) {
//                //if (event.getClickedBlock())
//            }
//        }

        Player player = event.getPlayer();

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (player.getItemInUse().getType().equals(Material.ENDER_PEARL)) {
                if (!CooldownManager.hasEnderpearlCooldown(player.getUniqueId())) {
                    CooldownManager.registerEnderpearlCooldown(player.getUniqueId());
                } else {
                    player.sendMessage("You currently have an enderpearl timer!");
                }

            }

            if (event.getClickedBlock() != null) {
                if (!ClaimsManager.playerInClaim(player).equals(null)) { // if player in claim
                    if (!ClaimsManager.playerOwnsClaim(player)) {
                        player.sendMessage("You can't interact in other peoples claims!");
                        event.setCancelled(true);
                    }
                }
            }
        }




    }

}
