package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.Hcfcore;
import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.cooldowns.CooldownManager;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.sql.SQLException;

public class CommandFactionHome extends SubCommand {
    @Override
    public String getName() {
        return "home";
    }

    @Override
    public String getDescription() {
        return "teleport to your factions home";
    }

    @Override
    public String getSyntax() {
        return "/f home";
    }

    @Override
    public boolean perform(Player player, String[] args) {
        try {
            Faction player_faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (player_faction == null) {
                player.sendMessage("You are not in a faction.");
                return false;
            }

            Location hq = player_faction.getFactionHQ();
            if (hq == null) {
                player.sendMessage("Faction does not have a home set");
                return true;
            }


            if (!CooldownManager.hasHomeTimer(player.getUniqueId())) {
                CooldownManager.registerHomeTimer(player, hq);
            }



        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR! CONTACT DEVELOPER ASAP.");
        }

        return true;
    }
}
