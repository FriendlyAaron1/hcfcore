package org.ayple.hcfcore.events;

import jdk.vm.ci.hotspot.EventProvider;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;

public class EnchantLimiterEvent implements Listener {
    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    public void onPlayerEnchantItem(EnchantItemEvent event) {
        final Player player = event.getEnchanter();
        ItemStack item = event.getItem();

        if (item == null || item.getType() == Material.AIR || !item.getItemMeta().hasEnchant(Enchantment.LUCK)) {
            // Check if the enchantment is Protection or Sharpness
            if (event.getEnchantsToAdd().containsKey(Enchantment.PROTECTION_ENVIRONMENTAL) ||
                    event.getEnchantsToAdd().containsKey(Enchantment.DAMAGE_ALL)) {
                // Get the current enchantment level for Protection and Sharpness
                int protectionLevel = event.getEnchantsToAdd().get(Enchantment.PROTECTION_ENVIRONMENTAL);
                int sharpnessLevel = event.getEnchantsToAdd().get(Enchantment.DAMAGE_ALL);

                // Set the desired limits (Protection 2 and Sharpness 2)
                if (protectionLevel > 2) {
                    event.getEnchantsToAdd().put(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
                    player.sendMessage(ChatColor.GREEN + "Set item to protection 2!");
                }
                if (sharpnessLevel > 2) {
                    event.getEnchantsToAdd().put(Enchantment.DAMAGE_ALL, 2);
                    player.sendMessage(ChatColor.GREEN + "Set item to sharpness 2!");
                }
            }
        }
    }


}
