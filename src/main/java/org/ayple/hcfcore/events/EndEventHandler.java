package org.ayple.hcfcore.events;

import org.ayple.hcfcore.helpers.ConfigHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerChannelEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

public class EndEventHandler implements Listener {

    private final Location end_portal_exit = new Location(
            Bukkit.getWorld("world"),
            ConfigHelper.getConfig().getDouble("end_portal_exit.x"),
            ConfigHelper.getConfig().getDouble("end_portal_exit.y"),
            ConfigHelper.getConfig().getDouble("end_portal_exit.z")
    );



    @EventHandler
    public void onPlayerLeaveEndThroughEndPortal(PlayerTeleportEvent event) {
        if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            Player player = event.getPlayer();
            player.teleport(end_portal_exit);
        }
    }


    // this isn't needed since the warzone thing stops it lmfao
//    @EventHandler
//    public void onPlayerInteractInEnd(PlayerInteractEvent event) {
//        if (event.getPlayer().getWorld() != Bukkit.getWorld("end")) return;
//
//        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.LEFT_CLICK_BLOCK) {
//            if (event.getPlayer().hasPermission("hcf.core.interact_in_end")) {
//                return;
//            }
//
//            event.setCancelled(true);
//            event.getPlayer().sendMessage(ChatColor.RED + "You cannot interact in end!");
//        }
//    }
}
