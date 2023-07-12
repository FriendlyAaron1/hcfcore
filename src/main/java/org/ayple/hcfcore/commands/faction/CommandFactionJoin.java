package org.ayple.hcfcore.commands.faction;

import org.ayple.hcfcore.commands.SubCommand;
import org.ayple.hcfcore.core.faction.Faction;
import org.ayple.hcfcore.core.faction.FactionInvite;
import org.ayple.hcfcore.core.faction.FactionInviteManager;
import org.ayple.hcfcore.core.faction.FactionManager;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class CommandFactionJoin extends SubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "join a faction";
    }

    @Override
    public String getSyntax() {
        return "/f join [faction name]";
    }

    @Override
    public boolean perform(Player player, String[] args) {
        try{
            if (args.length < 2) return false;
            if (FactionManager.getFactionFromPlayerID(player.getUniqueId()) != null) {
                player.sendMessage("You are already in a faction! do /f leave first");
                return true;
            }

            Faction target_faction = FactionManager.getFaction(args[1]);
            if (target_faction == null) {
                player.sendMessage("That faction doesnt exist");
                return true;
            }

            FactionInvite invite = new FactionInvite(target_faction.getFactionID(), player.getUniqueId());
            FactionInviteManager.onPlayerJoinFaction(invite);
            player.sendMessage("Joined " + target_faction.getFactionName());
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            player.sendMessage("SQL ERROR");
        }

        return true;
    }
}
