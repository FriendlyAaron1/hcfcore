package org.ayple.hcfcore.events;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.enchantment.EnchantItemEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerEnchantedItemEvent implements Listener {
    @EventHandler
    public void onEnchantItem(EnchantItemEvent event) {
        if (event.getEnchantmentHint() == Enchantment.DAMAGE_ALL) {
            if (event.getLevelHint() > 2) {
                ItemStack enchanted_item = event.getItem();
                enchanted_item.removeEnchantment(Enchantment.DAMAGE_ALL);
                enchanted_item.addEnchantment(Enchantment.DAMAGE_ALL, 2);
            }
        }
    }

}
