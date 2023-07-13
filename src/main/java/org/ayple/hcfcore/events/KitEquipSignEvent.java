package org.ayple.hcfcore.events;

import org.ayple.hcfcore.kits.KitManager;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class KitEquipSignEvent implements Listener {

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player player = e.getPlayer();
        if (!player.hasPermission("hcf.createkitsign")) return;

        if (e.getLine(0).equalsIgnoreCase("[Kit]")) {
            e.setLine(1, ChatColor.BLUE + e.getLine(1));
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        Player player = e.getPlayer();

        if (!(e.getAction() == Action.RIGHT_CLICK_BLOCK)) return;
        if (e.getClickedBlock().getState() instanceof Sign) {
            Sign sign = (Sign) e.getClickedBlock().getState();
            if (sign.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "Diamond")) {
                KitManager.diamondkit.givePlayerKit(player);
            }
            else if (sign.getLine(1).equalsIgnoreCase(ChatColor.GOLD + "Miner")) {
                KitManager.minerkit.givePlayerKit(player);
            }

        }
    }
}
