package org.ayple.hcfcore.core.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.*;

public class ServerScoreboard {

    public static Scoreboard newScoreboard() {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective objective = board.registerNewObjective("scoreboard", "dummy");

        objective.setDisplayName(ChatColor.GOLD + "     CheekyHCF [Map 1]     ");
        objective.setDisplaySlot(DisplaySlot.SIDEBAR);

        objective.getScore("https://store.cheekyhcf.com").setScore(0);



        return board;
    }
}
