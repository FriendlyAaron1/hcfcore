package org.ayple.hcfcore.core.cooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;

import java.util.UUID;

public class EnderpearlCooldown extends BukkitRunnable {
    Player owner;
    int seconds_left;
    Objective objective;
    public int getSecondsLeft() { return this.seconds_left; }


    public EnderpearlCooldown(Player owner) {
        this.owner = owner;
        this.seconds_left = 15;
        this.objective =  this.owner.getScoreboard().getObjective("scoreboard");
        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);

    }

    @Override
    public void run() {
        if (seconds_left == 0) {
            CooldownManager.onFinishedEnderpearlCooldown(owner);
            this.objective.getScoreboard().resetScores(ChatColor.DARK_PURPLE + "EnderPearl Cooldown:");
            cancel();
        } else {
            objective.getScore(ChatColor.DARK_PURPLE + "EnderPearl Cooldown:").setScore(seconds_left);
            seconds_left--;
        }
    }
}
