package org.ayple.hcfcore.core.cooldowns;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.UUID;

public class CooldownManager {

    // UUID is player id //

    // enderpearl
    private static Hashtable<UUID, BukkitRunnable> enderpearlCooldowns = new Hashtable<UUID, BukkitRunnable>();
    private static Hashtable<UUID, BukkitRunnable> homeTimers = new Hashtable<UUID, BukkitRunnable>(); // f home
    private static Hashtable<UUID, BukkitRunnable> combatCooldowns = new Hashtable<UUID, BukkitRunnable>(); // combat timers
    private static Hashtable<UUID, BukkitRunnable> logoutTimer = new Hashtable<UUID, BukkitRunnable>(); // logout timers


    public static void registerEnderpearlCooldown(Player player_id) {
        enderpearlCooldowns.put(player_id.getUniqueId(), new EnderpearlCooldown(player_id));
    }

    public static int getSecondsLeftOfEnderpearlCooldown(UUID player_id) {
        EnderpearlCooldown cooldown = (EnderpearlCooldown) enderpearlCooldowns.get(player_id);
        if (cooldown == null) {
            System.out.println("Failed to get the seconds left of enderpearl cooldown?");
            return 0;
        }

        return cooldown.getSecondsLeft();

    }

    public static void onFinishedEnderpearlCooldown(Player player) {
        enderpearlCooldowns.remove(player.getUniqueId());
    }

    public static boolean hasEnderpearlCooldown(UUID player_id) {
        return enderpearlCooldowns.containsKey(player_id);
    }

    public static void registerHomeTimer(Player player, Location hq) {
        homeTimers.put(player.getUniqueId(), new HomeTimer(player, hq));
    }

    public static void onFinishedHomeTimer(Player player_id) {
        homeTimers.remove(player_id.getUniqueId());
    }

    public static void cancelHomeTimer(Player player_id) {
        homeTimers.get(player_id.getUniqueId()).cancel();
        homeTimers.remove(player_id.getUniqueId());
    }

    public static boolean hasHomeTimer(UUID player_id) {
        return homeTimers.containsKey(player_id);
    }

    public static void registerCombatTimer(Player player) {
        combatCooldowns.put(player.getUniqueId(), new CombatTimer(player));
    }

    public static void onCombatTimerOver(Player player_id) {
        combatCooldowns.remove(player_id.getUniqueId());
    }

    public static boolean hasCombatTimer(UUID player_id) {
        return combatCooldowns.containsKey(player_id);
    }

    public static void registerLogoutTimer(Player player) {
        logoutTimer.put(player.getUniqueId(), new LogoutTimer(player));
    }

    public static void onLogoutTimerDone(UUID player_id) {
        logoutTimer.remove(player_id);
    }

    public static boolean hasLogoutTimer(Player player_id) {
        return logoutTimer.containsKey(player_id.getUniqueId());
    }

    public static void cancelLogoutTimer(Player player_id) {
        logoutTimer.get(player_id.getUniqueId()).cancel();
        logoutTimer.remove(player_id.getUniqueId());
    }


}
