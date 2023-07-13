package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.pvpclasses.BardClass;
import org.ayple.hcfcore.pvpclasses.PvpClass;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.sql.SQLException;

public class BardEffectsEvent implements Listener {
    @EventHandler
    public void onPlayerHoldItem(PlayerItemHeldEvent event) {
        Player p = event.getPlayer();

        // TODO: for the love of god, optimize this eventually,
        // TODO: test this!!
        // classic running sql query every millisecond


        // this is done here to avoid errors
        if (!PvpClass.wearingAllArmor(p.getInventory())) {
            return;
        }

        PlayerInventory inventory = p.getInventory();
        ItemStack helmet = inventory.getHelmet();
        ItemStack chestplate = inventory.getChestplate();
        ItemStack leggings = inventory.getLeggings();
        ItemStack boots = inventory.getBoots();

        if (helmet.getType() == Material.GOLD_HELMET &&
                chestplate.getType() == Material.GOLD_CHESTPLATE &&
                leggings.getType() == Material.GOLD_LEGGINGS &&
                boots.getType() == Material.GOLD_BOOTS)
        {
            try {
                Faction player_faction = FactionManager.getFactionFromPlayerID(p.getUniqueId());
                if (player_faction == null) {
                    return;
                }

                for (Entity ps : p.getNearbyEntities(10, 25, 10)) {
                    if (ps instanceof Player) {
                        Player target_player = (Player) ps;

                        Faction target_faction = FactionManager.getFactionFromPlayerID(ps.getUniqueId());
                        if (target_faction == null) {
                            continue;
                        }

                        if (player_faction.getFactionID().equals(target_faction.getFactionID()))
                        {
                            BardClass.giveEffectToTargetPlayer(p.getInventory().getItemInHand().getType(), target_player);
                            BardClass.giveEffectToTargetPlayer(p.getInventory().getItemInHand().getType(), p);
                        }

                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("SQL ERROR");
                p.sendMessage("Failed to bard [ SQL ERROR ]");
            }



        }
    }
}
