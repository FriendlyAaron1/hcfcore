package org.ayple.hcfcore.core.cooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class LogoutTimer extends BukkitRunnable {
    int seconds_left;
    Player owner;
    public int getSecondsLeft() { return this.seconds_left; }


    public LogoutTimer(Player owner) {
        this.owner = owner;
        this.seconds_left = 30;
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);

    }

    @Override
    public void run() {
        if (this.seconds_left == 0) {
            owner.kickPlayer("Logged out safely.");
            cancel();
        } else {
            this.seconds_left--;
            owner.sendMessage("Logging out in... " + Integer.toString(this.seconds_left));
        }
    }
}
