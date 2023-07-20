package org.ayple.hcfcore;

import org.ayple.hcfcore.commands.*;
import org.ayple.hcfcore.core.claims.ClaimsManager;
import org.ayple.hcfcore.core.claims.serverclaim.SpawnClaim;
import org.ayple.hcfcore.core.faction.NewFactionManager;
import org.ayple.hcfcore.events.*;
import org.ayple.hcfcore.helpers.ConfigHelper;
import org.ayple.hcfcore.playerdata.PlayerDataHandler;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import java.sql.SQLException;

public final class Hcfcore extends JavaPlugin {
    private static Hcfcore INSTANCE;


    public static Hcfcore getInstance() {
        return INSTANCE;
    }


    public boolean KITMAP_MODE = true;

    private ScoreboardManager scoreboardManager;
    public static ScoreboardManager getScoreManager() { return INSTANCE.scoreboardManager; }
    private Scoreboard board;
    public static Scoreboard getScoreBoard() { return INSTANCE.board; }

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
        ConfigHelper.getConfig().addDefault("server_name", "cheeky hcf");
        ConfigHelper.getConfig().addDefault("map_number", 1);
        ConfigHelper.getConfig().addDefault("kitmap_mode", false);
        ConfigHelper.getConfig().addDefault("enchant_limits.sharpness", 2);
        ConfigHelper.getConfig().addDefault("enchant_limits.protection", 2);
        ConfigHelper.getConfig().addDefault("end_portal_exit.x", 1);
        ConfigHelper.getConfig().addDefault("end_portal_exit.y", 80);
        ConfigHelper.getConfig().addDefault("end_portal_exit.z", 200);
        ConfigHelper.getConfig().addDefault("server_claims.spawn.spawn_corner_1_x", -50);
        ConfigHelper.getConfig().addDefault("server_claims.spawn.spawn_corner_1_z", 50);
        ConfigHelper.getConfig().addDefault("server_claims.spawn.spawn_corner_2_x", 50);
//        ConfigHelper.getConfig().addDefault("server_claims.glowstone_mountain.glowstone_respawn_area", -50);


        ConfigHelper.getConfig().options().copyDefaults(true);
        ConfigHelper.save();

        KITMAP_MODE = ConfigHelper.getConfig().getBoolean("kitmap_mode");

        this.scoreboardManager = Bukkit.getScoreboardManager();
        this.board = scoreboardManager.getNewScoreboard();




        try {
            NewFactionManager.loadFactions();
            PlayerDataHandler.loadAllPlayerData();
            ClaimsManager.reloadClaims();
            NewFactionManager.applyDTRregens();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        registerCommands();
        registerEvents();

        //GlowstoneMountainClaim glowstoneMountainClaim = new GlowstoneMountainClaim();
        new SpawnClaim(); // register spawn claim


    }

    private void registerCommands() {
        getCommand("faction").setExecutor(new CommandFaction());
        getCommand("logout").setExecutor(new CommandLogout());
        getCommand("balance").setExecutor(new CommandBalance());
        getCommand("pvpenable").setExecutor(new CommandPvpEnable());
        getCommand("kit").setExecutor(new CommandKit());
        //getCommand("serverclaim").setExecutor(new CommandServerClaim());

    }

    public void registerEvents() {
        PluginManager manager =  getServer().getPluginManager();
        manager.registerEvents(new AntiGriefEvent(), this);
        manager.registerEvents(new BannedItemListenerEvent(), this);
        manager.registerEvents(new BardEffectsEvent(), this);
        manager.registerEvents(new ClaimWandEvent(), this);
        manager.registerEvents(new CombatLoggerEvent(), this);
        manager.registerEvents(new DtrEventHandler(), this);
        manager.registerEvents(new EnchantLimiterEvent(), this);
        manager.registerEvents(new EndEventHandler(), this);
        manager.registerEvents(new GoldenAppleListenerEvent(), this);
        manager.registerEvents(new KeyOpenEvent(), this);
        manager.registerEvents(new KitEquipSignEvent(), this);
        manager.registerEvents(new OnClickKitGUIEvent(), this);
        manager.registerEvents(new OnEnderPearlEvent(), this);
        manager.registerEvents(new OnPlayerMoveEvent(), this);
        manager.registerEvents(new OnSleepEvent(), this);
        manager.registerEvents(new PlayerArmorChangeEvent(), this);
        manager.registerEvents(new PlayerDeathBanEvent(), this);
        manager.registerEvents(new PlayerHitEvent(), this);
        manager.registerEvents(new PlayerInteractEntity(), this);
        manager.registerEvents(new PlayerJoinedServerEvent(), this);
        manager.registerEvents(new PlayerLeaveServerEvent(), this);
        manager.registerEvents(new PlayerUseChatEvent(), this);
        manager.registerEvents(new PotionRefillSignEvent(), this);
        manager.registerEvents(new PvpTimerEvent(), this);
        manager.registerEvents(new ShopSignEvent(), this);
        manager.registerEvents(new SpawnListenerEvent(), this);
        manager.registerEvents(new TntDisablerEvent(), this);
    }

    @Override
    public void onDisable() {
    }

}
