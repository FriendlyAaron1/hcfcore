package org.ayple.hcfcore.core.cooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class EnderpearlCooldown extends BukkitRunnable {
    UUID owner;
    int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }


    public EnderpearlCooldown(UUID owner) {
        this.owner = owner;
        this.seconds_left = 30;
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);

    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            CooldownManager.onFinishedEnderpearlCooldown(owner);
            cancel();
        } else {
            seconds_left--;
        }
    }
}
