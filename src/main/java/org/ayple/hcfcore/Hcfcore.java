package org.ayple.hcfcore;

import org.ayple.hcfcore.commands.*;
import org.ayple.hcfcore.core.claims.Claim;
import org.ayple.hcfcore.events.PlayerEnchantedItemEvent;
import org.ayple.hcfcore.events.PlayerJoinedServerEvent;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.ayple.hcfcore.helpers.SQLHelper;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Objects;

public final class Hcfcore extends JavaPlugin {


    private static HashMap<String, Claim> claims = new HashMap<String, Claim>();
    private Spawn spawn;


    @Override
    public void onEnable() {
        Logger.ConsoleLog("Starting up HCF core!");
        ConfigHelper.setup();
        ConfigHelper.getConfig().addDefault("DB_TABLE_NAME", "map");
        ConfigHelper.getConfig().options().copyDefaults(true);
        ConfigHelper.save();

        String table_name = Objects.requireNonNull(ConfigHelper.getConfig().get("DB_TABLE_NAME")).toString();

        SQLHelper.querydb("CREATE TABLE IF NOT EXISTS `" + table_name + "`");



        registerCommands();
        registerEvents();



    }

    private void registerCommands() {
        getCommand("corehelp").setExecutor(new CommandCoreHelp());
        getCommand("admin").setExecutor(new CommandAdmin());
        getCommand("tp").setExecutor(new CommandTP());
        getCommand("tpa").setExecutor(new CommandTPA());
        getCommand("spawn").setExecutor(new CommandSpawn());
        getCommand("eotw").setExecutor(new CommandSpawn());
        getCommand("sotw").setExecutor(new CommandSOTW());
    }

    public void registerEvents() {
        PluginManager manager =  getServer().getPluginManager();
        manager.registerEvents(new PlayerJoinedServerEvent(), this);
        manager.registerEvents(new PlayerEnchantedItemEvent(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        Logger.ConsoleLog("Shutting down HCF core!");
    }

}
