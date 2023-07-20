package org.ayple.hcfcore.crates;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Random;

public class KillKey {

    public static ArrayList<ItemStack> getItems() {
        final ArrayList<ItemStack> items = new ArrayList<ItemStack>();
        items.add(new ItemStack(Material.DIAMOND_BLOCK, 2));
        items.add(new ItemStack(Material.GOLD_BLOCK, 4));
        items.add(new ItemStack(Material.IRON_BLOCK, 8));
        items.add(new ItemStack(Material.LAPIS_BLOCK, 8));
        items.add(new ItemStack(Material.GOLDEN_APPLE, 8));
        return items;
    }


    // make it so it makes an array of random items
    // tbf i don't really care lol
    public static ItemStack getRandomItems() {
        ArrayList<ItemStack> items = getItems();
        return items.get(new Random().nextInt(items.size()));
    }



    public static ItemStack getKeyItem() {
        ItemStack key = new ItemStack(Material.TRIPWIRE_HOOK, 1);
        ItemMeta key_meta = key.getItemMeta();
        ArrayList<String> lore = new ArrayList<String>();

        lore.add(ChatColor.GOLD + "Right click to open key!");
        key_meta.setLore(lore);
        key_meta.setDisplayName(ChatColor.RED + "Kill Key");

        key.setItemMeta(key_meta);
        return key;
    }

}
