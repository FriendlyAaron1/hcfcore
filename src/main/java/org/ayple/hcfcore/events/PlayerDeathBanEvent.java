package org.ayple.hcfcore.events;

import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

public class PlayerDeathBanEvent implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = (Player) event.getEntity();

        try {
            if (player.getKiller() != null) {
                Player killer = (Player) player.getKiller();

                String sql = "UPDATE player_data SET kills = kills + 1 WHERE player_uuid=?";
                HcfSqlConnection conn = new HcfSqlConnection();
                PreparedStatement statement = conn.getConnection().prepareStatement(sql);
                statement.setString(1, killer.getUniqueId().toString());
                conn.closeConnection();
            }

            Faction player_faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (player_faction != null) {
                FactionManager.decreaseDTR(player_faction.getFactionID());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // //Since there are 1000 miliseconds in one second, 60 seconds in one minute,
        // and 60 minutes in an hour,  by adding 60*60*1000 to the current time,
        // you are adding an hour to the bantime.
        // * 4 at end for 4 hours
        Date unban_date = new Date(System.currentTimeMillis() + (60 * 60 * 1000));
        Bukkit.getBanList(BanList.Type.NAME).addBan(player.getName(), "Death banned!", unban_date, null);
        player.kickPlayer("You died, banned for 4 hours");




    }
}
