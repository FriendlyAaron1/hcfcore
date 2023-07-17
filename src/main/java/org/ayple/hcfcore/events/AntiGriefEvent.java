package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.faction.Faction;
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

public class AntiGriefEvent implements Listener {
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Faction claim_owner = ClaimsManager.playerInClaim(player);
        if (event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getClickedBlock() != null) {
                if (claim_owner != null) { // if player in claim
//                  if (claim_owner.getClaim().getOwnerFactionID())
                    if (!ClaimsManager.playerOwnsClaim(player) && claim_owner.getFactionDTR() > 0) {
                        player.sendMessage("You can't interact in other peoples claims!");
                        event.setCancelled(true);
                    }
                }
            }
        }




    }

}
