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

    @Override
    public void perform(Player player, String[] args) {
        try {
            Faction faction = FactionManager.getFactionFromPlayerID(player.getUniqueId());
            if (faction.equals(null)) {
                player.sendMessage("Failed to disband faction!");
                return;
            }



        } catch (SQLException e) {

        }
    }
}
