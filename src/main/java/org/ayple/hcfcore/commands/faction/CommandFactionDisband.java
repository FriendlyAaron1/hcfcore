package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionDisband extends SubCommand {
    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getDescription() {
        return "disband the faction";
    }

    @Override
    public String getSyntax() {
        return "/f disband";
    }

    // TODO: test this
    @Override
    public boolean perform(Player player, String[] args) {
        try {
            Faction faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (faction == null) {
                player.sendMessage("You are not in a faction!");
                return true;
            }


            if (!FactionManager.isPlayerLeader(player.getUniqueId())) {
                player.sendMessage("You need to be leader!");
                return true;
            }

            FactionManager.disbandFaction(faction.getFactionID());


        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR! CONSULT DEVELOPER ASAP.");
        }

        return false;
    }
}
