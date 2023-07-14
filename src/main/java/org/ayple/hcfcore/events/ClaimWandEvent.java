package org.ayple.hcfcore.events;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.claims.*;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.core.items.ClaimWand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.SQLException;


// TODO: fix pillars
public class ClaimWandEvent implements Listener {

    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        // only allow admins to claim in overworld (for koths and glowstone mountain)
        // is the player holding a claim wand
        // are they in a faction? if not then return
        if (player.getWorld() != Bukkit.getWorld("world") && !player.hasPermission("hcfcore.admin")) return;
        if (!ClaimWand.isItemClaimWand(player.getInventory().getItemInHand())) return;
        if (!checkPlayerInFaction(player)) return;

        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
            System.out.println("left clicked floor with claim wand!");

            Location pos = event.getClickedBlock().getLocation();
            if (ClaimsManager.blockInClaim(pos)) {
                player.sendMessage("This block is already claimed!");
                return;
            }

            ClaimPillarManager.addCorner1(player, (int) pos.getX(), (int) pos.getZ());
            player.sendMessage("Set first position!");
            event.setCancelled(true);

        }

        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            System.out.println("right clicked floor with claim wand!");

            Location pos = event.getClickedBlock().getLocation();
            if (ClaimsManager.blockInClaim(pos)) {
                player.sendMessage("This block is already claimed!");
                return;
            }

            ClaimPillarManager.addCorner2(player, pos.getX(), pos.getZ());
            player.sendMessage("Set second position!");
            event.setCancelled(true);

        }

        if (event.getAction() == Action.LEFT_CLICK_AIR && player.isSneaking()) {
                Location corner_1 = ClaimPillarManager.getFirstCornerAsLocation(player.getUniqueId());
                Location corner_2 = ClaimPillarManager.getSecondCornerAsLocation(player.getUniqueId());
                if (corner_1 == null) {
                    player.sendMessage("First corner isn't set!");
                    return;
                }

                if (corner_2 == null) {
                    player.sendMessage("Second corner isn't set!");
                    return;
                }


                // make claim
                Selection selection = new Selection(corner_1, corner_2);
                if (!ClaimsManager.isClaimSizeLegal(selection.getCuboid())) {
                    player.sendMessage("Claim must be larger than a 5x5!");
                    return;
                }

                Bukkit.getScheduler().runTaskAsynchronously(Hcfcore.getInstance(), () -> {
                    try {
                        ClaimsManager.newClaim(player, selection);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                // clear wand
                removeClaimWand(player);

                // clear pillars
                // Only commented out for debug purposes
                ClaimPillarManager.removeCorner1Pillar(player);
                ClaimPillarManager.removeCorner2Pillar(player);

                player.sendMessage("Claimed land!");
                event.setCancelled(true);

        }




        // LEGACY CODE AS OF 09/07/23
//        if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
//            System.out.println("Left clicked floor with claim wand!");
//            Location pos1 = event.getClickedBlock().getLocation();
//            Selection old_selection = SelectionsManager.getAnyExistingSelection(player.getUniqueId());
//            if (old_selection != null) {
//                if (old_selection.getPos1() != null) {
//                    removePillar(world, player, (int) old_selection.getPos1().getX(), (int) old_selection.getPos1().getZ());
//                }
//            }
//
//            SelectionsManager.setPos1(player, pos1);
//            placePillar(world, player, (int) pos1.getX(), (int) pos1.getZ());
//        }
//
//        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
//            System.out.println("right clicked floor with claim wand!");
//            Location pos2 = event.getClickedBlock().getLocation();
//            Selection old_selection = SelectionsManager.getAnyExistingSelection(player.getUniqueId());
//            if (old_selection != null) {
//                if (old_selection.getPos1() != null) {
//                    removePillar(world, player, (int) old_selection.getPos1().getX(), (int) old_selection.getPos1().getZ());
//                }
//            }
//
//            SelectionsManager.setPos2(player, pos2);
//            placePillar(world, player, (int) pos2.getX(), (int) pos2.getZ());
//        }
//
//        if (event.getAction() == Action.LEFT_CLICK_AIR && player.isSneaking()) {
//            try {
//                Selection selection = SelectionsManager.getSelection(player);
//                if (selection.getPos1() == null) {
//                    player.sendMessage("First corner isn't set!");
//                    return;
//                }
//
//                if (selection.getPos2() == null) {
//                    player.sendMessage("Second corner isn't set!");
//                    return;
//                }
//
//                // make claim
//                ClaimsManager.newClaim(player, selection);
//
//                // clear wand
//                removeClaimWand(player);
//
//                // clear pillars
//                removePillar(world, player, (int) selection.getPos1().getX(), (int) selection.getPos1().getZ());
//                removePillar(world, player, (int) selection.getPos2().getX(), (int) selection.getPos2().getZ());
//
//                player.sendMessage("Claimed land!");
//            } catch(SQLException e) {
//                 player.sendMessage("SQL Error making claim! Consult developer immediately.");
//                e.printStackTrace();
//                return;
//            }
//
//        }






    }



    public void removeClaimWand(Player player) {
        player.getInventory().removeItem(ClaimWand.makeNewWand());
    }


    public boolean checkPlayerInFaction(Player player) {

        if (!NewFactionManager.playerInFaction(player.getUniqueId())) {
            removeClaimWand(player);
            player.sendMessage("You are not in a faction!");
            return false;
        }


        return true;
    }
}
