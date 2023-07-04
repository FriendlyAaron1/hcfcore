package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.core.items.ClaimWand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.sql.SQLException;

public class PlayerUseEvent implements Listener {
    @EventHandler
    public void onPlayerUse(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand() == ClaimWand.makeNewWand()) {
            // NOTE: this should never have to return since the player shouldn't
            // have a claim wand if they arent in a faction. But shit happens lmao
            try {
                // if player is in faction
                if (FactionManager.getInstance().playerInFaction(player.getUniqueId())) {
                    // TODO: remove the claim wand
                    return;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return;
            }

            if (event.getAction() == Action.LEFT_CLICK_AIR && player.isSneaking()) {
                // make claim
            }

            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Location pos1 = event.getClickedBlock().getLocation();
                World world = Bukkit.getWorld("world");
                placePillar(world, player, (int) pos1.getX(), (int) pos1.getZ());


            }

            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                Location pos2 = event.getClickedBlock().getLocation();
                World world = Bukkit.getWorld("world");
                placePillar(world, player, (int) pos2.getX(), (int) pos2.getZ());
            }


        }
    }

    public void placePillar(World world, Player player, int x, int z) {
        for (int y = world.getMinHeight(); y < world.getMaxHeight(); y++) {
            Location pos = new Location(world, x, y, z);
            Block block = world.getBlockAt(pos);

            if (block.getType().equals(Material.AIR)) {
                player.sendBlockChange(pos, Material.GLASS, (byte) 0);
            }
        }
    }
}
