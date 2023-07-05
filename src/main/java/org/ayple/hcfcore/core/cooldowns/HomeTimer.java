package org.ayple.hcfcore.core.cooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;


// I realized it would be better to store
// player and location object rather
// than doing another SQL query
// for the excact same result.
// TODO: look back on this in the future
public class HomeTimer extends BukkitRunnable {
    private Player owner;
    private Location hq;

    private int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }


    public HomeTimer(Player owner, Location hq) {
        this.owner = owner;
        this.seconds_left = 20;
        this.hq = hq;
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);

    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            CooldownManager.onFinishedHomeTimer(owner.getUniqueId());
            owner.teleport(hq);
            cancel();
        } else {
            owner.sendMessage("Teleporing home in " + Integer.toString(seconds_left) + " seconds!");
            seconds_left--;
        }
    }
}
