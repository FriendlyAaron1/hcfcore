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


    public static void registerEnderpearlCooldown(UUID player_id) {
        enderpearlCooldowns.remove(player_id); // aka reset the timer
        enderpearlCooldowns.put(player_id, new EnderpearlCooldown(player_id));
    }

    public static void onFinishedEnderpearlCooldown(UUID player_id) {
        enderpearlCooldowns.remove(player_id);
    }

    public static boolean hasEnderpearlCooldown(UUID player_id) {
        return enderpearlCooldowns.containsKey(player_id);
    }

    public static void registerHomeTimer(Player player, Location hq) {
        homeTimers.put(player.getUniqueId(), new HomeTimer(player, hq));
    }

    public static void onFinishedHomeTimer(UUID player_id) {
        homeTimers.remove(player_id);
    }

    public static void cancelHomeTimer(UUID player_id) {
        homeTimers.get(player_id).cancel();
    }

    public static boolean hasHomeTimer(UUID player_id) {
        return homeTimers.containsKey(player_id);
    }

    public static void registerCombatTimer(Player player) {
        combatCooldowns.put(player.getUniqueId(), new CombatTimer(player.getUniqueId()));
    }

    public static void onCombatTimerOver(UUID player_id) {
        combatCooldowns.remove(player_id);
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

    public static boolean hasLogoutTimer(UUID player_id) {
        return logoutTimer.containsKey(player_id);
    }

    public static void cancelLogoutTimer(UUID player_id) {
        logoutTimer.get(player_id).cancel();
    }


}
