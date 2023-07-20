package org.ayple.hcfcore.kits.pvpclasses;

import org.bukkit.inventory.PlayerInventory;

public class PvpClass {

    public static boolean wearingAllArmor(PlayerInventory armor) {
        return (armor.getHelmet() != null &&
                armor.getChestplate() != null &&
                armor.getLeggings() != null &&
                armor.getBoots() != null);
    }
}
