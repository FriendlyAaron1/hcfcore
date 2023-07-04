package org.ayple.hcfcore.events;

import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;

public class PlayerInteractedEvent implements Listener {
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Material item_player_holding = Objects.requireNonNull(event.getPlayer().getItemInUse().getType());
            Material clicked_block = event.getClickedBlock().getType();

            if (item_player_holding.equals(Material.ENDER_PEARL)) {
                //if (event.getClickedBlock())
            }
        }
    }

}
