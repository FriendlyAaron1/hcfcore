package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.BalanceHandler;
import org.ayple.hcfcore.kits.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.block.Sign;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.BlockStateMeta;

import java.util.Objects;

public class ShopSignEvent implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("hcf.create.shop_sign")) return;

        if (e.getLine(0).equalsIgnoreCase("[Buy]")) {
            if (e.getLine(1).isEmpty()) {
                player.sendMessage(ChatColor.RED + "Shop item ID is empty!");
                e.setCancelled(true);
                return;
            }

            if (e.getLine(2).isEmpty()) {
                player.sendMessage(ChatColor.RED + "Shop amount is empty!");
                e.setCancelled(true);
                return;
            }

            if (e.getLine(3).isEmpty()) {
                player.sendMessage(ChatColor.RED + "Shop price is empty!");
                e.setCancelled(true);
                return;
            }


            e.setLine(0, ChatColor.GREEN + e.getLine(0));
            e.setLine(3, ChatColor.GREEN + "$" + e.getLine(3));
        }

        if (e.getLine(0).equalsIgnoreCase("[Sell]")) {
            if (e.getLine(1).isEmpty()) {
                player.sendMessage(ChatColor.RED + "Shop item ID is empty!");
                e.setCancelled(true);
                return;
            }

            if (e.getLine(2).isEmpty()) {
                player.sendMessage(ChatColor.RED + "Shop amount is empty!");
                e.setCancelled(true);
                return;
            }

            if (e.getLine(3).isEmpty()) {
                player.sendMessage(ChatColor.RED + "Shop price is empty!");
                e.setCancelled(true);
                return;
            }


            e.setLine(0, ChatColor.BLUE + e.getLine(0));
            e.setLine(3, ChatColor.GREEN + "$" + e.getLine(3));
        }
    }


    // TODO: only drop items if inventory is full and
    // inventory has no other free slots
    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();
        if (e.getClickedBlock() == null) return;

        if (e.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) e.getClickedBlock().getState();

            if (sign.getLine(0).equalsIgnoreCase(ChatColor.GREEN + "[Buy]")) {
                Material material;
                ItemStack item;

                if (Objects.equals(sign.getLine(1), "ZOMBIE_SPAWNER")) {
                    item = new ItemStack(Material.MOB_SPAWNER, 1);
                    BlockStateMeta meta = (BlockStateMeta) item.getItemMeta();
                    CreatureSpawner spawnerState = (CreatureSpawner) meta.getBlockState();
                    spawnerState.setSpawnedType(EntityType.ZOMBIE);
                    meta.setBlockState(spawnerState);
                    item.setItemMeta(meta);

                } else {
                    material = Material.matchMaterial(sign.getLine(1));
                    try {
                        item = new ItemStack(material, Integer.parseInt(sign.getLine(2)));

                    } catch (NullPointerException error) {
                        return;
                    }
                }

                int price = Integer.parseInt(ChatColor.stripColor(sign.getLine(3).replace("$", "")));

                if (BalanceHandler.getPlayerBalance(player) >= price) {
                    BalanceHandler.takeMoneyFromPlayer(player, price);

//                    if (player.getItemInHand().getType() != )
                    player.getWorld().dropItem(player.getLocation(), item);
                    player.sendMessage(ChatColor.GREEN + "Bought " + sign.getLine(1) + " for " + sign.getLine(3) + "!");
                } else {
                    player.sendMessage(ChatColor.RED + "Insufficient funds!");
                }

            }

            else if (sign.getLine(0).equalsIgnoreCase(ChatColor.BLUE + "[Sell]")) {
                Inventory player_inventory = player.getInventory();
                Material material = Material.matchMaterial(sign.getLine(1));
                ItemStack item;
                try {
                    item = new ItemStack(material, 1);
                } catch (NullPointerException error) {
                    return;
                }

                int price = Integer.parseInt(ChatColor.stripColor(sign.getLine(3).replace("$", "")));
                int amount = Integer.parseInt(sign.getLine(2));

                if (player_inventory.containsAtLeast(item, amount)) {
                    BalanceHandler.giveMoneyToPlayer(player, price);
                    item.setAmount(amount);
                    player_inventory.removeItem(item);
                    player.sendMessage(ChatColor.BLUE + "Sold " + sign.getLine(1) + " for " + sign.getLine(3) + "!");
                    player.updateInventory();
                } else {
                    player.sendMessage(ChatColor.RED + "Item not fount in inventory!");
                }

            }



        }
    }
}
