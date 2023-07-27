package org.ayple.hcfcore.core.cooldowns;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.helpers.DateTimeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.*;

import java.util.UUID;

// as of 24/07/23 i learned why storing
// a Player object is stupid

public abstract class AbstractCooldown extends BukkitRunnable {
    private final UUID ownerID;
    public UUID getOwnerID() { return this.ownerID; }
    private int seconds_left;
    public int getSecondsLeft() { return this.seconds_left; }

    private final String objectiveContent;
    private final Objective scoreboardObjective;

    public Objective getScoreboardObjective() { return this.scoreboardObjective; }

//    private final Team team;
//    private String teamName;

//    public AbstractCooldown(Player player_instance, int starting_seconds, String team_name, String entry_content) {
//        this.seconds_left = starting_seconds;
//        this.ownerID = player_instance.getUniqueId();
//        this.teamName = team_name;
//        this.team = player_instance.getScoreboard().registerNewTeam(team_name);
//        this.team.addEntry(entry_content);
//        this.team.setPrefix("");
//        this.team.setSuffix("");
//        player_instance.getScoreboard().getObjective(entry_content);
//        player_instance.sendMessage("TEST");
//        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
//    }
//
//    @Override
//    public void run() {
//        String time_left = ChatColor.GRAY + DateTimeUtils.formatSecondsToMinutesSeconds(this.seconds_left);
//        if (this.seconds_left == 0) {
//            this.onTimerFinished();
//            this.team.removeEntry(this.teamName);
//            this.cancel();
//        } else {
//            this.onTimerChanged();
//            this.team.setSuffix(time_left);
//            this.seconds_left--;
//        }
//    }

    public AbstractCooldown(Player player_instance, int starting_seconds, String objective_name, String entry_content) {
        this.seconds_left = starting_seconds;
        this.ownerID = player_instance.getUniqueId();
        this.objectiveContent = entry_content;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard scoreboard = manager.getNewScoreboard();
        this.scoreboardObjective = scoreboard.registerNewObjective(objective_name, "dummy");
        this.scoreboardObjective.setDisplayName(entry_content);
        this.scoreboardObjective.setDisplaySlot(DisplaySlot.SIDEBAR);

        player_instance.setScoreboard(scoreboard);

        this.runTaskTimer(Hcfcore.getInstance(), 0, 20);
    }
        @Override
    public void run() {
        String time_left = ChatColor.GRAY + DateTimeUtils.formatSecondsToMinutesSeconds(this.seconds_left);
        if (this.seconds_left == 0) {
            this.onTimerFinished();
            this.cancel();
        } else {
            this.onTimerChanged();
            this.seconds_left--;
        }

        this.scoreboardObjective.setDisplayName(this.objectiveContent + time_left);
    }


    public abstract void onTimerFinished();
    public abstract void onTimerChanged();
}
