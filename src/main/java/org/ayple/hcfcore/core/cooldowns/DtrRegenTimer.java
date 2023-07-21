package org.ayple.hcfcore.core.cooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.core.faction.Faction;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;

public class DtrRegenTimer  extends BukkitRunnable {
    private Faction owner;
    private int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }

    public DtrRegenTimer(Faction owner) {
        this.owner = owner;
        this.seconds_left = 3600;
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            CooldownManager.onFinishedDtrRegen(owner);
            cancel();
        } else {
            seconds_left--;
        }
    }
}