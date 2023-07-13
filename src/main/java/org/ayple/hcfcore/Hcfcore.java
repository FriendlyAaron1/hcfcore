package org.ayple.hcfcore;

import org.ayple.hcfcore.commands.CommandBalance;
import org.ayple.hcfcore.commands.CommandFaction;
import org.ayple.hcfcore.commands.CommandKit;
import org.ayple.hcfcore.commands.CommandLogout;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.events.*;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;

public final class Hcfcore extends JavaPlugin {
    private static Hcfcore INSTANCE;


    public static Hcfcore getInstance() {
        return INSTANCE;
    }

    public boolean KITMAP_MODE = true;


    @Override
    public void onEnable() {
        INSTANCE = this;
        System.out.println("Starting up HCF core!");
        ConfigHelper.setup();

        ConfigHelper.getConfig().addDefault("DB_HOST", "127.0.0.1");
        ConfigHelper.getConfig().addDefault("DB_PORT", "3006");
        ConfigHelper.getConfig().addDefault("DB_USER", "root");
        ConfigHelper.getConfig().addDefault("DB_PASS", "password");
        ConfigHelper.getConfig().addDefault("DB_NAME", "hcf");


        ConfigHelper.getConfig().options().copyDefaults(true);
        ConfigHelper.save();

        //KITMAP_MODE = ConfigHelper.getConfig().getBoolean("kitmap_mode");

        try {
            ClaimsManager.loadClaims();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        registerCommands();
        registerEvents();



    }

    private void registerCommands() {
        getCommand("faction").setExecutor(new CommandFaction());
        getCommand("logout").setExecutor(new CommandLogout());
        getCommand("balance").setExecutor(new CommandBalance());
        getCommand("kit").setExecutor(new CommandKit());

    }

    public void registerEvents() {
        PluginManager manager =  getServer().getPluginManager();
        manager.registerEvents(new BardEffectsEvent(), this);
        manager.registerEvents(new ClaimWandEvent(), this);
        manager.registerEvents(new KitEquipSignEvent(), this);
        manager.registerEvents(new PlayerArmorChangeEvent(), this);
        manager.registerEvents(new PlayerDeathBanEvent(), this);
        manager.registerEvents(new PlayerHitEvent(), this);
        manager.registerEvents(new PlayerInteractedClaimEvent(), this);
        manager.registerEvents(new PlayerInteractEntity(), this);
        manager.registerEvents(new PlayerJoinedServerEvent(), this);
        manager.registerEvents(new PlayerLeaveServerEvent(), this);
        manager.registerEvents(new PlayerMoveEvent(), this);
        manager.registerEvents(new PotionRefillSignEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        // save stuff to disk
    }

}
