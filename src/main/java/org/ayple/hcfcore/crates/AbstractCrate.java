package org.ayple.hcfcore.crates;

import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public abstract class AbstractCrate {
    private ArrayList<ItemStack> crateItems;

    public abstract String getName();

    public ArrayList<ItemStack> getItems() {
        return this.crateItems;
    }

    public void registerCrateItem(ItemStack item) {
        crateItems.add(item);
    }

}
