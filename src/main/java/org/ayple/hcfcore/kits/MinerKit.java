package org.ayple.hcfcore.kits;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionType;

public class MinerKit extends KitmapKit {

    @Override
    public String getKitName() {
        return "Miner";
    }

    @Override
    public ItemStack[] getKitArmor() {
        ItemStack helmet = new ItemStack(Material.IRON_HELMET);
        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemStack leggings = new ItemStack(Material.IRON_LEGGINGS);
        ItemStack boots = new ItemStack(Material.IRON_BOOTS);

        helmet.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        chestplate.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        leggings.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);
        boots.addEnchantment(Enchantment.PROTECTION_ENVIRONMENTAL, 2);

        helmet.addEnchantment(Enchantment.DURABILITY, 3);
        chestplate.addEnchantment(Enchantment.DURABILITY, 3);
        leggings.addEnchantment(Enchantment.DURABILITY, 3);
        boots.addEnchantment(Enchantment.DURABILITY, 3);

        return new ItemStack[] {helmet, chestplate, leggings, boots};
    }

    @Override
    public Inventory getKitInventory() {
        Inventory inv = Bukkit.createInventory(null, 36);
        ItemStack pickaxe_1 = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack pickaxe_2 = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemStack steak = new ItemStack(Material.COOKED_BEEF, 64);

        pickaxe_1.addEnchantment(Enchantment.DIG_SPEED, 5);
        pickaxe_1.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
        pickaxe_1.addEnchantment(Enchantment.DURABILITY, 3);

        pickaxe_2.addEnchantment(Enchantment.DIG_SPEED, 5);
        pickaxe_2.addEnchantment(Enchantment.LOOT_BONUS_BLOCKS, 3);
        pickaxe_2.addEnchantment(Enchantment.DURABILITY, 3);
        pickaxe_2.addEnchantment(Enchantment.SILK_TOUCH, 1);



        inv.setItem(0, pickaxe_1);
        inv.setItem(1, pickaxe_2);
        inv.setItem(2, steak);

        return inv;
    }


}
