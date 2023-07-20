package org.ayple.hcfcore.events;

import org.ayple.hcfcore.crates.KillKey;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class KeyOpenEvent implements Listener {

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();
        player.getWorld().dropItem(player.getLocation(), KillKey.getKeyItem());
    }

    @EventHandler(priority = EventPriority.NORMAL, ignoreCancelled = true)
    public void onInteract(PlayerInteractEvent event) {
        ItemStack item_in_hand = event.getPlayer().getItemInHand();

        if (item_in_hand == null) return;
        if (item_in_hand.getType() != Material.TRIPWIRE_HOOK ) return;


        ItemMeta meta = item_in_hand.getItemMeta();
        if (Objects.equals(meta.getDisplayName(), ChatColor.RED + "Kill Key") && meta.getLore().contains(ChatColor.GOLD + "Right click to open key!")) {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
                item_in_hand.setAmount(1); // just to make sure it only takes 1
                event.getPlayer().getInventory().removeItem(item_in_hand);
                event.getPlayer().getInventory().addItem(KillKey.getRandomItems());
                event.setCancelled(true);

            }
        }



    }
}
