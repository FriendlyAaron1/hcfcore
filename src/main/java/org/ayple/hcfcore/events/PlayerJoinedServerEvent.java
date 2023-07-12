package org.ayple.hcfcore.events;

import org.ayple.hcfcore.helpers.HcfSqlConnection;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PlayerJoinedServerEvent implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event)
    {
        Player player = event.getPlayer();
        try {
            String sql = "SELECT * FROM player_data WHERE player_id=?";
            HcfSqlConnection conn = new HcfSqlConnection();
            PreparedStatement statment = conn.getConnection().prepareStatement(sql);
            statment.setString(1, player.getUniqueId().toString());
            statment.executeQuery();

            ResultSet result = statment.getResultSet();
            if (!result.next()) {
                sql = "INSERT INTO player_data (player_id, balance) VALUES (?, ?)";
                statment = conn.getConnection().prepareStatement(sql);
                statment.setString(1, player.getUniqueId().toString());
                statment.setInt(2, 500); // starting money
                statment.executeUpdate();

                // give starter items
                ItemStack steak = new ItemStack(Material.COOKED_BEEF);
                ItemStack fishing_rod = new ItemStack(Material.FISHING_ROD);
                fishing_rod.addEnchantment(Enchantment.LURE, 2);
                steak.setAmount(32);
                player.getInventory().addItem(steak);
                player.getInventory().addItem(fishing_rod);
                player.sendMessage("Welcome to the server for the first time!");
            }

            conn.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
            event.getPlayer().sendMessage("SQL ERROR WHEN FINDING PLAYER INFO. Contact developer asap.");
        }
    }


}
