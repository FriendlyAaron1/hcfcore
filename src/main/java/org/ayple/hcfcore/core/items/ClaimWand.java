package org.ayple.hcfcore.core.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Map;

public class ClaimWand {
    Player owner;
    ItemStack claim_wand;

    public ClaimWand(Player owner) {
        this.owner = owner;
        this.claim_wand = makeNewWand();
    }

    public static ItemStack makeNewWand() {
        ItemStack item = new ItemStack(Material.STICK);
        ItemMeta meta = item.getItemMeta();

        if (meta != null) {
            meta.setDisplayName("CLAIM WAND");
            meta.setUnbreakable(true);
        }

        item.setItemMeta(meta);
        item.addUnsafeEnchantment(Enchantment.MENDING, 10);

        return item;
    }

    public ItemStack getClaimWand() {
        return this.claim_wand;
    }

    public static boolean isItemClaimWand(ItemStack item) {
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            return (meta.isUnbreakable() && meta.getDisplayName().equals("CLAIM WAND"));
        }

        return false;
    }

}