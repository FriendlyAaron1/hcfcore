package org.ayple.hcfcore.events;


import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Date;

public class PlayerDeathEvent implements Listener {
    @EventHandler
    public void onPlayerDeath(org.bukkit.event.entity.PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();


        // TODO: store kill in database
        if (player.getKiller() instanceof Player) {
            Player killer = (Player) player.getKiller();

        }

        // //Since there are 1000 miliseconds in one second, 60 seconds in one minute,
        // and 60 minutes in an hour,  by adding 60*60*1000 to the current time,
        // you are adding an hour to the bantime.
        // * 4 at end for 4 hours
        Date unban_date = new Date(System.currentTimeMillis() + (60 * 60 * 1000 * 4));
        Bukkit.getBanList(BanList.Type.PROFILE).addBan(player.getName(), "Death banned!", unban_date, null);
        player.kickPlayer("You died, banned for 4 hours");


    }
}
