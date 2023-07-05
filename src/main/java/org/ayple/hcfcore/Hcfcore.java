package org.ayple.hcfcore;

import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.events.PlayerJoinedServerEvent;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.ayple.hcfcore.events.PlayerEnchantedItemEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Hcfcore extends JavaPlugin {
    private static Hcfcore INSTANCE;


    public static Hcfcore getInstance() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        INSTANCE = this;
        System.out.println("Starting up HCF core!");
        ConfigHelper.setup();
        ConfigHelper.getConfig().options().copyDefaults(true);
        ConfigHelper.save();

        try {
            ClaimsManager.loadClaims();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        registerCommands();
        registerEvents();



    }

    private void registerCommands() {
    }

    public void registerEvents() {
        PluginManager manager =  getServer().getPluginManager();
        manager.registerEvents(new PlayerJoinedServerEvent(), this);
        manager.registerEvents(new PlayerEnchantedItemEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // save stuff to disk
    }

}
